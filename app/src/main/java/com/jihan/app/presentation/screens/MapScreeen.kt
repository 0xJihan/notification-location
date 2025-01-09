package com.jihan.app.presentation.screens

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.jihan.app.presentation.screens.components.CenterBox

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
) {


    val context = LocalContext.current

    var currentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
    }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationCallBack: LocationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                for (location in p0.locations) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                }

            }
        }
    }

    LaunchedEffect(Unit) {
        locationCallBack.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                10000
            ).apply {
                setWaitForAccurateLocation(false)
                setMinUpdateIntervalMillis(1000)
                setMaxUpdateDelayMillis(100)
            }.build()

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )

        }
    }
    CenterBox {
        Text(
            " Current Latitude : ${currentLocation.latitude}\n" +
                    " Current Longitude : ${currentLocation.longitude}", fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

