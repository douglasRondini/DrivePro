package com.douglasrondini.drive_20_android.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user") val user: UserDto,
    @SerializedName("token") val token: String
)

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("age") val age: String,
    @SerializedName("role") val role: String
)
