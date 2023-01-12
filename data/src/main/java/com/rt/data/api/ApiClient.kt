package com.rt.data.api

import com.rt.common.DataLog
import com.rt.data.log.ApiLogger
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*

private const val BASE_URL = "https://api.coinstats.app/public/v1/"

class ApiClient {

    val client: HttpClient = HttpClient(Android) {
        expectSuccess = true
        //
        install(HttpTimeout)
        install(DefaultRequest) { url(BASE_URL) }
        install(ContentNegotiation) {
            json(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
            logger = ApiLogger.getApiLogger()
        }

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
//                ExceptionManager.getException(exception)
                DataLog.e(msg = "", throwable = exception)
            }
        }
    }

}