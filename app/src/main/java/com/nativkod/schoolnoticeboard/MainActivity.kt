package com.nativkod.schoolnoticeboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.nativkod.schoolnoticeboard.app.navigation.AppNavGraph
import com.nativkod.schoolnoticeboard.presentation.ui.theme.SchoolNoticeboardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            SchoolNoticeboardTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}