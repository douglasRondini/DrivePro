package com.douglasrondini.drive_20_android.data.model

import com.google.gson.annotations.SerializedName

data class CreateAppointmentRequest(
    @SerializedName("alunoId") val studentId: String,
    @SerializedName("instrutorId") val instructorId: String,
    @SerializedName("localOrigem") val originLocation: String,
    @SerializedName("dataHora") val dateTime: String,
    @SerializedName("preco") val price: Double
)
