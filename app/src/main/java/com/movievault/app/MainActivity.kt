package com.movievault.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.movievault.app.navigation.AppNavigation
import com.movievault.app.ui.theme.MovieVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as MovieVaultApp).container

        setContent {
            val themeMode by appContainer.movieRepository.themeMode.collectAsState(initial = "system")
            MovieVaultTheme(themeMode = themeMode) {
                AppNavigation(appContainer = appContainer)
            }
        }
    }
}
