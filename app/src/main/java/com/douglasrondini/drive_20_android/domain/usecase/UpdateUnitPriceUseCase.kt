package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.repository.InstructorRepository

class UpdateUnitPriceUseCase(
    private val repository: InstructorRepository
) {
    suspend operator fun invoke(instructorId: String, price: Double): Result<Unit> {
        return repository.updateUnitPrice(instructorId, price)
    }
}
