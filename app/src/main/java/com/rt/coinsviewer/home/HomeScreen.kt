package com.rt.coinsviewer.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rt.coinsviewer.Status
import com.rt.coinsviewer.common.BottomAppBar
import com.rt.coinsviewer.common.TopAppBar
import com.rt.coinsviewer.common.ProgressBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenEntryPoint(navController: NavHostController? = null) {
    Scaffold(
        topBar = { TopAppBar(title = "All Coins") },
        bottomBar = { BottomAppBar() },
        isFloatingActionButtonDocked = false,
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp)) {
                HomeScreen()
            }
        }
    )
}

@Composable
private fun HomeScreen(homeVM: HomeVM = hiltViewModel()) {
    homeVM.fetchCoins()
    CoinsView(homeVM)
}

@Composable
private fun CoinsView(homeVM: HomeVM) {
    val uiState = homeVM.restCoinsFlow.collectAsState()
    when (uiState.value.status) {
        Status.LOADING -> ProgressBar(true)
        Status.SUCCESS -> {
            RenderCoins(homeVM)
            ProgressBar(false)
        }
        Status.ERROR -> ProgressBar(false)
        else -> Unit
    }
}