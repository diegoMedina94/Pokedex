package com.example.pokedex.domain

import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.util.Resource

interface PokedexRepository {
    suspend fun getPokedexList(limit: Int, offset: Int): Resource<PokemonList>
    suspend fun getPokemonDetails(pokemonName:String): Resource<Pokemon>
}