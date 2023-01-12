package com.rt.domain.home

import com.rt.common.DomainLog
import com.rt.domain.repositories.CoinsContract
import javax.inject.Inject

class CoinsUseCase @Inject constructor(
    private val coinsContract: CoinsContract
) {

    suspend fun fetchCoins() {
        DomainLog.i("Fetch coins")
        coinsContract.fetchCoins()
    }
}