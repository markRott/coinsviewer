package com.rt.domain.home

import com.rt.domain.models.Coins
import com.rt.domain.repositories.CoinSocketContract
import com.rt.domain.repositories.CoinsContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinsUseCase @Inject constructor(
    private val coinsContract: CoinsContract,
    private val coinSocketContract: CoinSocketContract
) {

    fun fetchCoins(): Flow<Result<Coins>> {
        return coinsContract.fetchCoins()
    }

    suspend fun connectToCoinSocket() {
        coinSocketContract.connect()
    }
}