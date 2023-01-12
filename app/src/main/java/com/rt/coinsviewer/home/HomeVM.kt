package com.rt.coinsviewer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rt.coinsviewer.UiResult
import com.rt.coinsviewer.UiResult.Companion.error
import com.rt.coinsviewer.UiResult.Companion.success
import com.rt.common.UiLog
import com.rt.domain.home.CoinsUseCase
import com.rt.domain.models.Coins
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val coinsUseCase: CoinsUseCase
) : ViewModel() {

    private val _coinsFlow = MutableStateFlow<UiResult<Coins>>(UiResult.none())
    val coinsFlow: StateFlow<UiResult<Coins>> = _coinsFlow.asStateFlow()

    fun fetchCoins() {
        coinsUseCase.fetchCoins()
            .onStart { UiLog.i("Start request: Fetch coins.") }
            .onCompletion { UiLog.i("Stop request: Fetch coins.") }
            .onEach { result ->
                result.onSuccess { _coinsFlow.value = success(it) }
                result.onFailure { _coinsFlow.value = error(it.message) }
            }
            .catch { UiLog.e("Fetch coins exception.", it) }
            .launchIn(viewModelScope)
    }
}