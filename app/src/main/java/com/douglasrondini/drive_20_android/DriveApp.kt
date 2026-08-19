package com.douglasrondini.drive_20_android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.douglasrondini.drive_20_android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.douglasrondini.drive_20_android.worker.NotificationWorker
import java.util.concurrent.TimeUnit

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

        setupNotificationWorker()
    }

    private fun setupNotificationWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DriveProNotificationWork",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            notificationRequest
        )
    }
}
