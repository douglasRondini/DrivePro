package com.douglasrondini.drive_20_android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class DriveApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Força tema claro independentemente do tema do sistema.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}

