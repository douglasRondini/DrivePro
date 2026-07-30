package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.home.instrutor.InstrutorRegister
import com.douglasrondini.drive_20_android.domain.repository.InstrutorRepository

class RegisterInstrutorUseCase(
    private val repository: InstrutorRepository
) {
    suspend operator fun invoke(instrutor: InstrutorRegister): Result<Unit> {
        return repository.registerInstrutor(instrutor)
    }
}
