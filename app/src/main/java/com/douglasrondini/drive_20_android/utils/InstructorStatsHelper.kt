package com.douglasrondini.drive_20_android.utils

import com.douglasrondini.drive_20_android.domain.model.Appointment

object InstructorStatsHelper {
    
    fun calculateCompletedCount(appointments: List<Appointment>): Int {
        return appointments.count { it.status.uppercase() == "CONCLUIDA" }
    }

    fun calculatePendingCount(appointments: List<Appointment>): Int {
        return appointments.count { it.status.uppercase() == "PENDENTE" }
    }

    fun calculateAcceptedCount(appointments: List<Appointment>): Int {
        return appointments.count { it.status.uppercase() == "ACEITA" }
    }

    fun calculateCancelledCount(appointments: List<Appointment>): Int {
        return appointments.count { it.status.uppercase() == "CANCELADA" }
    }

    fun calculateTotalRevenue(appointments: List<Appointment>): Double {
        return appointments
            .filter { it.status.uppercase() == "CONCLUIDA" }
            .sumOf { it.preco }
    }

    fun calculateTotalRequests(appointments: List<Appointment>): Int {
        return appointments.size
    }
}
