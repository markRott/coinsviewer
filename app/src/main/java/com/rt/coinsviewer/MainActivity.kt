package com.rt.coinsviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rt.coinsviewer.home.HomeScreenEntryPoint
import com.rt.coinsviewer.navigation.Screens
import com.rt.coinsviewer.splash.SplashScreen
import com.rt.coinsviewer.ui.theme.CoinsViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinsViewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StartScreen()
                }
            }

        }
    }

    @Composable
    fun StartScreen() {
        val navController: NavHostController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screens.Splash.path
        ) {
            composable(Screens.Splash.path) { SplashScreen(navController) }
            composable(Screens.Home.path) { HomeScreenEntryPoint(navController) }
        }
    }
}