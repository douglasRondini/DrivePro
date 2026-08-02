package com.douglasrondini.drive_20_android.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.domain.usecase.UpdateUnitPriceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PerfilInstrutorUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class PerfilInstrutorViewModel(
    private val updateUnitPriceUseCase: UpdateUnitPriceUseCase,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilInstrutorUiState())
    val uiState: StateFlow<PerfilInstrutorUiState> = _uiState.asStateFlow()

    fun getInitialPrice() = preferenceManager.getClassPrice()

    fun updatePrice(price: Double) {
        val instructorId = preferenceManager.getUserId() ?: return
        
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }
        
        viewModelScope.launch {
            updateUnitPriceUseCase(instructorId, price)
                .onSuccess {
                    preferenceManager.saveClassPrice(price)
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.localizedMessage) }
                }
        }
    }

    fun resetSuccessState() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun consumeErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
