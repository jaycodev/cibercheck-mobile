package com.example.cibercheck.dto.session

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for a student's daily course session.
 * This class maps to the JSON response from the GetStudentDailySessions endpoint.
 */
data class StudentDailyCourseDto(

    @SerializedName("courseName")
    val courseName: String,

    @SerializedName("sectionName")
    val sectionName: String,

    // Representing C# TimeOnly as a String (e.g., "14:30:00"). Gson will parse it as a string.
    @SerializedName("startTime")
    val startTime: String?,

    @SerializedName("endTime")
    val endTime: String?,

    @SerializedName("topic")
    val topic: String?
)