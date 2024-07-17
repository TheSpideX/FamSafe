package com.spidex.safe.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class UserData(
    val id: String,
    var name: String,
    var photoUrl: String? = null,
    var fcmToken: String = "",
    val groups: Map<String, Boolean> = emptyMap(),
    var location: LocationData? = null,
    var status: UserStatus = UserStatus.UNKNOWN,
    var locationRequestActive: Boolean = false,
    var batteryPercentage: Int = -1,
    var networkType: String = "Unknown",
    val alertSettings: AlertSettings = AlertSettings()
) : Parcelable

// Location Data
@Parcelize
data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val accuracy: Float? = null
) : Parcelable

// User Status Enum
enum class UserStatus {
    OUTSIDE,
    HOME,
    SOS,
    LONG_RIDE,
    UNKNOWN
}

// Group Data
data class GroupData(
    val groupId: String,
    val name: String,
    val members: List<String>,
    val locationRequestActive: Boolean = false,
    val requestStartTime: Long? = null,
    val channelName: String? = null,
    val code: String,
    val admin: String? = null
)

// Alert Settings
@Parcelize
data class AlertSettings(
    val sosSound: String = "default_sos_sound",
    val sosRadius: Int = 500, // In meters
    val checkInSound: String = "default_check_in_sound"
) : Parcelable