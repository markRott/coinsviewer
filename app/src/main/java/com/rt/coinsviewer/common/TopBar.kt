package com.rt.coinsviewer.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun TopAppBar(
    title: String,
    showBackIcon: Boolean = false,
    navController: NavHostController? = null,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = when {
            isShowBackIcon(showBackIcon, navController) -> {
                { RenderBackIcon(navController = navController) }
            }
            else -> null
        }
    )
}

@Composable
private fun RenderBackIcon(navController: NavHostController?) {
    IconButton(onClick = { navController?.navigateUp() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
}

@Composable
private fun isShowBackIcon(
    showBackIcon: Boolean,
    navController: NavHostController?
) = showBackIcon && navController?.previousBackStackEntry != null