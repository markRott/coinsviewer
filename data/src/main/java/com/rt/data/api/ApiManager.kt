package com.rt.data.api

import javax.inject.Inject

class ApiManager @Inject constructor(
    private val apiClient: ApiClient
) {
    val api = apiClient.client
}