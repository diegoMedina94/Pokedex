package com.example.pokedex.ui.pokemons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.ui.PokedexUiState
import com.example.pokedex.ui.PokedexViewModel


@Composable
fun PokemonRoute(
    pokedexViewModel: PokedexViewModel,
    onNavigateToPokemonDetail: (pokemonId: String) -> Unit,
) {
    val pokemons by pokedexViewModel.pokedexUiState.collectAsStateWithLifecycle()

    PokemonsScreen(
        pokemons,
        onNavigateToPokemonDetail
    )
}

@Composable
fun PokemonsScreen(
    pokemons: PokedexUiState,
    onNavigateToPokemonDetail: (pokemonId: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            pokemons.pokemons.data?.results?.let { pokemons ->
                items(pokemons) {
                    PokemonView(pokemon = it.toPokemon()) { pokemonId ->
                        onNavigateToPokemonDetail(pokemonId)
                    }
                }
            }
        }
    }
}

fun com.example.pokedex.data.remote.responses.Result.toPokemon() : com.example.pokedex.domain.Pokemon {
    return com.example.pokedex.domain.Pokemon(
        this.name,
        this.url
    )
}