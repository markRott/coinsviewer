package com.rt.data.log

import com.rt.common.RawResponseLog
import io.ktor.client.plugins.logging.*

object ApiLogger {

    fun getApiLogger(): Logger {

        return object : Logger {
            override fun log(message: String) {
                RawResponseLog.i(message)
            }
        }
    }
}