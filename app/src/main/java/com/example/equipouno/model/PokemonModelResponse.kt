package com.example.equipouno.model

import com.google.gson.annotations.SerializedName

data class PokemonModelResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val title: String,

    @SerializedName("sprites")
    val sprites: Sprites
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String
)