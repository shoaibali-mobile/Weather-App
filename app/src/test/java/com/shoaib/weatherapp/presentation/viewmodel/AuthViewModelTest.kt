package com.shoaib.weatherapp.presentation.viewmodel

import com.shoaib.weatherapp.domain.model.User
import com.shoaib.weatherapp.domain.usecase.LoginUseCase
import com.shoaib.weatherapp.domain.usecase.SignUpUseCase
import com.shoaib.weatherapp.presentation.model.AuthUiState
import com.shoaib.weatherapp.presentation.viewModel.auth.AuthViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class AuthViewModelTest {

    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var viewModel: AuthViewModel
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        signUpUseCase = mock()
        loginUseCase = mock()
        viewModel = AuthViewModel(signUpUseCase, loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSignUpSuccess() = runTest {

        val firstName = "shoaib"
        val lastName = "Ali"
        val email = "shoaib.ali@gmail.com"
        val password = "password123"

        whenever(signUpUseCase(firstName, lastName, email, password))
            .thenReturn(Result.success(1L))


        viewModel.signUp(firstName, lastName, email, password)
        advanceUntilIdle()


        val state = viewModel.authState.value
        assertTrue(state is AuthUiState.SignUpSuccess)
    }


    @Test
    fun testSignUpFailure() = runTest {

        val firstName = "shoaib"
        val lastName = "Ali"
        val email = "shoaib.ali@gmail.com"
        val password = "password123"

        whenever(signUpUseCase(firstName, lastName, email, password))
            .thenReturn(Result.failure<Long>(Exception("Sign up failed")))


        viewModel.signUp(firstName, lastName, email, password)
        advanceUntilIdle()


        val state = viewModel.authState.value
        assertTrue(state is AuthUiState.Error)
    }


    @Test
    fun testSignUpUserAlreadyExist() = runTest {
        val firstName = "shoaib"
        val lastName = "Ali"
        val email = "shoaib.ali@gmail.com"
        val password = "password123"

        whenever(signUpUseCase(firstName, lastName, email, password))
            .thenReturn(Result.failure<Long>(Exception("User Already Exists")))


        viewModel.signUp(firstName, lastName, email, password)
        advanceUntilIdle()


        val state = viewModel.authState.value
        assertTrue(state is AuthUiState.Error)
    }

    @Test
    fun testLoginSuccess() = runTest {

        val email = "shoaib.ali@gmail.com"
        val password = "password123"

        val user = User(
            id = 1L,
            firstName = "shoaib",
            lastName = "Ali",
            email = email,
            password = password
        )

        whenever(loginUseCase(email, password))
            .thenReturn(Result.success(user))

        // Act
        viewModel.login(email, password)
        advanceUntilIdle()

        // Assert
        val state = viewModel.authState.value
        assertTrue(state is AuthUiState.LoginSuccess)
    }


    @Test
    fun testLoginFailure() = runTest {

        val email = "shoaib.ali@gmail.com"
        val password = "password123"

        val user = User(
            id = 1L,
            firstName = "shoaib",
            lastName = "Ali",
            email = email,
            password = password
        )

        whenever(loginUseCase(email, password))
            .thenReturn(Result.failure<User>(Exception("Invalid credentials")))


        viewModel.login(email, password)
        advanceUntilIdle()

        // Assert
        val state = viewModel.authState.value
        assertTrue(state is AuthUiState.Error)
    }

}