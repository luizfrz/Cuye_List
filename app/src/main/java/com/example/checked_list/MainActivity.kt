package com.example.checked_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.checked_list.navigation.AppNavigation
import com.example.checked_list.ui.theme.Checked_ListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Checked_ListTheme {
                AppNavigation()
            }
        }
    }
}
