package com.douglasrondini.drive_20_android.ui.home

import com.douglasrondini.drive_20_android.domain.model.Instructor

data class HomeAlunoUiState(
    val isLoading: Boolean = false,
    val instructors: List<Instructor> = emptyList(),
    val errorMessage: String? = null
)
