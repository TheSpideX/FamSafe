package com.spidex.safe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.spidex.safe.ui.theme.background

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    val singapore = LatLng(25.597620, 85.183739)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 15f)
    }

    var userLocation by remember { mutableStateOf<LatLng?>(singapore) } // For the map marker
    var isMapLoaded by remember { mutableStateOf(false) }

    BottomSheetScaffold(
        sheetContent = { BottomSheetContent(location = singapore) },
        containerColor = background,
        sheetContainerColor = Color.White
        ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SmallFloatingActionButton(
                onClick = {
                          navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
                    .padding(16.dp),
                containerColor = background
            ) {
                Icon(Icons.Default.Clear, contentDescription = null)
            }
            
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
                    isMapLoaded = true
                }
            ) {
                if (userLocation != null) {
                    Marker(
                        state = MarkerState(position = userLocation!!)
                    )
                }
            }

        }
    }
}

@Composable
fun BottomSheetContent(location: LatLng){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("User Location Details")
        // ... display location information here (latitude, longitude, etc.)
    }
}