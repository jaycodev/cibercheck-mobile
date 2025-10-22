package com.example.cibercheck.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cibercheck.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cibercheck.adapter.HistorialAdapter
import com.example.cibercheck.entity.Asistencia

class HistorialAsistenciasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistorialAdapter// tu adapter personalizado
    private val listaAsistencias = mutableListOf<Asistencia>() // tu lista de items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_asistencias)

        recyclerView = findViewById(R.id.rv_historialAsistencias)

        // Ejemplo de datos de prueba
        listaAsistencias.add(Asistencia("DESARROLLO DE APLICACIONES MÓVILES I", "PRESENCIAL", "7/10"))
        listaAsistencias.add(Asistencia("PROGRAMACIÓN ORIENTADA A OBJETOS", "VIRTUAL", "8/12"))

        adapter = HistorialAdapter(listaAsistencias)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
