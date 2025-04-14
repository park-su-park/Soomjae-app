package com.parksupark.soomjae.core.remote.networking

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun platformHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
