package com.douglasrondini.drive_20_android.data.model

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    @SerializedName("id") val id: String,
    @SerializedName("status") val status: String,
    @SerializedName("localOrigem") val localOrigem: String,
    @SerializedName("dataHora") val dataHora: String,
    @SerializedName("preco") val preco: Double,
    @SerializedName("alunoId") val alunoId: String,
    @SerializedName("instrutorId") val instrutorId: String,
    @SerializedName("criadoEm") val criadoEm: String,
    @SerializedName("aluno") val aluno: AlunoDto? = null,
    @SerializedName("instrutor") val instrutor: InstructorInfoDto? = null
)

data class AlunoDto(
    @SerializedName("id") val id: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("telefone") val telefone: String,
    @SerializedName("cpf") val cpf: String
)

data class InstructorInfoDto(
    @SerializedName("id") val id: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("telefone") val telefone: String,
    @SerializedName("cnh") val cnh: String,
    @SerializedName("placaVeiculo") val placaVeiculo: String,
    @SerializedName("disponivel") val disponivel: Boolean
)
