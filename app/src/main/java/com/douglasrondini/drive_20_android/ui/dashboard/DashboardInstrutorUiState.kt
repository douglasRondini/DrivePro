package com.douglasrondini.drive_20_android.ui.dashboard

import com.douglasrondini.drive_20_android.domain.model.Appointment

data class DashboardInstrutorUiState(
    val isLoading: Boolean = false,
    val appointments: List<Appointment> = emptyList(),
    val errorMessage: String? = null
)
