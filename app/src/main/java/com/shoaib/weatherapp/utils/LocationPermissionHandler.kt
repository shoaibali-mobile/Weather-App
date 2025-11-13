package com.shoaib.weatherapp.utils

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shoaib.weatherapp.R
class LocationPermissionHandler(
    application: Application
) {
    
    private val context: Context = application.applicationContext
    
    private val fusedLocationClient: FusedLocationProviderClient = 
        LocationServices.getFusedLocationProviderClient(context)
    

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getCurrentLocation(
        onLocationReceived: (Double, Double) -> Unit,
        onError: (String) -> Unit = { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    ) {
        if (!hasLocationPermission()) {
            onError(context.resources.getString(R.string.error_location_permission_not_granted))
            return
        }
        
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onLocationReceived(location.latitude, location.longitude)
                } else {
                    onError(context.resources.getString(R.string.error_unable_to_fetch_location))
                }
            }
            .addOnFailureListener {
                val errorMessage = context.resources.getString(
                    R.string.error_failed_to_get_location,
                    it.message ?: context.resources.getString(R.string.error_unknown)
                )
                onError(errorMessage)
            }
    }
    


    fun openAppSettings(activity: Activity) {
        val intent = android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = android.net.Uri.fromParts("package", context.packageName, null)
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.startActivity(intent)
    }
}
