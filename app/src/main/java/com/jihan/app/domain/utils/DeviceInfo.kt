package com.jihan.app.domain.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jihan.app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

object DeviceInfo {
    suspend fun getDeviceDetails(): String {
        return try {
            val manufacturer = Build.MANUFACTURER.capitalize()
            val model = Build.MODEL.capitalize()
            val device = Build.DEVICE.capitalize()
            val brand = Build.BRAND.capitalize()
            val product = Build.PRODUCT.capitalize()
            val hardware = Build.HARDWARE.capitalize()
            val sdkInt = Build.VERSION.SDK_INT
            val release = Build.VERSION.RELEASE
            val display = Build.DISPLAY
            val id = Build.ID

            """
            Manufacturer: $manufacturer
            Model: $model
            Device: $device
            Brand: $brand
            Product: $product
            Hardware: $hardware
            Android Version: $release
            SDK Level: $sdkInt
            Display: $display
            ID: $id
            """.trimIndent()
        } catch (e: Exception) {
            "Unable to retrieve device details: ${e.message}"
        }
    }

    // Extension function to capitalize the first letter
    private fun String.capitalize(): String {
        return if (isNotEmpty()) {
            this[0].uppercase() + substring(1).lowercase()
        } else {
            this
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
     fun showNotification(context: Context , title:String = "Notification Title ${Random.nextInt(999)}" , description:String="Random Description") {
        val channelId = "RANDOM_NOTIFICATION_CHANNEL"
        val notificationId = Random.nextInt(10000)

        val channel = NotificationChannel(
            channelId, "Random Notification", NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            this.description = "Random Description"
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notificationTitle = title.ifEmpty { "Notification Title ${Random.nextInt(999)}" }
        val notificationDescription = description.ifEmpty { "Random Description" }

        val builder = NotificationCompat.Builder(context, channelId).setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notificationTitle).setContentText(notificationDescription)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context, android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
        }
    }
}
