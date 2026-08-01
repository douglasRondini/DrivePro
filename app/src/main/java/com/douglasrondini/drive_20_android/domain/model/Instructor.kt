package com.douglasrondini.drive_20_android.domain.model

data class Instructor(
    val id: String,
    val name: String,
    val phone: String,
    val plate: String,
    val isAvailable: Boolean,
    val email: String
)
