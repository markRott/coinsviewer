package com.rt.data.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import javax.inject.Inject

class ThreadImpl @Inject constructor() : ThreadContract {

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    override val singleThread: CoroutineDispatcher
        get() = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
}