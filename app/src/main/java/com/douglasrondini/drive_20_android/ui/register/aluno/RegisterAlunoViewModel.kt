package com.douglasrondini.drive_20_android.ui.register.aluno

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoRegister
import com.douglasrondini.drive_20_android.domain.home.aluno.RegisterAlunoUseCase
import com.douglasrondini.drive_20_android.domain.home.aluno.Role
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterAlunoViewModel(
    private val registerAlunoUseCase: RegisterAlunoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterAlunoUiState())
    val uiState: StateFlow<RegisterAlunoUiState> = _uiState.asStateFlow()

    // 1. Orquestrador Principal: Apenas coordena a execução das funções especializadas
    fun registerAluno(aluno: AlunoRegister) {
        _uiState.update { it.copy(isLoading = true) }
        setLoadingState(aluno.name,aluno.email, aluno.age, aluno.password, aluno.telefone, aluno.cpf, aluno.role)

        val alunoRegister = buildAlunoRegisterModel(aluno.name, aluno.email, aluno.age, aluno.password, aluno.telefone, aluno.cpf, aluno.role)

        executeRegisterUseCase(alunoRegister)
    }

    // 2. Responsabilidade: Atualizar a UI com os dados recebidos + Estado de Loading
    private fun setLoadingState(
        name: String,
        email: String,
        age: String,
        password: String,
        telefone: String,
        cpf: String,
        role: Role
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name,
                email = email,
                age = age,
                password = password,
                telefone = telefone,
                cpf = cpf,
                role = role,
                isLoading = true,
                errorMessage = null
            )
        }
    }

    // 3. Responsabilidade: Mapear/construir o objeto de domínio (Model Maper)
    private fun buildAlunoRegisterModel(
        name: String,
        email: String,
        age: String,
        password: String,
        telefone: String,
        cpf: String,
        role: Role
    ): AlunoRegister {
        return AlunoRegister(
            email = email,
            name = name,
            age = age,
            password = password,
            role = role,
            telefone = telefone,
            cpf = cpf
        )
    }

    // 4. Responsabilidade: Gerenciar a corrotina e a chamada do UseCase
    private fun executeRegisterUseCase(alunoRegister: AlunoRegister) {
        viewModelScope.launch {
            try {
                registerAlunoUseCase(alunoRegister)
                handleSuccess()
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    // 5. Responsabilidade: Tratar o estado de Sucesso
    private fun handleSuccess() {
        _uiState.update {
            it.copy(
                isLoading = false,
                isSuccess = true
            )
        }
    }

    // 6. Responsabilidade: Tratar o estado de Erro
    private fun handleError(throwable: Throwable) {
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = throwable.localizedMessage ?: "Erro ao realizar cadastro."
            )
        }
    }
}