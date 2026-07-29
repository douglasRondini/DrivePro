package com.douglasrondini.drive_20_android.ui.register.aluno

import com.douglasrondini.drive_20_android.domain.home.aluno.Role

data class RegisterAlunoUiState(
    val email: String = "",
    val name: String = "",
    val age: String = "",
    val password: String = "",
    val telefone: String = "",
    val cpf: String = "",
    val role: Role = Role.ALUNO,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)