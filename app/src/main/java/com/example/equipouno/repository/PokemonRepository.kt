package com.example.equipouno.repository

import com.example.equipouno.model.PokemonModelResponse
import com.example.equipouno.webservice.ApiUtils
import kotlin.random.Random

class PokemonRepository {
    private val apiService = ApiUtils.getApiService()

    suspend fun getRandomPokemon(): PokemonModelResponse {
        val randomId = Random.nextInt(1, 899)
        return apiService.getPokemonById(randomId)
    }
}