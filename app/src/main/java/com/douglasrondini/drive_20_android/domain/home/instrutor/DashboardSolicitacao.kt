package com.douglasrondini.drive_20_android.domain.home.instrutor

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class DashboardSolicitacao(
    val nome: String,
    val info: String,
    val dataSolicitada: String,
    val horario: String,
    val contato: String,
    val status: String,
    @ColorRes val statusBgColor: Int,
    @ColorRes val statusTextColor: Int,
    @DrawableRes val avatarRes: Int
)

