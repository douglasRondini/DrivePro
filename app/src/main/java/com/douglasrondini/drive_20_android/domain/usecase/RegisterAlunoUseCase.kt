package com.douglasrondini.drive_20_android.domain.home.aluno

import com.douglasrondini.drive_20_android.domain.repository.AlunoRepository

class RegisterAlunoUseCase(
    private val repository: AlunoRepository
) {

    suspend operator fun invoke(aluno: AlunoRegister): Result<Unit> {
        return runCatching {
            validateFields(aluno)
            repository.registerAluno(aluno).fold(
                onSuccess = { Unit ->
                    return Result.success(Unit)
                },
                onFailure = {
                    return  Result.failure(it)
                }
            )
        }
    }

    // Responsabilidade Única: Validar se todos os campos do modelo estão preenchidos
    private fun validateFields(aluno: AlunoRegister) {
        when {
            aluno.name.isBlank() -> throw AlunoValidationException.EmptyName
            aluno.email.isBlank() -> throw AlunoValidationException.EmptyEmail
            aluno.age.isBlank() -> throw AlunoValidationException.EmptyAge
            aluno.password.isBlank() -> throw AlunoValidationException.EmptyPassword
            aluno.telefone.isBlank() -> throw AlunoValidationException.EmptyTelefone
            aluno.cpf.isBlank() -> throw AlunoValidationException.EmptyCpf
        }
    }
}