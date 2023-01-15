package com.rt.data.repositories

import com.google.gson.Gson
import com.rt.common.DataLog
import com.rt.data.thread.ThreadContract
import com.rt.domain.repositories.CoinSocketContract
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinSocketImpl @Inject constructor(
    private val thread: ThreadContract
) : CoinSocketContract {

    private val gson = Gson()
    private val socketStateFlow = MutableStateFlow<Map<String, String>>(emptyMap())

    override suspend fun connect() {
        DataLog.i("Connect to coin socket")
        initSocketClient()
    }

    override fun onSocketStateFlow(): StateFlow<Map<String, String>> = socketStateFlow

    private suspend fun initSocketClient() {
        val url = Url("wss://ws.coincap.io/prices?assets=ALL")
        val client = HttpClient(CIO) { install(WebSockets) }

        withContext(thread.singleThread) {
            try {
                connect(client, url)
            } catch (e: Throwable) {
                DataLog.e("Socket connection exception", e)
            }
        }
    }

    private suspend fun connect(ktor: HttpClient, u: Url) {
        ktor.webSocket(u.toString()) {
            incoming.consumeEach { frame ->
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        val coinMap = gson.fromJson(text, Map::class.java) as? Map<String, String>
                        if (coinMap?.isNotEmpty() == true) {
                            socketStateFlow.emit(coinMap ?: emptyMap())
                        }
                    }
                    is Frame.Close -> {
                        val text = closeReason.await()?.message ?: "Def msg"
                        DataLog.i("FrameClose: $text")
                    }
                    else -> DataLog.i("Else branch")
                }
                delay(1000)
            }
        }
    }
}