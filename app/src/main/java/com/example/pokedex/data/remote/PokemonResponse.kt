package com.example.pokedex.data.remote

import com.google.gson.annotations.SerializedName


data class PokedexResultResponse(
    @SerializedName("results") val pokedexs: List<PokedexResponse>
)

data class PokedexResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class PokemonResultResponse(
    @SerializedName("results") val pokemons: List<PokemonResponse>
)

data class PokemonResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

/*@JsonClass(generateAdapter = true)
data class PokedexResponse(
    @Json(name = "results") val pokemons : List<PokemonResponse>
)

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)*/
