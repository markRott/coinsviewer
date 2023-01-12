package com.rt.coinsviewer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rt.common.UiLog
import com.rt.domain.home.CoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val coinsUseCase: CoinsUseCase
) : ViewModel() {

    fun fetchCoins() {
        viewModelScope.launch {
            UiLog.i("Fetch coins")
            coinsUseCase.fetchCoins()
        }
    }
}