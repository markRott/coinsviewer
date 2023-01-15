package com.rt.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinLiveUpdateDto(
    @SerialName("aavegotchi")
    val aavegotchi: String? = null,
    @SerialName("bitcoin")
    val bitcoin: String? = null,
    @SerialName("civilization")
    val civilization: String? = null,
    @SerialName("ontology")
    val ontology: String? = null,
    @SerialName("pinknode")
    val pinknode: String? = null,
    @SerialName("stellar")
    val stellar: String? = null,
    @SerialName("tezos")
    val tezos: String? = null,
    @SerialName("xrp")
    val xrp: String? = null
)