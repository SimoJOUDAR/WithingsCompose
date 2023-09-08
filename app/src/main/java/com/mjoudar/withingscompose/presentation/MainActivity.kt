package com.mjoudar.withingscompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mjoudar.withingscompose.presentation.NavGraph.HomeScreenRoute
import com.mjoudar.withingscompose.presentation.NavGraph.ResultScreenRoute
import com.mjoudar.withingscompose.presentation.ui.screens.home_screen.HomeScreen
import com.mjoudar.withingscompose.presentation.ui.screens.result_screen.ResultScreen
import com.mjoudar.withingscompose.presentation.ui.theme.WithingsComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()

            val sharedViewModel: SharedViewModel = hiltViewModel()

            WithingsComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenRoute
                    ) {
                        composable(HomeScreenRoute) {
                            HomeScreen(navController, sharedViewModel)
                        }
                        composable(ResultScreenRoute) {
                            ResultScreen(this@MainActivity, sharedViewModel)
                        }
                    }
                }
            }
        }
    }
}