package com.douglasrondini.drive_20_android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.douglasrondini.drive_20_android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DriveApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Força tema claro independentemente do tema do sistema.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger()
            androidContext(this@DriveApp)
            modules(appModule)
        }
    }
}
