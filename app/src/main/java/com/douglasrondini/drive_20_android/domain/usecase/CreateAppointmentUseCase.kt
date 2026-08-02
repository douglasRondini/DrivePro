package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.repository.AppointmentRepository

class CreateAppointmentUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(
        studentId: String,
        instructorId: String,
        location: String,
        dateTime: String,
        price: Double
    ): Result<Unit> {
        return repository.createAppointment(
            studentId = studentId,
            instructorId = instructorId,
            location = location,
            dateTime = dateTime,
            price = price
        )
    }
}
