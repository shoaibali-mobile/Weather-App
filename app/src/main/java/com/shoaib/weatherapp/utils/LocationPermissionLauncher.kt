package com.shoaib.weatherapp.utils

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext


@Composable
fun rememberLocationPermissionLauncher(
    locationPermissionHandler: LocationPermissionHandler,
    onLocationReceived: (Double, Double) -> Unit,
    onPermissionDenied: () -> Unit = {}
): ActivityResultLauncher<String> {
    
    val context = LocalContext.current
    val activity = context as? Activity
    
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            try {
                locationPermissionHandler.getCurrentLocation(
                    onLocationReceived = onLocationReceived
                )
            } catch (e: SecurityException) {
                // Permission was revoked at runtime
            }
        } else {

            if (activity != null) {
                locationPermissionHandler.openAppSettings(activity)
            } else {
                onPermissionDenied()
            }
        }
    }
    
    LaunchedEffect(Unit) {
        if (locationPermissionHandler.hasLocationPermission()) {
            try {
                locationPermissionHandler.getCurrentLocation(
                    onLocationReceived = onLocationReceived
                )
            } catch (e: SecurityException) {
                // Permission was revoked at runtime
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    
    return locationPermissionLauncher
}

