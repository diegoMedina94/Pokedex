package com.example.pokedex.pokemonlist

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.data.models.PokedexListEntry
import com.example.pokedex.data.remote.responses.Result

@Composable
fun PokemonListRoute(
    pokedexViewModel: PokemonListViewModel,
    onNavigateToPokemonDetail: (pokemonId: String) -> Unit,
) {
    val pokemonState by pokedexViewModel.pokemonListUiState.collectAsStateWithLifecycle()
    PokemonListScreen(
        pokemonListUiState = pokemonState,
        onNavigateToPokemonDetail = {},
        getDominantColor = {
            pokedexViewModel.calcDominantColor(drawable = it)
        },
        onLoadPokemonPaginated = pokedexViewModel::loadPokemonPaginated
    )
}

@Composable
fun PokemonListScreen(
    pokemonListUiState: PokemonListUiState,
    onNavigateToPokemonDetail: (pokemonId: String) -> Unit,
    getDominantColor: (drawable: Drawable) -> Color?,
    onLoadPokemonPaginated: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

            }

            val listState = rememberLazyGridState()

            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemonListUiState.pokedexEntries) { pokedexListEntry ->
                    PokedexEntry(
                        entry = pokedexListEntry,
                        onItemClick = {},
                        pokemonDominantColor = getDominantColor
                    )
                }
            }

            if (
                listState.isScrolledToTheEnd() &&
                !pokemonListUiState.endReached &&
                !pokemonListUiState.isLoading
            ) {
                onLoadPokemonPaginated()
            }

        }
    }
}

fun LazyGridState.isScrolledToTheEnd(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}


private fun Result.toPokedexEntry(): PokedexListEntry {
    return PokedexListEntry(
        pokemonName = name,
        imageUrl = url,
        number = 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
) {
    var text by remember {
        mutableStateOf("")
    }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(it)
        },
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(color = Color.Black),
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar()
}

@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    onItemClick: (pokemon: String) -> Unit,
    pokemonDominantColor: (drawable: Drawable) -> Color?,
    modifier: Modifier = Modifier,
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                onItemClick(entry.pokemonName)
            }
    ) {
        Column {
            AsyncImage(
                model = entry.imageUrl,
                contentDescription = entry.pokemonName,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
            )

            ImageRequest.Builder(LocalContext.current)
                .data(entry.imageUrl)
                .target { drawable ->
                    dominantColor = pokemonDominantColor(drawable) ?: defaultDominantColor
                }
                .build()

            Text(
                text = entry.pokemonName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


