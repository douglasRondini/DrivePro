package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.model.Appointment
import com.douglasrondini.drive_20_android.domain.repository.AppointmentRepository

class GetInstructorAppointmentsUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(instructorId: String): Result<List<Appointment>> {
        return repository.getAppointmentsByInstructor(instructorId)
    }
}
