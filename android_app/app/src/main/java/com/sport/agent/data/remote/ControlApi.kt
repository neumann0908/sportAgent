package com.sport.agent.data.remote

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface ControlApi {
    @GET
    suspend fun getControlData(
        @Url endpoint: String,
        @Header("Authorization") authorization: String?
    ): Response<JsonObject>
}
