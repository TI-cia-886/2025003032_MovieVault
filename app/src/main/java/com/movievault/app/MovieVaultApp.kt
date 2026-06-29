package com.movievault.app

import android.app.Application

class MovieVaultApp : Application() {
    val container: AppContainer by lazy {
        AppContainer(applicationContext)
    }
}
