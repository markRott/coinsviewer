package com.rt.data

import com.rt.common.DataLog
import com.rt.data.api.ApiClient
import com.rt.data.models.CoinsDto
import com.rt.data.models.toCoins
import com.rt.data.thread.ThreadContract
import com.rt.domain.models.Coins
import com.rt.domain.repositories.CoinsContract
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoinsRepositoryImpl @Inject constructor(
    apiClient: ApiClient,
    private val thread: ThreadContract
) : CoinsContract {

    private val allCoinsUrl = "coins"
    private val api = apiClient.client

    override fun fetchCoins(): Flow<Result<Coins>> {

        return flow {
            val resultDto: CoinsDto = api.get(allCoinsUrl).body()
            val result = resultDto.toCoins()
            DataLog.i("Result: $result")
            emit(Result.success(result))
        }
            .catch { exception ->
                DataLog.e("Fetch coins exception", exception)
            }
            .flowOn(thread.io)
    }
}