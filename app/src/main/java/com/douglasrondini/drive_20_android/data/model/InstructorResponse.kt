package com.douglasrondini.drive_20_android.data.model

import com.google.gson.annotations.SerializedName

data class InstructorResponse(
    @SerializedName("id") val id: String,
    @SerializedName("nome") val name: String,
    @SerializedName("telefone") val phone: String,
    @SerializedName("cnh") val cnh: String,
    @SerializedName("placaVeiculo") val plate: String,
    @SerializedName("disponivel") val isAvailable: Boolean,
    @SerializedName("precoAula") val price: Double?,
    @SerializedName("user") val user: UserInfoDto
)

data class UserInfoDto(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String
)
