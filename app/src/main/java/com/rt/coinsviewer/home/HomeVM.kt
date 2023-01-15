package com.rt.coinsviewer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rt.coinsviewer.UiResult
import com.rt.coinsviewer.UiResult.Companion.error
import com.rt.coinsviewer.UiResult.Companion.loading
import com.rt.coinsviewer.UiResult.Companion.none
import com.rt.coinsviewer.UiResult.Companion.success
import com.rt.common.UiLog
import com.rt.domain.home.CoinsUseCase
import com.rt.domain.models.Coin
import com.rt.domain.models.PriceFluctuation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias taCoins = UiResult<List<Coin>>

@HiltViewModel
class HomeVM @Inject constructor(
    private val coinsUseCase: CoinsUseCase
) : ViewModel() {

    private val _restCoinsFlow = MutableStateFlow<taCoins>(none())
    val restCoinsFlow: StateFlow<taCoins> = _restCoinsFlow.asStateFlow()

    private val _updateCoinsFlow = MutableStateFlow(listOf<Coin>())
    val updateCoinsFlow: StateFlow<List<Coin>> get() = _updateCoinsFlow

    fun fetchCoins() {
        coinsUseCase.fetchCoins()
            .onStart { _restCoinsFlow.value = loading() }
            .onEach { result ->
                result.onSuccess {
                    _restCoinsFlow.value = success(it.coins)
                    _updateCoinsFlow.value = it.coins.take(5)
                    connectToCoinSocket()
                }
                result.onFailure { _restCoinsFlow.value = error(it.message) }
            }
            .catch { UiLog.e("Fetch coins exception.", it) }
            .launchIn(viewModelScope)
    }

    private fun connectToCoinSocket() {
        if (getAllCoins().isNotEmpty()) {
            viewModelScope.launch {
                coinsUseCase.connectToCoinSocket()
            }
            subscribeToSocketEvents()
        }
    }

    private fun subscribeToSocketEvents() {
        coinsUseCase
            .onSocketStateFlow()
            .filter { it.isNotEmpty() }
            .filterNotNull()
            .onEach { coinMap: Map<String, String> ->
                val currItems = getAllCoins().toMutableList()
                currItems.mapIndexed { index, coin ->

                    val coinId = coin.id
                    if (coinMap.containsKey(coinId)) {

                        val oldPrice = coin.price
                        val newPrice = coinMap[coinId] ?: ""
                        val newFluctuation = calcPriceFluctuation(oldPrice, newPrice)

                        val updatedCoin = coin.copy(
                            price = newPrice,
                            priceFluctuation = newFluctuation
                        )
                        currItems[index] = updatedCoin

                        _updateCoinsFlow.value = currItems.toList()
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun calcPriceFluctuation(
        oldPriceStr: String,
        newPriceStr: String
    ): PriceFluctuation {

        val oldPrice = oldPriceStr.toDoubleOrNull()
        val newPrice = newPriceStr.toDoubleOrNull()

        return if(oldPrice != null && newPrice != null) {
            val newFluctuation = when {
                newPrice > oldPrice -> PriceFluctuation.UP
                else -> PriceFluctuation.DOWN
            }
            newFluctuation

        } else {
            UiLog.i("Bad convert")
            PriceFluctuation.UNKNOWN
        }
    }

    private fun getAllCoins(): List<Coin> = _updateCoinsFlow.value
}