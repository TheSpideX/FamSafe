package com.spidex.safe.mainScreen

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.io.IOException
import java.util.Locale

@Composable
fun DashboardScreen(){


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

    }
}

@Composable
fun MapScreen(location: LatLng) {
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(hardcodedLocation, 15f)
    }
//    val address = getLocationFromLatLng(context, location)
//    Toast.makeText(context, "Location: $address", Toast.LENGTH_SHORT).show()

    Box(){
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                isMyLocationEnabled = false,
                isTrafficEnabled = false,
                mapType = MapType.NORMAL,
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                compassEnabled = false,
                myLocationButtonEnabled = false,
                mapToolbarEnabled = false
            ),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
            }
        ) {
                Marker(
                    state = MarkerState(position = hardcodedLocation)
                )
        }
    }
}

private fun getLocationFromLatLng(context: Context, latLng: LatLng): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address>? = try {
        geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    } catch (e: IOException) {
        Log.e("map geolocation", "Geocoding failed: ${e.message}")
        null
    }
    return addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown Address"
}

val hardcodedLocation = LatLng(37.7749, -122.4194) // Example: San Francisco
