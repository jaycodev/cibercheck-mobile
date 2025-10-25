package com.example.cibercheck.service

import android.util.Log
import com.example.cibercheck.BuildConfig
import com.example.cibercheck.dto.session.StudentDailyCourseDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /**
     * Gets the daily sessions for a specific student.
     * Corresponds to the [HttpGet("sessions/student/{studentId:int}/day")] endpoint.
     *
     * @param studentId The ID of the student.
     * @param date Optional date in "yyyy-MM-dd" format. If null, the server will use the current date.
     * @return A list of the student's course sessions for the given day.
     */
    @GET("sessions/student/{studentId}/day")
    suspend fun getStudentDailySessions(
        @Path("studentId") studentId: Int,
        @Query("date") date: String? // Format: "yyyy-MM-dd"
    ): Response<List<StudentDailyCourseDto>>
}

object RetrofitClient {
    // The base URL is now read from the auto-generated BuildConfig
    private val BASE_URL = BuildConfig.API_BASE_URL

    val instance: ApiService by lazy {
        // This will print the URL to Logcat when Retrofit is first initialized.
        Log.d("RETROFIT_URL", "Retrofit is using BASE_URL: $BASE_URL")

        // Create a logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
        }

        // Create an OkHttpClient and add the interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Use the custom client
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
