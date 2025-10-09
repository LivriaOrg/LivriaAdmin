package com.example.adminlivria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.adminlivria.common.navigation.AdminNavGraph
import com.example.adminlivria.common.ui.theme.AdminLivriaTheme

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