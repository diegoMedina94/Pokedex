package com.example.pokedex.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex.data.models.PokedexListEntry
import com.example.pokedex.domain.PokedexRepository
import com.example.pokedex.ui.navigation.PokemonDetails.pokemonId
import com.example.pokedex.util.Constants.PAGE_SIZE
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokedexRepository,
) : ViewModel() {

    private var curPage = 0
    private val _pokemonListUiState = MutableStateFlow(PokemonListUiState())
    val pokemonListUiState = _pokemonListUiState.asStateFlow()

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated() {
        _pokemonListUiState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            val result = repository.getPokedexList(
                limit = PAGE_SIZE,
                offset = curPage * PAGE_SIZE
            )
            when (result) {
                is Resource.Error -> {
                    _pokemonListUiState.update {
                        it.copy(
                            isLoading = false,
                            loadError = result.message ?: ""
                        )
                    }
                }

                is Resource.Idle -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    val isEndReached = curPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"
                        PokedexListEntry(
                            pokemonName = entry.name.capitalize(Locale.current),
                            imageUrl = url,
                            number = number.toInt()
                        )
                    }

                    val newList = _pokemonListUiState.value.pokedexEntries.toMutableList()
                    newList.addAll(pokedexEntries)

                    _pokemonListUiState.update {
                        it.copy(
                            isLoading = false,
                            pokedexEntries = newList,
                            endReached = isEndReached
                        )
                    }

                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable) : Color? {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(
            Bitmap.Config.ARGB_8888, true
        )
        var color : Color? = null
        Palette.from(bmp).generate { pallete ->
            pallete?.dominantSwatch?.rgb?.let { colorValue ->
               color = Color(colorValue)
            }
        }
        return color
    }

}



data class PokemonListUiState(
    val pokedexEntries: List<PokedexListEntry> = emptyList(),
    val loadError: String = "",
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
)