package com.douglasrondini.drive_20_android.domain.repository

import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoRegister

interface AlunoRepository {
    suspend fun registerAluno(aluno: AlunoRegister): Result<Unit>
}