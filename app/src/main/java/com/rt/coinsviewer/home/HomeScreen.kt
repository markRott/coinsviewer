package com.rt.coinsviewer.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.rt.common.UiLog
import com.rt.domain.models.Coin

@Composable
fun HomeScreen(homeVM: HomeVM = hiltViewModel()) {
    homeVM.fetchCoins()
    CoinsView(homeVM = homeVM)
}

@Composable
fun CoinsView(homeVM: HomeVM) {
    val uiState = homeVM.coinsFlow.collectAsState()
    val result: List<Coin> = uiState.value.data?.coins ?: emptyList()
    UiLog.i("Coins: $result")

    if (result.isNotEmpty()) RenderCoins(result)
}

@Composable
fun RenderCoins(coins: List<Coin>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(coins) { currCoin ->
            key(currCoin.id) { CoinItem(coin = currCoin) }
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