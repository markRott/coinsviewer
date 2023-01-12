package com.rt.coinsviewer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rt.coinsviewer.home.HomeScreen
import com.rt.coinsviewer.splash.SplashScreen

sealed class Routes(val route: String) {
    object None : Routes("")
    object Splash : Routes("Splash")
    object Home : Routes("Home")
}

object RouteManager {

    fun navigate(
        route: Routes,
        navController: NavHostController? = null,
        inclusiveState: Boolean = false,
        popUpRoute: Routes = Routes.None
    ) {
        navController?.navigate(route.route) {
            if (inclusiveState) {
                popUpTo(popUpRoute.route) { inclusive = true }
            }
        }
    }
}

@Composable
fun StartScreen() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) { SplashScreen(navController) }
        composable(Routes.Home.route) { HomeScreen() }
    }
}
