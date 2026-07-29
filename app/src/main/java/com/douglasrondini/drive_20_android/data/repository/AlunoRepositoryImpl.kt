package com.douglasrondini.drive_20_android.data.repository

import com.douglasrondini.drive_20_android.data.model.registerAlunoFromToModel
import com.douglasrondini.drive_20_android.data.remote.ApiService
import com.douglasrondini.drive_20_android.data.remote.helper.BaseRepository
import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoRegister
import com.douglasrondini.drive_20_android.domain.repository.AlunoRepository

class AlunoRepositoryImpl(
    private val apiService: ApiService
) : AlunoRepository, BaseRepository() {

    override suspend fun registerAluno(aluno: AlunoRegister): Result<Unit> {
        return safeApiCallUnit {
            apiService.registerAluno(registerAlunoFromToModel(aluno))
        }
    }
}
