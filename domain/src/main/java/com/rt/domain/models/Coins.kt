package com.rt.domain.models

data class Coins(val coins: List<Coin> = emptyList())

data class Coin(
    val price: String = "",
    val id: String,
    val name: String = "",
    val symbol: String = "",
    val icon: String = "",
) {
    override fun toString(): String {
        return "Coin(price='$price', name='$name')"
    }
}