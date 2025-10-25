package com.example.cibercheck.entity

/**
 * Represents a course session to be displayed in the UI.
 */
data class Curso(
    val periodoId: String, // e.g., Section Name
    val nombre: String,      // e.g., Course Name
    val tiempo: String?,     // The display text for the time (e.g., "08:00 - 10:15" or "Empieza en 5 min")
    val status: CursoStatus, // The calculated status (IN_PROGRESS, STARTING_SOON, etc.)
    val startTime: String?,  // Raw start time for calculations
    val endTime: String?      // Raw end time for calculations
)
