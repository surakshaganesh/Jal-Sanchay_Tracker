package com.jalsanchay.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.jalsanchay.tracker.navigation.JalSanchayNavGraph
import com.jalsanchay.tracker.ui.theme.JalSanchayTheme
import com.jalsanchay.tracker.viewmodel.JalSanchayViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JalSanchayTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val viewModel: JalSanchayViewModel = viewModel()
                    JalSanchayNavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}
