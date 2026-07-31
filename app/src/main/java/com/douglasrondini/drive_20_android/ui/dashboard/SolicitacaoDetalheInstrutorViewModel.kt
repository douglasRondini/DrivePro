package com.douglasrondini.drive_20_android.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.domain.usecase.AcceptAppointmentUseCase
import com.douglasrondini.drive_20_android.domain.usecase.CancelAppointmentUseCase
import com.douglasrondini.drive_20_android.domain.usecase.CompleteAppointmentUseCase
import com.douglasrondini.drive_20_android.domain.usecase.RefuseAppointmentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class SolicitacaoDetalheInstrutorViewModel(
    private val acceptAppointmentUseCase: AcceptAppointmentUseCase,
    private val refuseAppointmentUseCase: RefuseAppointmentUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase,
    private val completeAppointmentUseCase: CompleteAppointmentUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun accept(appointmentId: String) {
        val instructorId = preferenceManager.getUserId() ?: return
        executeAction { acceptAppointmentUseCase(appointmentId, instructorId) }
    }

    fun refuse(appointmentId: String) {
        executeAction { refuseAppointmentUseCase(appointmentId) }
    }

    fun cancel(appointmentId: String) {
        executeAction { cancelAppointmentUseCase(appointmentId) }
    }

    fun complete(appointmentId: String) {
        executeAction { completeAppointmentUseCase(appointmentId) }
    }

    private fun executeAction(action: suspend () -> Result<Unit>) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }
        viewModelScope.launch {
            action().onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.localizedMessage) }
            }
        }
    }
}
