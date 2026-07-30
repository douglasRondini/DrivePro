package com.douglasrondini.drive_20_android.data.repository

import android.util.Log
import com.douglasrondini.drive_20_android.data.model.registerInstrutorFromToModel
import com.douglasrondini.drive_20_android.data.remote.ApiService
import com.douglasrondini.drive_20_android.data.remote.helper.BaseRepository
import com.douglasrondini.drive_20_android.domain.home.instrutor.InstrutorRegister
import com.douglasrondini.drive_20_android.domain.repository.InstrutorRepository
import com.google.gson.Gson

class InstrutorRepositoryImpl(
    private val apiService: ApiService
) : InstrutorRepository, BaseRepository() {

    override suspend fun registerInstrutor(instrutor: InstrutorRegister): Result<Unit> {
        val request = registerInstrutorFromToModel(instrutor)
        Log.d("DEBUG_REG", "Enviando JSON: ${Gson().toJson(request)}")
        
        return safeApiCallUnit {
            apiService.registerInstrutor(request)
        }
    }
}
