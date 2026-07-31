package com.douglasrondini.drive_20_android.ui.dashboard

import com.douglasrondini.drive_20_android.domain.model.Appointment

data class DashboardInstrutorUiState(
    val isLoading: Boolean = false,
    val appointments: List<Appointment> = emptyList(),
    val completedCount: Int = 0,
    val pendingCount: Int = 0,
    val cancelledCount: Int = 0,
    val totalRevenue: Double = 0.0,
    val totalRequests: Int = 0,
    val acceptedCount: Int = 0,
    val errorMessage: String? = null
)
