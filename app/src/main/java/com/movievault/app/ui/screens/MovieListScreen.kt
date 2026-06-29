package com.movievault.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.movievault.app.data.entity.MovieEntity
import com.movievault.app.data.entity.WatchStatus
import com.movievault.app.ui.components.CollectionEmptyState
import com.movievault.app.ui.components.EmptyState
import com.movievault.app.ui.components.ErrorState
import com.movievault.app.ui.components.LoadingState
import com.movievault.app.ui.components.MovieCard
import com.movievault.app.ui.components.MovieGridCard
import com.movievault.app.viewmodel.MovieListUiState
import com.movievault.app.viewmodel.MovieListViewModel
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel,
    onMovieClick: (Long) -> Unit,
    onAddMovieClick: () -> Unit,
    themeMode: String = "system",
    onToggleTheme: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var searchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showSortMenu by remember { mutableStateOf(false) }
    var showFilterMenu by remember { mutableStateOf(false) }
    val currentSort = (uiState as? MovieListUiState.Success)?.sortOrder ?: "date_desc"

    // Scroll to top when sort order changes
    val gridState = rememberLazyGridState()
    val listState = rememberLazyListState()

    // React to currentSort changes: scroll to top after data has updated
    LaunchedEffect(currentSort) {
        // Wait for the sorted data to appear in the layout
        snapshotFlow { gridState.layoutInfo.totalItemsCount > 0 || listState.layoutInfo.totalItemsCount > 0 }
            .first { it }
        try {
            gridState.scrollToItem(0)
        } catch (_: Exception) {}
        try {
            listState.scrollToItem(0)
        } catch (_: Exception) {}
    }

    // Helper to change sort order
    val onSortSelected: (String) -> Unit = { order ->
        viewModel.onSortOrderChange(order)
        showSortMenu = false
    }

    // Sync local search state with ViewModel when returning to main screen
    val stateSearchQuery = (uiState as? MovieListUiState.Success)?.searchQuery ?: ""
    LaunchedEffect(stateSearchQuery) {
        if (stateSearchQuery.isEmpty()) {
            searchQuery = ""
            searchExpanded = false
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("MovieVault") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    actions = {
                        // Search & Add movie button
                        IconButton(onClick = onAddMovieClick) {
                            Icon(Icons.Default.Add, contentDescription = "Search & Add Movie")
                        }
                        // Theme toggle button
                        IconButton(onClick = onToggleTheme) {
                            Icon(
                                imageVector = if (themeMode == "dark") Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (themeMode == "dark") "Switch to light mode" else "Switch to dark mode"
                            )
                        }
                        IconButton(onClick = {
                            searchExpanded = !searchExpanded
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = { showSortMenu = true }) {
                            Icon(Icons.Default.Sort, contentDescription = "Sort")
                            DropdownMenu(
                                expanded = showSortMenu,
                                onDismissRequest = { showSortMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Date (Newest)") },
                                    onClick = { onSortSelected("date_desc") },
                                    leadingIcon = {
                                        if (currentSort == "date_desc") {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Date (Oldest)") },
                                    onClick = { onSortSelected("date_asc") },
                                    leadingIcon = {
                                        if (currentSort == "date_asc") {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("IMDb Rating (High)") },
                                    onClick = { onSortSelected("rating_desc") },
                                    leadingIcon = {
                                        if (currentSort == "rating_desc") {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("IMDb Rating (Low)") },
                                    onClick = { onSortSelected("rating_asc") },
                                    leadingIcon = {
                                        if (currentSort == "rating_asc") {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("My Rating") },
                                    onClick = { onSortSelected("user_rating") },
                                    leadingIcon = {
                                        if (currentSort == "user_rating") {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                )
                            }
                        }
                        IconButton(onClick = { viewModel.onToggleViewMode() }) {
                            val isGrid = (uiState as? MovieListUiState.Success)?.isGridView ?: true
                            Icon(
                                if (isGrid) Icons.Default.ViewList else Icons.Default.GridView,
                                contentDescription = "Toggle view"
                            )
                        }
                    }
                )
                // Filter chips bar below TopAppBar
                val successState = uiState as? MovieListUiState.Success
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    data class FilterItem(val value: String, val label: String, val count: Int?)

                    val filters = listOf(
                        FilterItem("All", "All", successState?.totalCount ?: 0),
                        FilterItem("want_to_watch", "Want to Watch", successState?.wantToWatchCount ?: 0),
                        FilterItem("watching", "Watching", successState?.watchingCount ?: 0),
                        FilterItem("Favorites", "Favorites", null)
                    )
                    items(filters.size) { index ->
                        val filter = filters[index]
                        FilterChip(
                            selected = successState?.selectedFilter == filter.value,
                            onClick = { viewModel.onFilterChange(filter.value) },
                            label = {
                                Text(
                                    text = if (filter.count != null) "${filter.label} (${filter.count})" else filter.label,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (successState?.selectedFilter == filter.value) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMovieClick,
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Movie")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search bar
            if (searchExpanded) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.onSearchQueryChange(it)
                    },
                    placeholder = { Text("Search movies...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = {
                            searchQuery = ""
                            viewModel.onSearchQueryChange("")
                            searchExpanded = false
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Close search")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    singleLine = true
                )
            }

            // Content
            when (val state = uiState) {
                is MovieListUiState.Loading -> {
                    LoadingState()
                }
                is MovieListUiState.Error -> {
                    ErrorState(message = state.message)
                }
                is MovieListUiState.Success -> {
                    if (state.movies.isEmpty()) {
                        if (state.searchQuery.isNotBlank()) {
                            EmptyState(
                                title = "No movies found",
                                subtitle = "Try a different search term"
                            )
                        } else {
                            CollectionEmptyState(onAddMovie = onAddMovieClick)
                        }
                    } else {
                        if (state.isGridView) {
                            LazyVerticalGrid(
                                state = gridState,
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(state.movies, key = { it.id }) { movie ->
                                    MovieGridCard(
                                        movie = movie,
                                        onClick = { onMovieClick(movie.id) },
                                        onFavoriteClick = { viewModel.onToggleFavorite(movie) }
                                    )
                                }
                            }
                        } else {
                            LazyColumn(
                                state = listState,
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(state.movies, key = { it.id }) { movie ->
                                    MovieCard(
                                        movie = movie,
                                        onClick = { onMovieClick(movie.id) },
                                        onFavoriteClick = { viewModel.onToggleFavorite(movie) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
