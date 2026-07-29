package com.douglasrondini.drive_20_android.domain.repository

import com.douglasrondini.drive_20_android.domain.model.User

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<User>
}
