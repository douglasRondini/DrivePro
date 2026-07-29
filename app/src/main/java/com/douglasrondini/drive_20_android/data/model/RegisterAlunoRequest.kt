package com.douglasrondini.drive_20_android.data.model

import com.douglasrondini.drive_20_android.domain.home.aluno.AlunoRegister
import com.douglasrondini.drive_20_android.domain.home.aluno.Role
import com.google.gson.annotations.SerializedName

data class RegisterAlunoRequest(
    val email: String,
    val name: String,
    val age: String,
    val password: String,
    val role: Role,
    val telefone: String,
    val cpf: String
)
fun registerAlunoFromToModel(alunoRegister: AlunoRegister): RegisterAlunoRequest {
    return RegisterAlunoRequest(
        email = alunoRegister.email,
        name = alunoRegister.name,
        age = alunoRegister.age,
        password = alunoRegister.password,
        role = alunoRegister.role,
        telefone = alunoRegister.telefone,
        cpf = alunoRegister.cpf
    )
}
