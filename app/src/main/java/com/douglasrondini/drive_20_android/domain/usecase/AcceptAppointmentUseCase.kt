package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.repository.AppointmentRepository

class AcceptAppointmentUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(appointmentId: String, instructorId: String): Result<Unit> {
        return repository.acceptAppointment(appointmentId, instructorId)
    }
}
