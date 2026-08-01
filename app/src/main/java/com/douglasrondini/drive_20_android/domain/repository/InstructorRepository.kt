package com.douglasrondini.drive_20_android.domain.repository

import com.douglasrondini.drive_20_android.domain.model.Instructor

interface InstructorRepository {
    suspend fun getAvailableInstructors(): Result<List<Instructor>>
}
