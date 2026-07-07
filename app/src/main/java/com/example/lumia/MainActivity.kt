package com.example.lumia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lumia.ui.theme.LumiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LumiaTheme {
                LumiaApp()
            }
        }
    }
}

@Composable
fun LumiaApp() {
    val navController = rememberNavController()
    val soundViewModel: SoundViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "inicial"
    ) {
        composable("inicial") {
            TelaInicial(
                onNavigateToSons = {
                    navController.navigate("sons")
                }
            )
        }
        composable("sons") {
            TelaSons(
                viewModel = soundViewModel,
                onNavigateToPlayer = {
                    navController.navigate("player")
                }
            )
        }
        composable("player") {
            TelaPlayer(
                viewModel = soundViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
