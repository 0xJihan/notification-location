package com.jihan.app.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jihan.app.Routes
import com.jihan.app.domain.utils.DeviceInfo
import com.jihan.app.presentation.screens.components.CenterBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(navController: NavController) {

    CenterBox {

        var deviceInfo by rememberSaveable { mutableStateOf("Fetching device info...") }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO){
                deviceInfo = DeviceInfo.getDeviceDetails()
            }
        }

        Text(deviceInfo, fontSize = 22.sp , color = MaterialTheme.colorScheme.onSurface)

        Spacer(Modifier.height(22.dp))

        Button(onClick = {
            navController.navigate(Routes.Map)
        }) { Text("Go to Map Screen") }
        Button(onClick = {
          navController.navigate(Routes.Notifications)
        }) { Text("Go to Notification Screen") }

    }
}