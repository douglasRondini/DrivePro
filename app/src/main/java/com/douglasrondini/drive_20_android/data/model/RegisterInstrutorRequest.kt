package com.douglasrondini.drive_20_android.data.model

import com.douglasrondini.drive_20_android.domain.home.instrutor.InstrutorRegister
import com.google.gson.annotations.SerializedName

data class RegisterInstrutorRequest(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("age") val age: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: String = "Instrutor",
    @SerializedName("telefone") val telefone: String,
    @SerializedName("cnh") val cnh: String,
    @SerializedName("placaVeiculo") val placaVeiculo: String
)

fun registerInstrutorFromToModel(domain: InstrutorRegister): RegisterInstrutorRequest {
    return RegisterInstrutorRequest(
        email = domain.email,
        name = domain.name,
        age = domain.age,
        password = domain.password,
        role = "Instrutor",
        telefone = domain.telefone,
        cnh = domain.cnh,
        placaVeiculo = domain.placaVeiculo
    )
}
