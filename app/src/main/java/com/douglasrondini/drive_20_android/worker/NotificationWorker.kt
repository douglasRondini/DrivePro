package com.douglasrondini.drive_20_android.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import com.douglasrondini.drive_20_android.domain.repository.AppointmentRepository
import com.douglasrondini.drive_20_android.ui.activities.AccountActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val appointmentRepository: AppointmentRepository by inject()
    private val preferenceManager: PreferenceManager by inject()

    override suspend fun doWork(): Result {
        val userId = preferenceManager.getUserId() ?: return Result.success()
        val userRole = preferenceManager.getUserRole() ?: return Result.success()

        Log.d("NotificationWorker", "Checking for updates for user: $userId")

        val result = if (userRole.lowercase() == "instrutor") {
            appointmentRepository.getAppointmentsByInstructor(userId)
        } else {
            appointmentRepository.getAppointmentsByStudent(userId)
        }

        result.onSuccess { appointments ->
            // Aqui simplificamos: se houver alguma mudança de status ou novos itens, notificamos.
            // Em um app real, compararíamos com o estado anterior salvo localmente.
            // Para este exemplo, vamos simular que encontramos algo novo.
            
            val pendingCount = appointments.count { it.status.uppercase() == "PENDENTE" }
            if (pendingCount > 0 && userRole.lowercase() == "instrutor") {
                showNotification(
                    "Novas Solicitações",
                    "Você tem $pendingCount nova(s) aula(s) aguardando aprovação."
                )
            }
            
            // Exemplo de evolução de status (notificar aluno quando aceita)
            if (userRole.lowercase() == "aluno") {
                val accepted = appointments.any { it.status.uppercase() == "ACEITA" }
                if (accepted) {
                    showNotification(
                        "Aula Confirmada!",
                        "Seu instrutor aceitou sua solicitação de aula."
                    )
                }
            }
        }

        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "drivepro_updates"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Atualizações DrivePro", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, AccountActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_car) // Usando ic_car como ícone da notificação
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
