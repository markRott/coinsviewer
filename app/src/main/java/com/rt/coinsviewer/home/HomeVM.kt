package com.rt.coinsviewer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rt.coinsviewer.UiResult
import com.rt.coinsviewer.UiResult.Companion.error
import com.rt.coinsviewer.UiResult.Companion.loading
import com.rt.coinsviewer.UiResult.Companion.success
import com.rt.common.UiLog
import com.rt.domain.home.CoinsUseCase
import com.rt.domain.models.Coins
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val coinsUseCase: CoinsUseCase
) : ViewModel() {

    private val _coinsFlow = MutableStateFlow<UiResult<Coins>>(UiResult.none())
    val coinsFlow: StateFlow<UiResult<Coins>> = _coinsFlow.asStateFlow()

    fun fetchCoins() {
        coinsUseCase.fetchCoins()
            .onStart {
                UiLog.i("Start request: Fetch coins.")
                _coinsFlow.value = loading()
            }
            .onEach { result ->
                result.onSuccess {
                    UiLog.i("Success.")
                    _coinsFlow.value = success(it)
                    connectToCoinSocket()
                }
                result.onFailure { _coinsFlow.value = error(it.message) }
            }
            .catch { UiLog.e("Fetch coins exception.", it) }
            .launchIn(viewModelScope)
    }

    private fun connectToCoinSocket() {
        if (_coinsFlow.value.data?.coins?.isNotEmpty() == true) {
            viewModelScope.launch {
                delay(2000)
                coinsUseCase.connectToCoinSocket()
            }
        }
    }
}