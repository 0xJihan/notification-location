package com.jihan.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jihan.app.domain.utils.DeviceInfo
import com.jihan.app.presentation.screens.HomeScreen
import com.jihan.app.presentation.screens.MapScreen
import com.jihan.app.presentation.screens.NotificationChannel
import com.jihan.app.presentation.screens.components.CenterBox
import com.jihan.app.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {

    private val permissionsToRequest = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.ACCESS_FINE_LOCATION
    )



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {


            LaunchedEffect(Unit) {
                while (true){
                    delay(60000)
                    DeviceInfo.showNotification(this@MainActivity)
                }
            }

            AppTheme {


                var isLocationPermissionGranted by remember {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                }
                var isNotificationPermissionGranted by remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                    } else mutableStateOf(true)
                }


                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        permissionsToRequest.forEach { permission ->
                            when (permission) {
                                Manifest.permission.POST_NOTIFICATIONS -> {
                                    isNotificationPermissionGranted = perms[permission] == true
                                }

                                Manifest.permission.ACCESS_FINE_LOCATION -> {

                                    isLocationPermissionGranted = perms[permission] == true

                                }

                            }
                        }
                    }
                )


                if (isNotificationPermissionGranted && isLocationPermissionGranted) {
                    MainApp()
                } else {
                    CenterBox {
                        Text(
                            "Permissions Required",
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Button(onClick = {

                            val shouldShowRationaleNotification =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                                } else false

                            val shouldShowRationaleLocation =
                                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)

                            if (!shouldShowRationaleNotification && !shouldShowRationaleLocation) {
                                // Permissions cannot be requested anymore, open settings
                                openAppSettings()
                            } else {
                                // Request permissions
                                multiplePermissionResultLauncher.launch(permissionsToRequest)
                            }
                        }) {
                            Text("Grant Permissions")
                        }
                    }
                }


                LaunchedEffect(Unit) {
                    multiplePermissionResultLauncher.launch(permissionsToRequest)
                }


            }
        }
    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onPause() {
//        super.onPause()
//      Handler(Looper.getMainLooper()).postDelayed({
//          DeviceInfo.showNotification(this)
//      },3000)
//    }

    @Composable
    fun MainApp() {

        val navController = rememberNavController()


        NavHost(navController, Routes.Home) {

            composable<Routes.Home> {
                HomeScreen(navController)
            }
            composable<Routes.Map> {
                MapScreen()
            }
            composable<Routes.Notifications> {
                NotificationChannel()
            }

        }

    }

}


sealed interface Routes {
    @Serializable
    data object Home : Routes

    @Serializable
    data object Map : Routes

    @Serializable
    data object Notifications : Routes
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}