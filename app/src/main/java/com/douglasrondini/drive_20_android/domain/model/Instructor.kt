package com.douglasrondini.drive_20_android.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Instructor(
    val id: String,
    val name: String,
    val phone: String,
    val cnh: String,
    val plate: String,
    val isAvailable: Boolean,
    val price: Double?,
    val email: String
) : Parcelable
