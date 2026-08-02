package com.douglasrondini.drive_20_android.domain.model

data class Appointment(
    val id: String,
    val status: String,
    val localOrigem: String,
    val dataHora: String,
    val preco: Double,
    val partnerName: String,
    val partnerPhone: String
)
