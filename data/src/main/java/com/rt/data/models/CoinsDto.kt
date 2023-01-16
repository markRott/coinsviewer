package com.rt.data.models

import com.rt.common.coinPriceFormat
import com.rt.domain.models.Coin
import com.rt.domain.models.Coins
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
            items.add(
                Coin(
                    id = currCoin.id,
                    name = currCoin.name,
                    icon = currCoin.icon,
                    price = currCoin.price.coinPriceFormat(2),
                    symbol = currCoin.symbol
                )
            )
        }

    return Coins(items)
}