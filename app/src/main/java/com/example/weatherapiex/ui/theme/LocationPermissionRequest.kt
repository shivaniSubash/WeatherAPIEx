package com.example.weatherapiex.ui.theme

import androidx.compose.runtime.*
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
    //Enables use of experimental APIs from Accompanist for handling permissions in Jetpack Compose.
@Composable
fun LocationPermissionRequest(
    onPermissionGranted: () -> Unit
) {
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
        // Remembers the state of the location permission, tracking whether it has been granted or denied.

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
            //Triggers the permission request as soon as the Composable is launched.
    }

    if (permissionState.hasPermission) {
            //Executes the onPermissionGranted callback if the location permission is granted.
        onPermissionGranted()
    }
}