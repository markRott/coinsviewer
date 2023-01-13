package com.rt.coinsviewer.navigation

import androidx.navigation.NavHostController

sealed class Screens(val path: String) {
    object None : Screens("")
    object Splash : Screens("Splash")
    object Home : Screens("Home")
}

object NavManager {

    fun navigate(
        screen: Screens,
        navController: NavHostController? = null,
        inclusiveState: Boolean = false,
        popUpScreen: Screens = Screens.None
    ) {
        navController?.navigate(screen.path) {
            if (inclusiveState) {
                popUpTo(popUpScreen.path) { inclusive = true }
            }
        }
    }
}
