package com.douglasrondini.drive_20_android.ui.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val userRole: String? = null,
    val errorMessage: String? = null
)
