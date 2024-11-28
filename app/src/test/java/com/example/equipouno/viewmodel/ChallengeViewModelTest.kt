package com.example.equipouno.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.equipouno.model.Challenge
import com.example.equipouno.repository.ChallengeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class ChallengeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule() //código que involucra LiveData y ViewModel
    private lateinit var viewModel: ChallengeViewModel
    private lateinit var repository: ChallengeRepository

    @Before
    fun setUp() {
        repository = mock(ChallengeRepository::class.java)
        viewModel = ChallengeViewModel(repository)
    }

    @Test
    fun testSaveChallenge_success(): Unit = runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        //Given
        val challange = Challenge("1","Prueba")

        //When
        viewModel.saveChallenge(challange)

        // Then
        verify(repository).insertChallenge(challange)
    }

    @Test
    fun testSaveChallenge_fail(): Unit = runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        // Given
        val challenge = Challenge("1", "Prueba")

        // Hacemos que el repositorio falle al insertar el desafío
        `when`(repository.insertChallenge(challenge)).thenThrow(RuntimeException("Insert failed"))

        // When
        viewModel.saveChallenge(challenge)

        // Then: La verificación fallará, porque esperamos que el desafío se inserte correctamente.
        verify(repository).insertChallenge(challenge)
    }

    @Test
    fun testDeleteChallenge_success() = runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        // Given
        val challenge = Challenge("1", "Prueba a Eliminar")

        // Simula que el repositorio elimina correctamente el desafío con el ID dado
        `when`(repository.deleteChallenge(challenge.id)).thenReturn(true)

        // When
        viewModel.deleteChallenge(challenge)

        // Then
        // Verificamos que `deleteChallenge` del repositorio fue llamado con el ID adecuado
        verify(repository).deleteChallenge(challenge.id)
        // Verificamos que el estado de progreso cambió a `false` después de la operación
        assertFalse(viewModel.progressState.value == true)
    }

    @Test
    fun testDeleteChallenge_failure() = runBlocking {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        // Given
        val challenge = Challenge("1", "Prueba Fallida")

        // Simulamos que el repositorio lanza una excepción al intentar eliminar el desafío
        `when`(repository.deleteChallenge(challenge.id)).thenThrow(RuntimeException("Delete failed"))

        // When
        viewModel.deleteChallenge(challenge)

        // Then
        // Verificamos que `deleteChallenge` del repositorio fue llamado con el desafío adecuado
        verify(repository).deleteChallenge(challenge.id)
        // Verificamos que el estado de progreso haya sido actualizado a `false` después de la excepción
        assertFalse(viewModel.progressState.value == true)
    }



}