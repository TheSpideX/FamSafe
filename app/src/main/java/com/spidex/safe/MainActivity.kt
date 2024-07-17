package com.spidex.safe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pusher.pushnotifications.PushNotifications
import com.spidex.safe.mainScreen.AppNavigation
import com.spidex.safe.ui.theme.SafeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        PushNotifications.start(this, "0b71755f-5349-4dbe-9e36-a078a747761a")
        setContent {
            SafeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFe5e7e8)
                ) {
                    AppNavigation()
                    Log.e("mainActivity","done")
                }
            }
        }
    }
}
