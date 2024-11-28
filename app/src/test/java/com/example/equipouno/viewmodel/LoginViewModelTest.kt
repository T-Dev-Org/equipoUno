package com.example.equipouno.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.equipouno.repository.LoginRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockLoginRepository: LoginRepository

    @Mock
    lateinit var mockObserver: Observer<Boolean>

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        loginViewModel = LoginViewModel(mockLoginRepository)
    }

    @Test
    fun `validatePassword success`() {
        //Given
        val password = "123456"

        // when
        loginViewModel.isPasswordValid.observeForever(mockObserver)
        loginViewModel.validatePassword(password)

        // then
        assertTrue(loginViewModel.isPasswordValid.value == true)
    }

    @Test
    fun `validatePassword failure`() {
        // Given
        val password = "123"

        // When
        loginViewModel.isPasswordValid.observeForever(mockObserver)
        loginViewModel.validatePassword(password)

        // Then
        assertFalse(loginViewModel.isPasswordValid.value == true)
    }

    @Test
    fun `sesion invalid email`() {
        //Given
        val email: String? = null
        var isViewEnabled = true

        //When
        loginViewModel.sesion(email) { isEnabled ->
            isViewEnabled = isEnabled
        }

        //Then
        assertFalse(isViewEnabled)
    }

    @Test
    fun `sesion success`() {
        //Given
        val email = "user@gmail.com"
        var isViewEnabled = false

        //When
        loginViewModel.sesion(email) { isEnabled ->
            isViewEnabled = isEnabled
        }

        //Then
        assertTrue(isViewEnabled)
    }

    @Test
    fun `test loginUser failure`() = runBlocking {
        //Given
        var email = ""
        var passowrd = ""
        var isLoginResult: Boolean? = null

        //When
        loginViewModel.loginUser(email , passowrd) { isSuccess ->
            isLoginResult = isSuccess
        }

        // Then
        assert(isLoginResult == false)
    }
}