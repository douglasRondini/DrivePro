package com.douglasrondini.drive_20_android.ui.register.instrutor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.drive_20_android.domain.home.instrutor.InstrutorRegister
import com.douglasrondini.drive_20_android.domain.usecase.RegisterInstrutorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterInstrutorViewModel(
    private val registerInstrutorUseCase: RegisterInstrutorUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterInstrutorUiState())
    val uiState: StateFlow<RegisterInstrutorUiState> = _uiState.asStateFlow()

    fun registerInstrutor(instrutor: InstrutorRegister) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            registerInstrutorUseCase(instrutor)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = error.localizedMessage ?: "Erro ao realizar cadastro de instrutor"
                        ) 
                    }
                }
        }
    }
}
