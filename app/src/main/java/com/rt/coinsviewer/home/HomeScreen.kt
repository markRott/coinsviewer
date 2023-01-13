package com.rt.coinsviewer.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rt.coinsviewer.Status
import com.rt.domain.models.Coin

@Composable
fun HomeScreen(homeVM: HomeVM = hiltViewModel()) {
    homeVM.fetchCoins()
    CoinsView(homeVM = homeVM)
}

@Composable
fun CoinsView(homeVM: HomeVM) {
    val uiState = homeVM.coinsFlow.collectAsState()

    when (uiState.value.status) {
        Status.LOADING -> ProgressBar(true)
        Status.SUCCESS -> {
            RenderCoins(uiState.value.data?.coins ?: emptyList())
            ProgressBar(false)
        }
        Status.ERROR -> ProgressBar(false)
        else -> Unit
    }
}

@Composable
fun RenderCoins(coins: List<Coin>) {
    if (coins.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(coins) { currCoin ->
                key(currCoin.id) { CoinItem(coin = currCoin) }
            }
        }
    }
}

@Composable
fun CoinItem(coin: Coin) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        CoinImage(url = coin.icon)
        Spacer(modifier = Modifier.width(4.dp))
        CoinName(name = coin.name)
        Spacer(Modifier.weight(1f))
        CoinPrice(price = coin.price)
    }
}

@Composable
fun CoinImage(url: String) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(24.dp)
            .height(24.dp)
            .clip(CircleShape)
    )
}

@Composable
fun CoinName(name: String) {
    Text(
        text = name,
        fontSize = 18.sp
    )
}

@Composable
fun CoinPrice(price: String) {
    Text(
        text = price,
        fontSize = 18.sp
    )
}

@Composable
fun ProgressBar(visibleState: Boolean) {
//    var visible by remember { mutableStateOf(true) }
    AnimatedVisibility(
        visible = visibleState,
        enter = fadeIn(
            // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
            initialAlpha = 0.4f
        ),
        exit = fadeOut(
            // Overwrites the default animation with tween
            animationSpec = tween(durationMillis = 250)
        )
    ) {
        // Content that needs to appear/disappear goes here:
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) { CircularProgressIndicator() }
    }
}