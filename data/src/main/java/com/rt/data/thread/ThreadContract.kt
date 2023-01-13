package com.rt.data.thread

import kotlinx.coroutines.CoroutineDispatcher

interface ThreadContract {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val singleThread: CoroutineDispatcher
}