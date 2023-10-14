package com.example.pokedex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.domain.PokedexRepository
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val repository: PokedexRepository,
) : ViewModel() {
    private val _pokedexUiState = MutableStateFlow(
        PokedexUiState(
            pokemons = Resource.Idle()
        )
    )
    val pokedexUiState: StateFlow<PokedexUiState> = _pokedexUiState.asStateFlow()

    init {
        getPokemons()
    }

    private fun getPokemons() {
        viewModelScope.launch {
            _pokedexUiState.update {
                it.copy(isLoading = true)
            }
            val pokemons = repository.getPokedexList(20, 0)
            _pokedexUiState.update {
                it.copy(
                    pokemons = pokemons,
                    isLoading = false
                )
            }
        }
    }
}

class PokedexViewModelFactory(
    private val repository: PokedexRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokedexViewModel(repository = repository) as T
    }
}

data class PokedexUiState(
    val pokemons: Resource<PokemonList>,
    val isLoading: Boolean = false,
)

