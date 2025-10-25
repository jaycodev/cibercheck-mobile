package com.example.cibercheck.entity

/**
 * Represents the current status of a course session.
 */
enum class CursoStatus {
    IN_PROGRESS,    // The class is currently in session.
    STARTING_SOON,  // The class is about to start (e.g., within 10 minutes).
    FINISHED,       // The class has already finished for the day.
    UPCOMING        // The class is scheduled for later in the day.
}
