package com.douglasrondini.drive_20_android.data.repository

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
                        alunoNome = response.aluno.nome,
                        alunoTelefone = response.aluno.telefone
                    )
                }
            }
        )
    }
}
