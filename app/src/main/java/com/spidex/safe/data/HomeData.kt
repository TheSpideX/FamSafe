package com.spidex.safe.data

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

data class ListData(
    val dataList :  List<PersonData>
)

@Parcelize
data class PersonData(
    val id: Int,
    val name: String,
    val location: String,
    val battery: Int,
    val distance: Int,
    val network: String,
    val mobile: String,
    val icon: Int,
    val cond: Condition,
    val time: String,
    val latLng: LatLng,
) : Parcelable

enum class Condition { SOS, HOME, OUTSIDE, LONG_RIDE }