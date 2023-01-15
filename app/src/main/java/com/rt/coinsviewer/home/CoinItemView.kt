package com.rt.coinsviewer.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rt.domain.models.Coin

@Composable
fun RenderCoins(homeVM: HomeVM) {

    val coinItems: List<Coin> by homeVM.updateCoinsFlow.collectAsState()

    if (coinItems.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = coinItems, key = { it.id }) { coin ->
                PrepareCoinItemCard(coin)
            }
        }
    }
}

@Composable
fun PrepareCoinItemCard(coin: Coin) {
    CoinItem(coin = coin)
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