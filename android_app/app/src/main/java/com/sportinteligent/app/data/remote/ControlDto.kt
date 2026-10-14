package com.sportinteligent.app.data.remote

import com.google.gson.annotations.SerializedName

// Contrato de respuesta estándar del backend
data class BaseResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: T?,
    @SerializedName("metadata") val metadata: MetadataDto?,
    @SerializedName("errors") val errors: List<String> = emptyList()
)

data class MetadataDto(
    @SerializedName("sport") val sport: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("source") val source: String,
    @SerializedName("cached") val cached: Boolean
)

// Data DTOs para el Master Control Center
data class SystemOverviewData(
    @SerializedName("server_status") val serverStatus: String,
    @SerializedName("environment") val environment: String,
    @SerializedName("system_version") val systemVersion: String,
    @SerializedName("engines") val engines: Map<String, EngineStatus>,
    @SerializedName("active_services") val activeServices: List<String>,
    @SerializedName("isolated_architecture") val isolatedArchitecture: Boolean
)

data class EngineStatus(
    @SerializedName("status") val status: String,
    @SerializedName("db") val db: String
)

data class SyncOverviewData(
    @SerializedName("last_synchronizations") val lastSynchronizations: Map<String, String>,
    @SerializedName("completed_syncs") val completedSyncs: Int,
    @SerializedName("failed_syncs") val failedSyncs: Int,
    @SerializedName("pending_updates") val pendingUpdates: String,
    @SerializedName("history") val history: List<SyncHistoryItem>
)

data class SyncHistoryItem(
    @SerializedName("engine") val engine: String,
    @SerializedName("status") val status: String,
    @SerializedName("timestamp") val timestamp: String
)

data class PredictionOverviewData(
    @SerializedName("total_predictions_generated") val totalPredictions: Long,
    @SerializedName("predictions_by_sport") val predictionsBySport: Map<String, Long>,
    @SerializedName("pending_evaluation") val pendingEvaluation: Long,
    @SerializedName("evaluated_results") val evaluatedResults: Long,
    @SerializedName("correct_results") val correctResults: Long,
    @SerializedName("incorrect_results") val incorrectResults: Long,
    @SerializedName("performance_metrics") val performanceMetrics: PerformanceMetrics
)

data class PerformanceMetrics(
    @SerializedName("mean_absolute_error") val mae: Double,
    @SerializedName("root_mean_squared_error") val rmse: Double,
    @SerializedName("accuracy_rate") val accuracyRate: String
)

data class IntelligenceOverviewData(
    @SerializedName("analysis_modules_status") val modulesStatus: String,
    @SerializedName("statistical_availability") val statisticalAvailability: String,
    @SerializedName("data_quality_and_age") val dataQuality: String,
    @SerializedName("modules_lacking_data") val modulesLackingData: List<String>,
    @SerializedName("sport_readiness") val sportReadiness: Map<String, String>
)
