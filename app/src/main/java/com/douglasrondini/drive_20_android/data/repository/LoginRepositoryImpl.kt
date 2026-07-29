package com.douglasrondini.drive_20_android.data.repository

import com.douglasrondini.drive_20_android.data.model.LoginRequest
import com.douglasrondini.drive_20_android.data.remote.ApiService
import com.douglasrondini.drive_20_android.data.remote.helper.BaseRepository
import com.douglasrondini.drive_20_android.domain.model.User
import com.douglasrondini.drive_20_android.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val apiService: ApiService
) : LoginRepository, BaseRepository() {

    override suspend fun login(email: String, password: String): Result<User> {
        return safeApiCall(
            apiCall = { apiService.login(LoginRequest(email, password)) },
            transform = { response ->
                User(
                    id = response.user.id,
                    name = response.user.name,
                    email = response.user.email,
                    role = response.user.role,
                    token = response.token
                )
            }
        )
    }
}
