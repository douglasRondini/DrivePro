package com.douglasrondini.drive_20_android.data.remote

import com.douglasrondini.drive_20_android.data.model.*
import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoRegister
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("users")
    suspend fun registerAluno(@Body aluno: RegisterAlunoRequest): Response<Unit>

    @POST("users")
    suspend fun registerInstrutor(@Body instrutor: RegisterInstrutorRequest): Response<Unit>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("agendamentos/instrutor/{id}")
    suspend fun getAppointmentsByInstructor(@Path("id") instructorId: String): Response<List<AppointmentResponse>>

    @GET("agendamentos/aluno/{id}")
    suspend fun getAppointmentsByStudent(@Path("id") studentId: String): Response<List<AppointmentResponse>>

    @PATCH("agendamentos/{id}/aceitar")
    suspend fun acceptAppointment(
        @Path("id") appointmentId: String,
        @Body request: AcceptAppointmentRequest
    ): Response<Unit>

    @PATCH("agendamentos/{id}/recusar")
    suspend fun refuseAppointment(@Path("id") appointmentId: String): Response<Unit>

    @PATCH("agendamentos/{id}/cancelar")
    suspend fun cancelAppointment(@Path("id") appointmentId: String): Response<Unit>

    @PATCH("agendamentos/{id}/concluir")
    suspend fun completeAppointment(@Path("id") appointmentId: String): Response<Unit>

    @GET("instrutores/disponiveis")
    suspend fun getAvailableInstructors(): Response<List<InstructorResponse>>
}
