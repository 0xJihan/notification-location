package com.jihan.app.presentation.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jihan.app.domain.utils.DeviceInfo
import com.jihan.app.presentation.screens.components.CenterBox

@Composable
fun NotificationChannel(modifier: Modifier = Modifier) {
    val context = LocalContext.current


    LazyColumn(Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        item {


    CenterBox {


        var title by rememberSaveable { mutableStateOf("") }
        var description by rememberSaveable { mutableStateOf("") }


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f).padding(8.dp),
            value = title,
            onValueChange = { title = it },
            label = { Text("Title of Notification") })
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f).padding(8.dp),
            value = description,
            onValueChange = { description = it },
            label = { Text("Title of Notification") })


        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DeviceInfo.showNotification(context,title,description)
            }
        }) {
            Text("Show Notification")
        }
    }
        }
    }


}