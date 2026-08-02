package com.douglasrondini.drive_20_android.data.repository

import com.douglasrondini.drive_20_android.data.model.UpdatePriceRequest
import com.douglasrondini.drive_20_android.data.remote.ApiService
import com.douglasrondini.drive_20_android.data.remote.helper.BaseRepository
import com.douglasrondini.drive_20_android.domain.model.Instructor
import com.douglasrondini.drive_20_android.domain.repository.InstructorRepository

class InstructorRepositoryImpl(
    private val apiService: ApiService
) : InstructorRepository, BaseRepository() {

    override suspend fun getAvailableInstructors(): Result<List<Instructor>> {
        return safeApiCall(
            apiCall = { apiService.getAvailableInstructors() },
            transform = { responses ->
                responses.map { dto ->
                    Instructor(
                        id = dto.id,
                        name = dto.name,
                        phone = dto.phone,
                        cnh = dto.cnh,
                        plate = dto.plate,
                        isAvailable = dto.isAvailable,
                        price = dto.price,
                        email = dto.user.email
                    )
                }
            }
        )
    }

    override suspend fun updateUnitPrice(instructorId: String, price: Double): Result<Unit> {
        return safeApiCallUnit {
            apiService.updateUnitPrice(instructorId, UpdatePriceRequest(price))
        }
    }
}
