package com.example.cibercheck.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cibercheck.R
import com.example.cibercheck.adapter.CursoAdapter
import com.example.cibercheck.dto.session.StudentDailyCourseDto
import com.example.cibercheck.entity.Curso
import com.example.cibercheck.service.RetrofitClient
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class MisClasesActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adpCursos : CursoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.misclases_main_page)

        findViewById<MaterialToolbar>(R.id.tb_misClases).setOnMenuItemClickListener { it ->
            if (it.itemId == R.id.action_profile) {
                startActivity(Intent(this, MiPerfilActivity::class.java))
                true
            } else { false }
        }

        rv = findViewById(R.id.rv_misClases)
        rv.layoutManager = LinearLayoutManager(this)
        adpCursos = CursoAdapter(mutableListOf())
        rv.adapter = adpCursos

        loadCursosFromApi()

        val btnHistorialAsis: Button = findViewById(R.id.btn_historial)
        btnHistorialAsis.setOnClickListener {
            val intent = Intent(this, HistorialAsistenciasActivity::class.java)
            startActivity(intent)
        }

        val btnMarcarAsis: Button = findViewById(R.id.btn_marcarAsistencia)
        btnMarcarAsis.setOnClickListener {
            val intent = Intent(this, ScanQRActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadCursosFromApi() {
        lifecycleScope.launch {
            try {
                // Calling the new endpoint with the test data you provided.
                val studentId = 5
                val date = "2025-10-14" // Corrected format yyyy-MM-dd

                val response = RetrofitClient.instance.getStudentDailySessions(studentId, date)

                if (response.isSuccessful) {
                    // This is your "console.log". It prints the API data to the Logcat window.
                    Log.d("API_RESPONSE", "Datos recibidos: ${response.body()}")

                    val sessions: List<StudentDailyCourseDto> = response.body() ?: emptyList()

                    // Convert the DTO list to a Curso list that the adapter can use.
                    val cursos = sessions.map { sessionDto ->
                        Curso(
                            periodoId = sessionDto.sectionName, // Assuming sectionName can be used as periodoId
                            nombre = sessionDto.courseName,
                            enCurso = false, // Setting to false as the date is in the future
                            tiempo = "${sessionDto.startTime} - ${sessionDto.endTime}" // Combining start and end times
                        )
                    }

                    adpCursos.replaceAll(cursos)

                } else {
                    Log.e("API_ERROR", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("NETWORK_ERROR", "Exception: ${e.message}")
            }
        }
    }
}