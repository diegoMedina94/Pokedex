package com.example.pokedex.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pokedex.pokemonlist.PokemonListRoute
import com.example.pokedex.ui.pokemons.PokemonRoute

const val POKEMON_GRAPH_START_DESTINATION = "pokemonsScreen"
const val POKEMON_GRAPH_ROUTE = "pokemon"

object PokemonsListRoute : NestedDestination() {
    override val route: String
        get() = "pokemonsScreen"
}

object PokemonDetails : NestedDestination() {
    override val route: String
        get() = "pokemonDetails/{${pokemonId}}"

    const val pokemonId = "pokemonId"
}

fun NavGraphBuilder.pokemonNestedGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = POKEMON_GRAPH_START_DESTINATION,
        route = POKEMON_GRAPH_ROUTE
    ) {
        composable(PokemonsListRoute.route) {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                PokemonListRoute(
                    pokedexViewModel = hiltViewModel()
                ){

                }

                /*PokemonRoute(
                    pokedexViewModel = hiltViewModel(),
                    onNavigateToPokemonDetail = {
                        navController.navigate(
                            PokemonDetails.getRouteWithArgs(
                                Pair(
                                    PokemonDetails.pokemonId, it
                                )
                            )
                        )
                    })*/
            }
        }

        composable(PokemonDetails.route) {
            val pokemonId = it.arguments?.getString(PokemonDetails.pokemonId)
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "THIS is a NEW SCREEN $pokemonId"
            )
        }
    }
}


