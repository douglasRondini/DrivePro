package com.douglasrondini.drive_20_android.domain.home.instrutor

import com.douglasrondini.drive_20_android.domain.home.aluno.Role

data class InstrutorRegister(
    val email: String,
    val name: String,
    val age: String,
    val password: String,
    val role: Role = Role.INSTRUTOR,
    val telefone: String,
    val cnh: String,
    val placaVeiculo: String
)
