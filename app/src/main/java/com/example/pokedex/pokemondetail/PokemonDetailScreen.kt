package com.example.pokedex.pokemondetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PokemonDetailRoute(
    pokemonName: String,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val pokemonDetailUiState by viewModel.pokemonDetailUiState.collectAsStateWithLifecycle()
    PokemonDetailScreen(
        pokemonName = pokemonName,
        onNavigateBack = onNavigateBack,
        pokemonDetailUiState = pokemonDetailUiState
    )
}

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    onNavigateBack: () -> Unit,
    topPadding: Dp = 200.dp,
    pokemonDetailUiState: PokemonDetailUiState,
) {

}