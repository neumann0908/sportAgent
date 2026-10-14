package com.sportinteligent.app.data.remote

import retrofit2.http.GET

interface ControlApi {
    @GET("api/v1/control/overview")
    suspend fun getSystemOverview(): BaseResponse<SystemOverviewData>

    @GET("api/v1/control/synchronization")
    suspend fun getSyncOverview(): BaseResponse<SyncOverviewData>

    @GET("api/v1/control/predictions")
    suspend fun getPredictionOverview(): BaseResponse<PredictionOverviewData>

    @GET("api/v1/control/intelligence")
    suspend fun getIntelligenceOverview(): BaseResponse<IntelligenceOverviewData>
}
