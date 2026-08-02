package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.model.Appointment
import com.douglasrondini.drive_20_android.domain.repository.AppointmentRepository

class GetStudentAppointmentsUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(studentId: String): Result<List<Appointment>> {
        return repository.getAppointmentsByStudent(studentId)
    }
}
