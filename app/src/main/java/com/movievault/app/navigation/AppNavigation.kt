package com.movievault.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.movievault.app.AppContainer
import com.movievault.app.ui.screens.AddMovieScreen
import com.movievault.app.ui.screens.MovieDetailScreen
import com.movievault.app.ui.screens.MovieListScreen
import com.movievault.app.ui.screens.SplashScreen
import com.movievault.app.ui.theme.MovieVaultTheme
import com.movievault.app.ui.theme.ThemeMode
import com.movievault.app.viewmodel.AddMovieViewModel
import com.movievault.app.viewmodel.MovieDetailViewModel
import com.movievault.app.viewmodel.MovieListViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object Routes {
    const val SPLASH = "splash"
    const val MOVIE_LIST = "movie_list"
    const val MOVIE_DETAIL = "movie_detail/{movieId}"
    const val ADD_MOVIE = "add_movie"

    fun movieDetail(movieId: Long) = "movie_detail/$movieId"
}

@Composable
fun AppNavigation(appContainer: AppContainer) {
    val navController: NavHostController = rememberNavController()
    val scope = rememberCoroutineScope()

    // Observe theme mode from DataStore
    val themeMode by appContainer.movieRepository.themeMode.collectAsState(initial = ThemeMode.SYSTEM)

    // Track latest theme mode for toggle to avoid stale closure capture
    val themeModeRef = remember { mutableStateOf(ThemeMode.SYSTEM) }
    themeModeRef.value = themeMode

    MovieVaultTheme(themeMode = themeMode) {
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH
        ) {
            composable(Routes.SPLASH) {
                SplashScreen(
                    onEnter = {
                        navController.navigate(Routes.MOVIE_LIST) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.MOVIE_LIST) {
            val viewModel: MovieListViewModel = viewModel(
                factory = MovieListViewModel.Factory(appContainer.movieRepository)
            )
            // Refresh movie list when returning to this screen
            val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
            val hasResumed = remember { mutableStateOf(false) }
            androidx.compose.runtime.DisposableEffect(lifecycleOwner) {
                val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
                    if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                        if (hasResumed.value) {
                            viewModel.onRefresh()
                        } else {
                            hasResumed.value = true
                        }
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
            MovieListScreen(
                viewModel = viewModel,
                onMovieClick = { movieId ->
                    navController.navigate(Routes.movieDetail(movieId))
                },
                onAddMovieClick = {
                    navController.navigate(Routes.ADD_MOVIE)
                },
                themeMode = themeMode,
                onToggleTheme = {
                    scope.launch {
                        // Read latest theme mode from DataStore to avoid stale closure value
                        val currentMode = try {
                            appContainer.movieRepository.themeMode.first()
                        } catch (_: Exception) {
                            themeModeRef.value
                        }
                        val nextMode = when (currentMode) {
                            ThemeMode.SYSTEM -> ThemeMode.DARK
                            ThemeMode.DARK -> ThemeMode.LIGHT
                            ThemeMode.LIGHT -> ThemeMode.SYSTEM
                            else -> ThemeMode.SYSTEM
                        }
                        appContainer.movieRepository.setThemeMode(nextMode)
                    }
                }
            )
        }

        composable(
            route = Routes.MOVIE_DETAIL,
            arguments = listOf(
                navArgument("movieId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable
            val viewModel: MovieDetailViewModel = viewModel(
                factory = MovieDetailViewModel.Factory(appContainer.movieRepository, movieId)
            )
            MovieDetailScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ADD_MOVIE) {
            val viewModel: AddMovieViewModel = viewModel(
                factory = AddMovieViewModel.Factory(appContainer.movieRepository)
            )
            AddMovieScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onMovieAdded = { movieId ->
                    navController.navigate(Routes.movieDetail(movieId)) {
                        popUpTo(Routes.ADD_MOVIE) { inclusive = true }
                    }
                }
            )
        }
    }
    }
}
