package com.rt.domain.repositories

import kotlinx.coroutines.flow.StateFlow

interface CoinSocketContract {

    suspend fun connect()

    fun onSocketStateFlow(): StateFlow<Map<String, String>>
}