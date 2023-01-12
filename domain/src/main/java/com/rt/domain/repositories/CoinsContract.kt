package com.rt.domain.repositories

import com.rt.domain.models.Coins
import kotlinx.coroutines.flow.Flow

interface CoinsContract {

    fun fetchCoins() : Flow<Result<Coins>>
}