package com.douglasrondini.drive_20_android.domain.repository

import com.douglasrondini.drive_20_android.domain.home.instrutor.InstrutorRegister

interface InstrutorRepository {
    suspend fun registerInstrutor(instrutor: InstrutorRegister): Result<Unit>
}
