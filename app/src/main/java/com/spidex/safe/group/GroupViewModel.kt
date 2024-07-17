package com.spidex.safe.group

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.spidex.safe.MainRepository
import com.spidex.safe.data.GroupData
import com.spidex.safe.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


private const val TAG = "GroupViewModel"

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository : MainRepository
) : ViewModel(){

    private val databaseReference = Firebase.database.reference
    private var userId : String? = null

    private val _groupList = MutableStateFlow<List<GroupData>>(emptyList())
    val groupList: StateFlow<List<GroupData>> = _groupList.asStateFlow()
    private var groupsListener: ValueEventListener? = null

    private val _groupMembers = MutableStateFlow<Map<String, UserData>>(emptyMap())
    val groupMembers: StateFlow<Map<String, UserData>> = _groupMembers.asStateFlow()
    private val userListeners = mutableMapOf<String, ValueEventListener>()

    private val _currentGroup = MutableStateFlow<GroupData?>(null)
    val currentGroup: StateFlow<GroupData?> = _currentGroup.asStateFlow()

    private val _viewGroup = MutableStateFlow<GroupData?>(null)
    val viewGroup: StateFlow<GroupData?> = _viewGroup.asStateFlow()


    // State to manage dialog visibility
    private val _showCreateGroupDialog = MutableStateFlow(false)
    val showCreateGroupDialog: StateFlow<Boolean> = _showCreateGroupDialog.asStateFlow()

    private val _showJoinGroupDialog = MutableStateFlow(false)
    val showJoinGroupDialog: StateFlow<Boolean> = _showJoinGroupDialog.asStateFlow()

    private val _showEditGroupDialog = MutableStateFlow(false)
    val showEditGroupDialog: StateFlow<Boolean> = _showEditGroupDialog.asStateFlow()

    private val _showGroupDetail = MutableStateFlow(false)
    val showGroupDetail: StateFlow<Boolean> = _showGroupDetail.asStateFlow()

    // SharedFlow for emitting error messages
    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage.asSharedFlow()




    init {
        viewModelScope.launch {
            repository.currentUser.collectLatest { user ->
                user?.let{
                    userId = it.uid
                    fetchUserGroups(it)
                }
            }
        }

        viewModelScope.launch {
            _currentGroup.collectLatest { group ->
                group?.let{
                    fetchGroupMemberData(it)
                }
            }
        }
    }


    private fun fetchUserGroups(user : FirebaseUser?){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = user?.uid ?: return@launch
                val userGroupsRef = databaseReference.child("users").child(userId).child("groups")
                groupsListener = userGroupsRef.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val groupIds = snapshot.children.mapNotNull { it.key }
                        viewModelScope.launch (Dispatchers.IO){
                            val groups = groupIds.mapNotNull { groupId->
                                val groupSnapshot = databaseReference.child("groups").child(groupId).get().await()
                                groupSnapshot.getValue(GroupData::class.java)
                            }
                            _groupList.value = groups
                            groups.forEach { group ->
                                if(group.groupId == _currentGroup.value?.groupId){
                                    fetchGroupMemberData(group)
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e(TAG, "Error fetching user groups: ${error.message}")
                        viewModelScope.launch {
                            _errorMessage.emit("Error fetching groups: ${error.message}")
                        }
                    }
                })
            }
            catch (e : Exception) {
                Log.e(TAG, "Error fetching user groups: ${e.message}")
                viewModelScope.launch {
                    _errorMessage.emit("Error fetching groups: ${e.message}")
                }
            }
        }
    }

    private fun fetchGroupMemberData(groupData : GroupData){
        stopListeningForGroupMembers()

        viewModelScope.launch (Dispatchers.IO){
            _groupMembers.value = emptyMap()
            groupData.members.forEach { userId ->
                val userRef = databaseReference.child("users").child(userId)
                val listener = object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val updatedUserData = snapshot.getValue(UserData::class.java)
                        updatedUserData?.let {
                            _groupMembers.value += (userId to it)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e(TAG, "Error fetching user data: ${error.message}")
                    }
                }
                userRef.addValueEventListener(listener)
                userListeners[userId] = listener
            }
        }
    }

    private fun stopListeningForGroupMembers() {
        userListeners.forEach { (userId, listener) ->
            databaseReference.child("users").child(userId).removeEventListener(listener)
        }
        userListeners.clear()
    }

    fun createGroup(groupName : String){
        _showCreateGroupDialog.value = false
        if(groupName.isNotBlank()){
            createGroupInDatabase(groupName)
        }
        else
        {
            viewModelScope.launch {
                _errorMessage.emit(if (userId == null) "User not logged in" else "Please enter a group name.")
            }
        }
    }

    private fun createGroupInDatabase(groupName : String){
        viewModelScope.launch (Dispatchers.IO){
            try {
                userId?.let { currentUser ->
                    val newGroupRef = databaseReference.child("groups").push()
                    val groupId = newGroupRef.key ?: throw Exception("Failed to create group")

                    val maxAttempts = 10
                    var attemptCount = 0
                    var groupCode : String? = null
                    while (attemptCount < maxAttempts) {
                        groupCode = NanoIdUtils.randomNanoId(
                            NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                            NanoIdUtils.DEFAULT_ALPHABET, 10)

                        val existingGroup = databaseReference.child("groups").orderByChild("code").equalTo(groupCode).get().await()
                        if (!existingGroup.exists()) {
                            break
                        }
                        attemptCount++
                    }
                    if (attemptCount == maxAttempts) {
                        _errorMessage.emit("Could not generate a unique group code. Please try again.")
                        Log.e("create group","failed")
                        return@launch
                    }
                    groupCode?.let {
                        val groupData = GroupData(
                            groupId = groupId,
                            name = groupName,
                            code = groupCode,
                            members = listOf(currentUser),
                            admin = currentUser
                        )
                        Log.d(TAG, "Creating group with admin: $currentUser")  // Log the admin ID
                        Log.d(TAG, "Group data to be saved: $groupData")
                        newGroupRef.setValue(groupData)
                            .addOnSuccessListener {
                                // ... (success handling)
                            }
                            .addOnFailureListener { exception ->
                                Log.e(TAG, "Error creating group: ${exception.message}")
                            }
                            .await()
                        databaseReference.child("users").child(currentUser).child("groups")
                            .child(groupId).setValue(true)
                        fetchUserGroups(repository.currentUser.value!!)
                        _currentGroup.value = groupData
                        _errorMessage.emit("Group created successfully!")
                    }
                }
            }
            catch (e : Exception){
                Log.e(TAG, "Error creating group: ${e.message}")
                viewModelScope.launch {
                    _errorMessage.emit(e.localizedMessage ?: "Failed to create group")
                }
            }
        }
    }

    fun joinGroup(groupCode : String){
        if(groupCode.isNotBlank() && userId!=null){
            joinGroupInDatabase(groupCode)
        }
        else
        {
            viewModelScope.launch {
                _errorMessage.emit(if (userId == null) "User not logged in" else "Please enter a valid group code.")
            }
        }
    }

    private fun joinGroupInDatabase(groupCode : String){
        viewModelScope.launch (Dispatchers.IO){
            try{
                val groupQuery = databaseReference.child("groups").orderByChild("code").equalTo(groupCode)
                val groupSnapshot = groupQuery.get().await()

                if(groupSnapshot.exists()){
                    for(group in groupSnapshot.children){
                        val groupId = group.key ?: throw Exception("Failed to get group ID")
                        val groupData = group.getValue(GroupData::class.java)

                        databaseReference.child("groups/$groupId/members/$userId").runTransaction(object : Transaction.Handler{
                            override fun doTransaction(currentData: MutableData): Transaction.Result {
                                val members = currentData.value as? MutableMap<String,Boolean> ?: mutableMapOf()
                                if(!members.contains(userId!!))
                                {
                                    members[userId!!] = true
                                    currentData.value = members
                                    return Transaction.success(currentData)
                                }
                                return Transaction.abort()
                            }

                            override fun onComplete(
                                error: DatabaseError?,
                                committed: Boolean,
                                currentData: DataSnapshot?
                            ) {
                                viewModelScope.launch {
                                    if (error != null) {
                                        Log.e(TAG, "Failed to join group")
                                        _errorMessage.emit("Failed to join group")
                                    } else if (committed) {
                                        // Add the group to the user's groups list
                                        databaseReference.child("users/$userId/groups/$groupId")
                                            .setValue(true)
                                        _groupList.value = _groupList.value!! + groupData!!
                                        _errorMessage.emit("Joined group successfully")
                                    }
                                }
                            }
                        })
                    }
                }
                else
                {
                    _errorMessage.emit("Invalid group code.")
                }
            }
            catch (e : Exception) {
                Log.e(TAG, "Error joining group: ${e.message}")
                _errorMessage.emit("Failed to join group: ${e.message}")
            }
        }
    }

    fun leaveGroup(groupId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = repository.currentUser.value ?: throw IllegalStateException("User not logged in")

                val groupRef = databaseReference.child("groups").child(groupId)
                val groupSnapshot = groupRef.get().await()
                if (groupSnapshot.exists()) {
                    val groupData = groupSnapshot.getValue(GroupData::class.java)
                    if (groupData?.admin == currentUser.uid) {
                        // User is the admin: Delete the entire group and its member data.
                        groupSnapshot.child("members").children.forEach { memberSnapshot ->
                            val memberId = memberSnapshot.key ?: return@forEach

                            // Remove group from user's group list
                            databaseReference.child("users").child(memberId).child("groups").child(groupId).removeValue().await()
                        }
                        groupRef.removeValue().await() // Delete the group node
                    } else {
                        // User is not admin: Remove them from the group's members and their own groups list
                        removeUserFromGroup(groupId, currentUser.uid)
                    }
                    if(_currentGroup.value == groupData)
                    {
                        _currentGroup.value = null
                    }
                    _errorMessage.emit("Left group successfully!")
                } else {
                    _errorMessage.emit("Group not found!")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error leaving group: ${e.message}")
                _errorMessage.emit(e.localizedMessage ?: "Failed to leave group")
            }
        }
    }

    // Helper function to remove a user from a group (unchanged)
    private suspend fun removeUserFromGroup(groupId: String, userId: String) {
        databaseReference.child("groups/$groupId/members/$userId").removeValue().await()
        databaseReference.child("users/$userId/groups/$groupId").removeValue().await()

        // Refresh the group list to reflect the change
        fetchUserGroups(repository.currentUser.value!!)
    }

    fun setCurrentGroup(group : GroupData){
        _currentGroup.value = group
    }

    fun setCreateGroupDialog(value : Boolean) {
        _showCreateGroupDialog.value = value
    }

    fun setJoinGroupDialog(value : Boolean) {
        _showJoinGroupDialog.value = value
    }

    fun setEditGroupDialog(value : Boolean) {
        _showEditGroupDialog.value = value
    }

    fun setShowGroupDetail(value : Boolean,group : GroupData){
        _showGroupDetail.value = value
        _viewGroup.value = group
    }

    fun clearErrorMessage() {
        viewModelScope.launch {
            _errorMessage.emit(null) // Clear the error message
        }
    }

    override fun onCleared() {
        super.onCleared()
        groupsListener?.let { databaseReference.child("users").child(repository.currentUser.value!!.uid).child("groups").removeEventListener(it) }
        stopListeningForGroupMembers()
    }
}