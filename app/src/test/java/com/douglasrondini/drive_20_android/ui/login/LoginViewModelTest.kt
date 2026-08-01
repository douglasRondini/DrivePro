package com.douglasrondini.drive_20_android.ui.login

import app.cash.turbine.test
import com.douglasrondini.drive_20_android.domain.model.User
import com.douglasrondini.drive_20_android.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private val loginUseCase: LoginUseCase = mockk()
    private lateinit var viewModel: LoginViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when login is called and succeeds, should update uiState to success`() = runTest {
        val user = User("1", "Douglas", "doug@email.com", "Aluno", "token123")
        coEvery { loginUseCase("doug@email.com", "123456") } returns Result.success(user)

        viewModel.uiState.test {
            assertEquals(LoginUiState(), awaitItem()) // Initial state

            viewModel.login("doug@email.com", "123456")

            val loadingItem = awaitItem()
            assertTrue(loadingItem.isLoading)

            val successItem = awaitItem()
            assertTrue(successItem.isSuccess)
            assertEquals("Aluno", successItem.userRole)
        }
    }

    @Test
    fun `when login is called and fails, should update uiState with error message`() = runTest {
        coEvery { loginUseCase("error@email.com", "123") } returns Result.failure(Exception("Unauthorized"))

        viewModel.uiState.test {
            assertEquals(LoginUiState(), awaitItem())

            viewModel.login("error@email.com", "123")

            val loadingItem = awaitItem()
            assertTrue(loadingItem.isLoading)

            val errorItem = awaitItem()
            assertEquals("Unauthorized", errorItem.errorMessage)
            assertEquals(false, errorItem.isLoading)
        }
    }
}
