package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.repository.AppointmentRepository

class CancelAppointmentUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(appointmentId: String): Result<Unit> {
        return repository.cancelAppointment(appointmentId)
    }
}
