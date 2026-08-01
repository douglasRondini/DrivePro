package com.douglasrondini.drive_20_android.utils

import com.douglasrondini.drive_20_android.domain.model.Appointment
import org.junit.Assert.assertEquals
import org.junit.Test

class InstructorStatsHelperTest {

    private val appointments = listOf(
        Appointment("1", "CONCLUIDA", "Loc 1", "2026-08-01", 100.0, "Aluno 1", "123"),
        Appointment("2", "PENDENTE", "Loc 2", "2026-08-02", 150.0, "Aluno 2", "456"),
        Appointment("3", "ACEITA", "Loc 3", "2026-08-03", 200.0, "Aluno 3", "789"),
        Appointment("4", "CANCELADA", "Loc 4", "2026-08-04", 50.0, "Aluno 4", "000"),
        Appointment("5", "CONCLUIDA", "Loc 5", "2026-08-05", 150.0, "Aluno 5", "111")
    )

    @Test
    fun `calculateCompletedCount should return correct count of completed appointments`() {
        val result = InstructorStatsHelper.calculateCompletedCount(appointments)
        assertEquals(2, result)
    }

    @Test
    fun `calculatePendingCount should return correct count of pending appointments`() {
        val result = InstructorStatsHelper.calculatePendingCount(appointments)
        assertEquals(1, result)
    }

    @Test
    fun `calculateAcceptedCount should return correct count of accepted appointments`() {
        val result = InstructorStatsHelper.calculateAcceptedCount(appointments)
        assertEquals(1, result)
    }

    @Test
    fun `calculateCancelledCount should return correct count of cancelled appointments`() {
        val result = InstructorStatsHelper.calculateCancelledCount(appointments)
        assertEquals(1, result)
    }

    @Test
    fun `calculateTotalRevenue should return sum of prices for completed appointments`() {
        val result = InstructorStatsHelper.calculateTotalRevenue(appointments)
        assertEquals(250.0, result, 0.001)
    }

    @Test
    fun `calculateTotalRequests should return total count of appointments`() {
        val result = InstructorStatsHelper.calculateTotalRequests(appointments)
        assertEquals(5, result)
    }
}
