package com.douglasrondini.drive_20_android.ui.solicitações

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.domain.model.Appointment
import com.douglasrondini.drive_20_android.domain.usecase.GetStudentAppointmentsUseCase
import com.douglasrondini.drive_20_android.ui.dashboard.DashboardInstrutorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeAlunoSolicitacoesViewModel(
    private val getStudentAppointmentsUseCase: GetStudentAppointmentsUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardInstrutorUiState()) // Reusing the same UI state structure
    val uiState: StateFlow<DashboardInstrutorUiState> = _uiState.asStateFlow()

    private var allAppointments: List<Appointment> = emptyList()

    init {
        loadAppointments()
    }

    fun loadAppointments() {
        val studentId = preferenceManager.getUserId() ?: return
        
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            getStudentAppointmentsUseCase(studentId)
                .onSuccess { list ->
                    allAppointments = list
                    _uiState.update { it.copy(isLoading = false, appointments = list) }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = error.localizedMessage ?: "Erro ao carregar solicitações"
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
