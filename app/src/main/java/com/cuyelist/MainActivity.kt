package com.cuyelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cuyelist.navigation.AppNavigation
import com.cuyelist.ui.theme.CuyeListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CuyeListTheme {
                AppNavigation()
            }
        }
    }
}
