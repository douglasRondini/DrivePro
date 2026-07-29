package com.douglasrondini.drive_20_android.domain.home.aluno

import com.google.gson.annotations.SerializedName

data class AlunoRegister(
    @SerializedName("email")
    val email: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("age")
    val age: String, // 👈 Mantido como String ("20")

    @SerializedName("password")
    val password: String,

    @SerializedName("role")
    val role: Role,

    @SerializedName("telefone")
    val telefone: String,

    @SerializedName("cpf")
    val cpf: String
)

enum class Role {
    @SerializedName("Aluno")
    ALUNO,

    @SerializedName("Instrutor")
    INSTRUTOR,

    @SerializedName("Admin")
    ADMIN
}