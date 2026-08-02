package com.douglasrondini.drive_20_android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.domain.usecase.CreateAppointmentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalhesInstrutorUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class DetalhesInstrutorViewModel(
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalhesInstrutorUiState())
    val uiState: StateFlow<DetalhesInstrutorUiState> = _uiState.asStateFlow()

    fun createAppointment(instructorId: String, location: String, date: String, time: String, price: Double) {
        val studentId = preferenceManager.getUserId() ?: return
        
        // Formatar dataHora para o padrão ISO que a API espera
        // selectedDate está como dd/MM/yyyy e time como HH:mm
        val dateTimeIso = try {
            val dateParts = date.split("/")
            "${dateParts[2]}-${dateParts[1]}-${dateParts[0]}T$time:00.000Z"
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = "Formato de data inválido") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }

        viewModelScope.launch {
            createAppointmentUseCase(
                studentId = studentId,
                instructorId = instructorId,
                location = location,
                dateTime = dateTimeIso,
                price = price
            ).onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.localizedMessage) }
            }
        }
    }

    fun consumeSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun consumeError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
