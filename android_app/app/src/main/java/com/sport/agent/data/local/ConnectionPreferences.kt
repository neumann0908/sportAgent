package com.sport.agent.data.local

import javax.inject.Inject
import javax.inject.Singleton

data class ServerConfig(
    val baseUrl: String,
    val apiToken: String
)

@Singleton
class ConnectionPreferences @Inject constructor() {
    fun getConfig(): ServerConfig {
        return ServerConfig(
            baseUrl = "http://127.0.0.1:8000",
            apiToken = ""
        )
    }
}
