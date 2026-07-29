package com.douglasrondini.drive_20_android.domain.home.aluno

sealed class AlunoValidationException(override val message: String) : IllegalArgumentException(message) {
    object EmptyName : AlunoValidationException("O nome não pode estar vazio.")
    object EmptyEmail : AlunoValidationException("O e-mail não pode estar vazio.")
    object EmptyAge : AlunoValidationException("A idade não pode estar vazia.")
    object EmptyPassword : AlunoValidationException("A senha não pode estar vazia.")
    object EmptyTelefone : AlunoValidationException("O telefone não pode estar vazio.")
    object EmptyCpf : AlunoValidationException("O CPF não pode estar vazio.")
}