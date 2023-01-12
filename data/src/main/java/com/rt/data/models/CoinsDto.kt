package com.rt.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinsDto(
    @SerialName("coins")
    val coinsDto: List<CoinDto?> = emptyList()
)

@Serializable
data class CoinDto(

    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String = "",

    @SerialName("icon")
    val icon: String = "",

    @SerialName("price")
    val price: Double = 0.0,

    @SerialName("symbol")
    val symbol: String = "",
)