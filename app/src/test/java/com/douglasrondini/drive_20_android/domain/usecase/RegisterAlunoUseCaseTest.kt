package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoRegister
import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoValidationException
import com.douglasrondini.drive_20_android.domain.home.aluno.RegisterAlunoUseCase
import com.douglasrondini.drive_20_android.domain.home.aluno.Role
import com.douglasrondini.drive_20_android.domain.repository.AlunoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterAlunoUseCaseTest {

    private val repository: AlunoRepository = mockk()
    private val useCase = RegisterAlunoUseCase(repository)

    @Test
    fun `when all fields are valid, should return success`() = runTest {
        val aluno = AlunoRegister(
            email = "test@test.com",
            name = "Test",
            age = "25",
            password = "password",
            role = Role.ALUNO,
            telefone = "123456789",
            cpf = "12345678900"
        )

        coEvery { repository.registerAluno(aluno) } returns Result.success(Unit)

        val result = useCase(aluno)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `when name is blank, should return failure with EmptyName exception`() = runTest {
        val aluno = AlunoRegister(
            email = "test@test.com",
            name = "",
            age = "25",
            password = "password",
            role = Role.ALUNO,
            telefone = "123456789",
            cpf = "12345678900"
        )

        val result = useCase(aluno)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AlunoValidationException.EmptyName)
    }

    @Test
    fun `when email is blank, should return failure with EmptyEmail exception`() = runTest {
        val aluno = AlunoRegister(
            email = "",
            name = "Test",
            age = "25",
            password = "password",
            role = Role.ALUNO,
            telefone = "123456789",
            cpf = "12345678900"
        )

        val result = useCase(aluno)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is AlunoValidationException.EmptyEmail)
    }
}
