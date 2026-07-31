package com.douglasrondini.drive_20_android.data.model

import com.google.gson.annotations.SerializedName

data class AcceptAppointmentRequest(
    @SerializedName("instrutorId") val instructorId: String
)
