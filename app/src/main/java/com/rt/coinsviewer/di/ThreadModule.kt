package com.rt.coinsviewer.di

import com.rt.data.thread.ThreadContract
import com.rt.data.thread.ThreadImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ThreadModule {

    @Binds
    abstract fun bindTreadContract(threadImpl: ThreadImpl): ThreadContract
}