package com.rt.domain.models

data class Coins(val coins: List<Coin> = emptyList())

data class Coin(
    val id: String,
    val name: String = "",
    val icon: String = "",
    val price: String = "",
    val symbol: String = "",
)