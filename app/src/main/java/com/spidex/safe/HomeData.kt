package com.spidex.safe

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

data class ListData(
    val dataList :  List<PersonData>
)

@Parcelize
data class PersonData(
    val name: String,
    val location : String,
    val battery : Int,
    val distance : Int,
    val network : String,
    val mobile : String,
    val icon : Int,
    val cond : Int,
    val time : String,
    val id: Int,
    val latLag : LatLng
) : Parcelable