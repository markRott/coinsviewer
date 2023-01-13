package com.rt.coinsviewer.di

import com.rt.data.repositories.CoinSocketImpl
import com.rt.data.repositories.CoinsRepositoryImpl
import com.rt.domain.repositories.CoinsContract
import com.rt.domain.repositories.CoinSocketContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindCoinsContract(coinsContract: CoinsRepositoryImpl): CoinsContract

    @Binds
    abstract fun bindSocketContract(coinSocket: CoinSocketImpl): CoinSocketContract
}