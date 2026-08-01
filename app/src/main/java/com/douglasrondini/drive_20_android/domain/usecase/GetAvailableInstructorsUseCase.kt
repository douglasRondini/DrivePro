package com.douglasrondini.drive_20_android.domain.usecase

import com.douglasrondini.drive_20_android.domain.model.Instructor
import com.douglasrondini.drive_20_android.domain.repository.InstructorRepository

class GetAvailableInstructorsUseCase(
    private val repository: InstructorRepository
) {
    suspend operator fun invoke(): Result<List<Instructor>> {
        return repository.getAvailableInstructors()
    }
}
