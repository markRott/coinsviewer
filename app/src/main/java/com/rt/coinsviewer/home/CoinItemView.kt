package com.rt.coinsviewer.home

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rt.domain.models.Coin
import com.rt.domain.models.PriceFluctuation

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
    val context = LocalContext.current
    val currFluctuation = coin.priceFluctuation
    val fluctuationColour: Color = when (coin.priceFluctuation) {
        PriceFluctuation.UP -> Color.Green
        PriceFluctuation.DOWN -> Color.Red
        else -> Color.Black
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = CoinRowModifier(currFluctuation, fluctuationColour)
    ) {
        CoinImage(url = coin.icon)
        Spacer(modifier = Modifier.width(4.dp))
        CoinName(name = coin.name)
        Spacer(Modifier.weight(1f))
        CoinPrice(coin.price, currFluctuation, fluctuationColour)
    }
}

@Composable
fun CoinRowModifier(
    currFluctuation: PriceFluctuation,
    fluctuationColour: Color
): Modifier {
//    val animatedBorderColour = animBorderColor(currFluctuation, fluctuationColour)
    return Modifier
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = Color.Black,
//            shape = remember(animatedBorderColour) { RoundedCornerShape(percent = 15) }
            shape = RoundedCornerShape(percent = 15)
        )
        .padding(16.dp)
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
        fontSize = 16.sp,
        color = Color.Black
    )
}

@Composable
fun CoinPrice(price: String, currFluctuation: PriceFluctuation, fluctuationColour: Color) {
//    val animatedTextColour = animTextColor(currFluctuation, fluctuationColour)
    Text(
        text = price,
        fontSize = 16.sp,
//        color = animatedTextColour.value
        color = Color.Black
    )
}

@Composable
private fun animBorderColor(
    currFluctuation: PriceFluctuation,
    fluctuationColour: Color
): Animatable<Color, AnimationVector4D> {
    val animatedBorderColour = remember { Animatable(Color.Black) }
    if (currFluctuation != PriceFluctuation.UNKNOWN) {
        LaunchedEffect(currFluctuation) {
            animatedBorderColour.animateTo(fluctuationColour, tween(ANIM_DURATION))
            animatedBorderColour.animateTo(Color.Black, tween(ANIM_DURATION))
        }
    }
    return animatedBorderColour
}

@Composable
private fun animTextColor(
    currFluctuation: PriceFluctuation,
    fluctuationColour: Color
): Animatable<Color, AnimationVector4D> {
    val animatedTextColour = remember { Animatable(Color.Black) }
    if (currFluctuation != PriceFluctuation.UNKNOWN) {
        LaunchedEffect(currFluctuation) {
            animatedTextColour.animateTo(fluctuationColour, tween(ANIM_DURATION))
            animatedTextColour.animateTo(Color.Black, tween(ANIM_DURATION))
        }
    }
    return animatedTextColour
}

private const val ANIM_DURATION = 250