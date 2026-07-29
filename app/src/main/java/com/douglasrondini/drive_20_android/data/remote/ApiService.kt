package com.douglasrondini.drive_20_android.data.remote

import com.douglasrondini.drive_20_android.data.model.LoginRequest
import com.douglasrondini.drive_20_android.data.model.LoginResponse
import com.douglasrondini.drive_20_android.data.model.RegisterAlunoRequest
import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("users")
    suspend fun registerAluno(@Body aluno: RegisterAlunoRequest): Response<Unit>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
