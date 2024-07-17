package com.spidex.safe.authentication

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.database
import com.google.firebase.messaging.FirebaseMessaging
import com.spidex.safe.MainRepository
import com.spidex.safe.data.AlertSettings
import com.spidex.safe.data.LocationData
import com.spidex.safe.data.UserData
import com.spidex.safe.data.UserStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val repository : MainRepository
) : AndroidViewModel(application){
    //Firebase
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = Firebase.database.reference

    // StateFlow for the current Firebase user
    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser : StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    // SharedFlow for emitting error messages
    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    // StateFlow to track if the user is being created
    private val _isCreatingUser = MutableStateFlow(false)
    val isCreatingUser: StateFlow<Boolean> = _isCreatingUser

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(application)
    }

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser
            repository.setCurrentUser(firebaseAuth.currentUser)
            firebaseAuth.currentUser?.let { user ->
                viewModelScope.launch {
                    createUserIfNotExist(user)
                }
            }
        }
    }

    // Sign in with email and password
    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _currentUser.value = auth.currentUser
            } catch (e: Exception) {
                _errorMessage.emit(e.localizedMessage ?: "Sign-in failed.")
            }
        }
    }

    // Sign up with email and password
    fun signUpWithEmailAndPassword(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isCreatingUser.value = true
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user ?: throw Exception("Sign-up failed")
                saveUserDataToDatabase(firebaseUser, name)
                _isCreatingUser.value = false
            } catch (e: Exception) {
                _isCreatingUser.value = false
                _errorMessage.emit(e.localizedMessage ?: "Sign-up failed.")
            }
        }
    }

    // Sign out
    fun signOut() {
        auth.signOut()
    }

    // Function to check and create (or update) user data upon login
    private suspend fun createUserIfNotExist(user: FirebaseUser) {
        val userSnapshot = databaseReference.child("users").child(user.uid).get().await()
        if (!userSnapshot.exists()) {
            // User data doesn't exist, so create it
            saveUserDataToDatabase(user)
        } else {
            // User data exists, update it if necessary (e.g., FCM token)
            updateUserDataIfNeeded(user)
        }
    }

    private suspend fun updateUserDataIfNeeded(firebaseUser: FirebaseUser) {
        // Fetch updated FCM token
        val updatedFcmToken = FirebaseMessaging.getInstance().token.await()
        databaseReference.child("users").child(firebaseUser.uid).child("fcmToken").setValue(updatedFcmToken)

        // Fetch other updatable user data (e.g., profile picture) if needed and update in the database
    }

    // Save user data to Realtime Database
    private suspend fun saveUserDataToDatabase(user: FirebaseUser, name: String = "New User") {
        try {
            val fcmToken = FirebaseMessaging.getInstance().token.await()
            val context = getApplication<Application>().applicationContext
            val location = fetchLocation(context)
            val batteryPercentage = getBatteryPercentage(context)
            val networkType = getNetworkType(context)
            val newUserData = UserData(
                id = user.uid,
                name = name,
                photoUrl = "https://example.com/alice.jpg",
                fcmToken = fcmToken,
                groups = emptyMap(),
                location = location,
                status = UserStatus.UNKNOWN,
                locationRequestActive = false,
                batteryPercentage = batteryPercentage,
                networkType = networkType,
                alertSettings = AlertSettings()
            )

            databaseReference.child("users").child(user.uid).setValue(newUserData)
                .addOnSuccessListener {
                    _isCreatingUser.value = false
                    Log.d(TAG, "User data saved successfully for UID: ${user.uid}")
                }
                .addOnFailureListener { exception ->
                    _isCreatingUser.value = false
                    viewModelScope.launch {
                        _errorMessage.emit(exception.localizedMessage ?: "Sign-up failed.")
                        Log.e(
                            TAG,
                            "Failed to save user data for UID: ${user.uid}, error: ${exception.localizedMessage}"
                        )
                    }
                }

        } catch (e: Exception) {
            _isCreatingUser.value = false
            viewModelScope.launch {
                _errorMessage.emit(e.localizedMessage ?: "Sign-up failed.")
                Log.e(
                    TAG,
                    "Exception during saveUserDataToDatabase for UID: ${user.uid}, error: ${e.localizedMessage}"
                )
            }
        }
    }
    // Function to fetch location (using FusedLocationProviderClient)
    private suspend fun fetchLocation(context: Context): LocationData? {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "No location permission granted")
            return null
        }
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        continuation.resume(
                            LocationData(
                                location.latitude,
                                location.longitude,
                                location.time,
                                location.accuracy
                            )
                        )
                    } else {
                        continuation.resume(null) // Return null if location is unavailable
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception) // Pass exception in case of failure
                }
        }
    }

    // Function to get battery percentage
    private fun getBatteryPercentage(context: Context): Int {
        val batteryManager = getSystemService(context, BatteryManager::class.java)
        return batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) ?: -1
    }

    // Function to get network type
    private fun getNetworkType(context: Context): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return "Unknown"
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                when (capabilities.linkDownstreamBandwidthKbps) {
                    in 0..1000 -> "2G"
                    in 1001..5000 -> "3G"
                    in 5001..20000 -> "4G"
                    else -> "5G"
                }
            }
            else -> "Unknown"
        }
    }
}