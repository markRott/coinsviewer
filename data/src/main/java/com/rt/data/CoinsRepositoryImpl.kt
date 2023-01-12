package com.rt.data

import com.rt.common.DataLog
import com.rt.data.api.ApiClient
import com.rt.domain.repositories.CoinsContract
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class CoinsRepositoryImpl @Inject constructor(
    apiClient: ApiClient
) : CoinsContract {

    private val api = apiClient.client

    override suspend fun fetchCoins() {
        val url = "coins"
        val result = api.get(url).body<String>()
        DataLog.i("Result: $result")
    }

}