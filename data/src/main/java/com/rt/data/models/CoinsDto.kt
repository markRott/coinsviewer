package com.rt.data.models

import com.rt.domain.models.Coin
import com.rt.domain.models.Coins
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.internal.toImmutableList
import kotlin.math.roundToInt

@Serializable
data class CoinsDto(
    @SerialName("coins") val coinsDto: List<CoinDto?> = emptyList()
)

@Serializable
data class CoinDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String = "",
    @SerialName("icon") val icon: String = "",
    @SerialName("price") val price: Double = 0.0,
    @SerialName("symbol") val symbol: String = "",
)

fun CoinsDto.toCoins(): Coins {
    val items = mutableListOf<Coin>()

    coinsDto
        .filterNotNull()
        .forEach { currCoin ->
//            val price = (currCoin.price * 10000.0).roundToInt() / 10000.00
            items.add(
                Coin(
                    id = currCoin.id,
                    name = currCoin.name,
                    icon = currCoin.icon,
                    price = "%.2f".format(currCoin.price),
                    symbol = currCoin.symbol
                )
            )
        }

    return Coins(items)
}