package com.rt.domain.repositories

import kotlinx.coroutines.flow.Flow

interface CoinSocketContract {

    suspend fun connect()

//    fun socketFlow() : Flow<String>
}