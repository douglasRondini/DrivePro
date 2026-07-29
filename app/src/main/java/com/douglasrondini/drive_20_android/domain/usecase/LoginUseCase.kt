package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.domain.model.User
import com.douglasrondini.drive_20_android.domain.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository,
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.login(email, password).onSuccess { user ->
            preferenceManager.saveUserToken(user.token)
            preferenceManager.saveUserData(
                id = user.id,
                name = user.name,
                email = user.email,
                role = user.role
            )
        }
    }
}
