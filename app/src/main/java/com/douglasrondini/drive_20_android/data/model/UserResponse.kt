package com.douglasrondini.drive_20_android.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: String,
    @SerializedName("nome") val name: String
)
