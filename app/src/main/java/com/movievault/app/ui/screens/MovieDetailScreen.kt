package com.movievault.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.movievault.app.data.entity.MovieEntity
import com.movievault.app.data.entity.ReviewEntity
import com.movievault.app.data.entity.WatchStatus
import com.movievault.app.ui.components.EmptyState
import com.movievault.app.ui.components.ErrorState
import com.movievault.app.ui.components.LoadingState
import com.movievault.app.ui.theme.StarColor
import com.movievault.app.viewmodel.MovieDetailUiState
import com.movievault.app.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showReviewDialog by remember { mutableStateOf(false) }
    var showStatusMenu by remember { mutableStateOf(false) }
    var reviewContent by remember { mutableStateOf("") }
    var reviewRating by remember { mutableFloatStateOf(3f) }

    // Handle movie deletion
    LaunchedEffect(Unit) {
        // Track if we need to navigate back after deletion
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = (uiState as? MovieDetailUiState.Success)?.movie?.title ?: "MovieVault"
                    Text(title, maxLines = 1)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when (val state = uiState) {
            is MovieDetailUiState.Loading -> {
                LoadingState(modifier = Modifier.padding(paddingValues))
            }
            is MovieDetailUiState.Error -> {
                ErrorState(
                    message = state.message,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is MovieDetailUiState.Success -> {
                val movie = state.movie

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Poster
                    item {
                        AsyncImage(
                            model = movie.posterUrl,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Movie info card
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Title and year
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = (-0.5).sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                // IMDb rating
                                if (movie.imdbRating.isNotBlank() && movie.imdbRating != "N/A") {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = StarColor,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "${movie.imdbRating}/10",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Info rows
                                InfoRow("Year", movie.year)
                                InfoRow("Runtime", movie.runtime)
                                InfoRow("Genre", movie.genre)
                                InfoRow("Director", movie.director)
                                InfoRow("Cast", movie.actors.take(100))

                                Spacer(modifier = Modifier.height(8.dp))

                                // Plot
                                if (movie.plot.isNotBlank()) {
                                    Text(
                                        text = "Plot",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = movie.plot,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    // Actions card
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Actions",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                // Favorite toggle
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { viewModel.onToggleFavorite() }) {
                                        Icon(
                                            if (movie.isFavorite) Icons.Default.Favorite
                                            else Icons.Default.FavoriteBorder,
                                            contentDescription = "Favorite",
                                            tint = if (movie.isFavorite) MaterialTheme.colorScheme.secondary
                                            else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Text(
                                        if (movie.isFavorite) "Favorited" else "Add to Favorites",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                // Watch status
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box {
                                        TextButton(onClick = { showStatusMenu = true }) {
                                            val statusText = when (movie.watchStatus) {
                                                WatchStatus.WATCHED -> "✓ Watched"
                                                WatchStatus.WATCHING -> "▶ Watching"
                                                else -> "📋 Want to Watch"
                                            }
                                            Text("Status: $statusText")
                                        }
                                        DropdownMenu(
                                            expanded = showStatusMenu,
                                            onDismissRequest = { showStatusMenu = false }
                                        ) {
                                            DropdownMenuItem(
                                                text = { Text("📋 Want to Watch") },
                                                onClick = {
                                                    viewModel.onUpdateWatchStatus(WatchStatus.WANT_TO_WATCH)
                                                    showStatusMenu = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("▶ Watching") },
                                                onClick = {
                                                    viewModel.onUpdateWatchStatus(WatchStatus.WATCHING)
                                                    showStatusMenu = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("✓ Watched") },
                                                onClick = {
                                                    viewModel.onUpdateWatchStatus(WatchStatus.WATCHED)
                                                    showStatusMenu = false
                                                }
                                            )
                                        }
                                    }
                                }

                                // User rating slider
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "My Rating: ${movie.userRating}/10",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Slider(
                                    value = movie.userRating,
                                    onValueChange = { viewModel.onUpdateUserRating(it) },
                                    valueRange = 0f..10f,
                                    steps = 9
                                )
                            }
                        }
                    }

                    // Reviews section
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Reviews (${state.reviewCount})",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (state.reviewCount > 0) {
                                        Text(
                                            text = "Avg: %.1f".format(state.averageRating),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = { showReviewDialog = true },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Write a Review")
                                }

                                if (state.reviews.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    state.reviews.forEach { review ->
                                        ReviewItem(
                                            review = review,
                                            onDelete = { viewModel.onDeleteReview(review) }
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }

                    // Bottom spacer
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Movie") },
            text = { Text("Are you sure you want to remove this movie from your collection?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onDeleteMovie()
                        showDeleteDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Add review dialog
    if (showReviewDialog) {
        AlertDialog(
            onDismissRequest = { showReviewDialog = false },
            title = { Text("Write a Review") },
            text = {
                Column {
                    Text("Rating: ${reviewRating.toInt()}/10")
                    Slider(
                        value = reviewRating,
                        onValueChange = { reviewRating = it },
                        valueRange = 0f..10f,
                        steps = 9
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reviewContent,
                        onValueChange = { reviewContent = it },
                        label = { Text("Your review") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (reviewContent.isNotBlank()) {
                            viewModel.onAddReview(reviewContent, reviewRating)
                            reviewContent = ""
                            reviewRating = 3f
                            showReviewDialog = false
                            scope.launch {
                                snackbarHostState.showSnackbar("Review added!")
                            }
                        }
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReviewDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    if (value.isNotBlank() && value != "N/A") {
        Row(modifier = Modifier.padding(vertical = 3.dp)) {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.width(80.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ReviewItem(
    review: ReviewEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = StarColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${review.rating}/10",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                            .format(Date(review.createdAt)),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete review",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = review.content,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )
        }
    }
}
