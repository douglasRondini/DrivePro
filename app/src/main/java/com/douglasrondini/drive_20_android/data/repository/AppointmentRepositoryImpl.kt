package com.douglasrondini.drive_20_android.data.repository

import com.douglasrondini.drive_20_android.data.model.AcceptAppointmentRequest
import com.douglasrondini.drive_20_android.data.model.CreateAppointmentRequest
import com.douglasrondini.drive_20_android.data.remote.ApiService
import com.douglasrondini.drive_20_android.data.remote.helper.BaseRepository
import com.douglasrondini.drive_20_android.domain.model.Appointment
import com.douglasrondini.drive_20_android.domain.repository.AppointmentRepository

class AppointmentRepositoryImpl(
    private val apiService: ApiService
) : AppointmentRepository, BaseRepository() {

    override suspend fun getAppointmentsByInstructor(instructorId: String): Result<List<Appointment>> {
        return safeApiCall(
            apiCall = { apiService.getAppointmentsByInstructor(instructorId) },
            transform = { responses ->
                responses.map { response ->
                    Appointment(
                        id = response.id,
                        status = response.status,
                        localOrigem = response.localOrigem,
                        dataHora = response.dataHora,
                        preco = response.preco,
                        partnerName = response.aluno?.nome ?: "",
                        partnerPhone = response.aluno?.telefone ?: ""
                    )
                }
            }
        )
    }

    override suspend fun getAppointmentsByStudent(studentId: String): Result<List<Appointment>> {
        return safeApiCall(
            apiCall = { apiService.getAppointmentsByStudent(studentId) },
            transform = { responses ->
                responses.map { response ->
                    Appointment(
                        id = response.id,
                        status = response.status,
                        localOrigem = response.localOrigem,
                        dataHora = response.dataHora,
                        preco = response.preco,
                        partnerName = response.instrutor?.nome ?: "",
                        partnerPhone = response.instrutor?.telefone ?: ""
                    )
                }
            }
        )
    }

    override suspend fun createAppointment(
        studentId: String,
        instructorId: String,
        location: String,
        dateTime: String,
        price: Double
    ): Result<Unit> {
        return safeApiCallUnit {
            apiService.createAppointment(
                CreateAppointmentRequest(
                    studentId = studentId,
                    instructorId = instructorId,
                    originLocation = location,
                    dateTime = dateTime,
                    price = price
                )
            )
        }
    }

    override suspend fun acceptAppointment(appointmentId: String, instructorId: String): Result<Unit> {
        return safeApiCallUnit {
            apiService.acceptAppointment(appointmentId, AcceptAppointmentRequest(instructorId))
        }
    }

    override suspend fun refuseAppointment(appointmentId: String): Result<Unit> {
        return safeApiCallUnit {
            apiService.refuseAppointment(appointmentId)
        }
    }

    override suspend fun cancelAppointment(appointmentId: String): Result<Unit> {
        return safeApiCallUnit {
            apiService.cancelAppointment(appointmentId)
        }
    }

    override suspend fun completeAppointment(appointmentId: String): Result<Unit> {
        return safeApiCallUnit {
            apiService.completeAppointment(appointmentId)
        }
    }
}
