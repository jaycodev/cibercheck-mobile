package com.example.cibercheck.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cibercheck.R
import com.example.cibercheck.adapter.CursoAdapter
import com.example.cibercheck.dto.session.StudentDailyCourseDto
import com.example.cibercheck.entity.Curso
import com.example.cibercheck.entity.CursoStatus
import com.example.cibercheck.service.RetrofitClient
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class MisClasesActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adpCursos : CursoAdapter

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadCursosFromApi() {
        lifecycleScope.launch {
            try {
                val studentId = 5
                // Fecha de prueba restaurada
                val date: String? = "2025-10-14"

                val response = RetrofitClient.instance.getStudentDailySessions(studentId, date)

                if (response.isSuccessful) {
                    Log.d("API_RESPONSE", "Datos recibidos: ${response.body()}")

                    val sessions: List<StudentDailyCourseDto> = response.body() ?: emptyList()

                    // --- INICIO DE LA LÓGICA DE TIEMPO ---
                    val ahora = LocalTime.now()

                    val cursos = sessions.map { sessionDto ->
                        // Asegurarse de que las horas no son nulas
                        if (sessionDto.startTime == null || sessionDto.endTime == null) {
                            // Si no hay hora, se trata como un curso normal sin estado de tiempo
                            return@map Curso(
                                periodoId = sessionDto.sectionName,
                                nombre = sessionDto.courseName,
                                tiempo = "Horario no definido",
                                status = CursoStatus.UPCOMING,
                                startTime = null,
                                endTime = null
                            )
                        }

                        val horaInicio = LocalTime.parse(sessionDto.startTime)
                        val horaFin = LocalTime.parse(sessionDto.endTime)
                        val minutosParaEmpezar = ChronoUnit.MINUTES.between(ahora, horaInicio)

                        val (status, textoTiempo) = when {
                            // En curso
                            ahora.isAfter(horaInicio) && ahora.isBefore(horaFin) -> {
                                Pair(CursoStatus.IN_PROGRESS, "${sessionDto.startTime} - ${sessionDto.endTime}")
                            }
                            // Por empezar (dentro de los 10 minutos previos)
                            ahora.isBefore(horaInicio) && minutosParaEmpezar in 0..10 -> {
                                Pair(CursoStatus.STARTING_SOON, "Empieza en $minutosParaEmpezar min")
                            }
                            // Finalizado
                            ahora.isAfter(horaFin) -> {
                                Pair(CursoStatus.FINISHED, "${sessionDto.startTime} - ${sessionDto.endTime}")
                            }
                            // Próximo (más tarde en el día)
                            else -> {
                                Pair(CursoStatus.UPCOMING, "${sessionDto.startTime} - ${sessionDto.endTime}")
                            }
                        }

                        Curso(
                            periodoId = sessionDto.sectionName,
                            nombre = sessionDto.courseName,
                            tiempo = textoTiempo, // Texto dinámico calculado
                            status = status,       // Estado dinámico calculado
                            startTime = sessionDto.startTime,
                            endTime = sessionDto.endTime
                        )
                    }
                    // --- FIN DE LA LÓGICA DE TIEMPO ---

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
