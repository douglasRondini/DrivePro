package com.douglasrondini.drive_20_android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.drive_20_android.domain.usecase.GetAvailableInstructorsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeAlunoViewModel(
    private val getAvailableInstructorsUseCase: GetAvailableInstructorsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeAlunoUiState())
    val uiState: StateFlow<HomeAlunoUiState> = _uiState.asStateFlow()

    init {
        loadInstructors()
    }

    fun loadInstructors() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            getAvailableInstructorsUseCase()
                .onSuccess { list ->
                    _uiState.update { it.copy(isLoading = false, instructors = list) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.localizedMessage) }
                }
        }
    }
}
