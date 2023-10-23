package com.example.pokedex.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.domain.PokedexRepository
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokedexRepository,
) : ViewModel() {

    private val _pokemonDetailUiState = MutableStateFlow(PokemonDetailUiState())
    val pokemonDetailUiState = _pokemonDetailUiState.asStateFlow()
    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            val result = repository.getPokemonDetails(pokemonName)
            when (result) {
                is Resource.Error -> {}
                is Resource.Idle -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _pokemonDetailUiState.update {
                        it.copy(
                            pokemon = result.data
                        )
                    }
                }
            }
        }
    }
}

data class PokemonDetailUiState(
    val pokemon: Pokemon? = null,
)