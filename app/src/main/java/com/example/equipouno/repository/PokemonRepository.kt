package com.example.equipouno.repository

import com.example.equipouno.model.PokemonModelResponse
import com.example.equipouno.webservice.ApiService
import kotlin.random.Random
import javax.inject.Inject

class PokemonRepository @Inject constructor
    (
    private val apiService: ApiService
) {
    suspend fun getRandomPokemon(): PokemonModelResponse {
        val randomId = Random.nextInt(1, 150)
        return apiService.getPokemonById(randomId)
    }
}
