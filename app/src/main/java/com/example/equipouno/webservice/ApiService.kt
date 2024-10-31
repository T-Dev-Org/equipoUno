package com.example.equipouno.webservice

import com.example.equipouno.model.PokemonModelResponse
import com.example.equipouno.utils.Constants.END_POINT
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(END_POINT)
    suspend fun getProducts(): MutableList<PokemonModelResponse>

    @GET("${END_POINT}/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): PokemonModelResponse

}