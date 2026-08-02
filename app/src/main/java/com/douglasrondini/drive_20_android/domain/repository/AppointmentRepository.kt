package com.douglasrondini.drive_20_android.domain.repository

import com.douglasrondini.drive_20_android.domain.model.Appointment

interface AppointmentRepository {
    suspend fun getAppointmentsByInstructor(instructorId: String): Result<List<Appointment>>
    suspend fun getAppointmentsByStudent(studentId: String): Result<List<Appointment>>
    suspend fun acceptAppointment(appointmentId: String, instructorId: String): Result<Unit>
    suspend fun refuseAppointment(appointmentId: String): Result<Unit>
    suspend fun cancelAppointment(appointmentId: String): Result<Unit>
    suspend fun completeAppointment(appointmentId: String): Result<Unit>
}
