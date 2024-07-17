package com.spidex.safe

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.spidex.safe.data.GroupData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor() {

    companion object {
        val instance: MainRepository by lazy { MainRepository() }
    }

    // Firebase references
    private val database = Firebase.database
    private val usersRef = database.getReference("users")
    private val groupsRef = database.getReference("groups")
    private val auth = Firebase.auth

    // User data
    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()
    private var userId: String? = null

    // Group data
    private val _userGroups = MutableStateFlow<List<GroupData>>(emptyList())
    val userGroups: StateFlow<List<GroupData>> = _userGroups.asStateFlow()

    // Error message state (optional)
    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    fun setCurrentUser(user : FirebaseUser?){
        _currentUser.value = user
        user?.let{
            userId = it.uid
        }
    }

}