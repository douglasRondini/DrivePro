package com.douglasrondini.drive_20_android.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.domain.model.Appointment
import com.douglasrondini.drive_20_android.domain.usecase.GetInstructorAppointmentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardInstrutorViewModel(
    private val getInstructorAppointmentsUseCase: GetInstructorAppointmentsUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardInstrutorUiState())
    val uiState: StateFlow<DashboardInstrutorUiState> = _uiState.asStateFlow()

    private var allAppointments: List<Appointment> = emptyList()

    init {
        loadAppointments()
    }

    fun loadAppointments() {
        val instructorId = preferenceManager.getUserId() ?: return
        
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            getInstructorAppointmentsUseCase(instructorId)
                .onSuccess { list ->
                    allAppointments = list
                    _uiState.update { it.copy(isLoading = false, appointments = list) }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = error.localizedMessage ?: "Erro ao carregar agendamentos"
                        ) 
                    }
                }
        }
    }

    fun filterByStatus(status: String) {
        val filteredList = if (status.uppercase() == "TODAS") {
            allAppointments
        } else {
            allAppointments.filter { it.status.uppercase() == status.uppercase() }
        }
        _uiState.update { it.copy(appointments = filteredList) }
    }
}
