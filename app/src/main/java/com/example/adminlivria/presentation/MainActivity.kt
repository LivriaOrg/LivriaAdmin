package com.example.adminlivria.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.adminlivria.presentation.navigation.AdminNavGraph
import com.example.adminlivria.presentation.ui.theme.AdminLivriaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge()
        setContent {
            AdminLivriaTheme {
                AdminNavGraph()
            }
        }
    }
}
