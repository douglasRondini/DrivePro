package com.douglasrondini.drive_20_android.domain.repository

import com.douglasrondini.drive_20_android.domain.model.Appointment

interface AppointmentRepository {
    suspend fun getAppointmentsByInstructor(instructorId: String): Result<List<Appointment>>
}
