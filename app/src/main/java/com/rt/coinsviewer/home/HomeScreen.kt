package com.rt.coinsviewer.home

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.rt.coinsviewer.Status
import com.rt.coinsviewer.common.ProgressBar

@Composable
fun HomeScreen(homeVM: HomeVM = hiltViewModel()) {
    homeVM.fetchCoins()
    CoinsView(homeVM = homeVM)
}

@Composable
fun CoinsView(homeVM: HomeVM) {
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