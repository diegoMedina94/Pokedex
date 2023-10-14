package com.example.pokedex.data.remote

import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.domain.PokedexRepository
import com.example.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val api: PokedexApi,
) : PokedexRepository {
    override suspend fun getPokedexList(limit: Int, offset: Int): Resource<PokemonList> {
        return try {
            Resource.Success(api.getPokemonList(limit,offset))
        } catch (e:Exception){
            Resource.Error("An unknown error ocurred")
        }
        /*return result.pokemons.map {
            val pokemonId = it.url.split("/").dropLastWhile { it.isEmpty() }.last()
            Pokemon(
                it.name,
                imageUrl =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
            )
        }*/
    }

    override suspend fun getPokemonDetails(pokemonName:String): Resource<Pokemon> {
        return try {
            Resource.Success(api.getPokemonInfo(pokemonName))
        } catch (e:Exception){
            Resource.Error("An unknown error ocurred")
        }
    }
}