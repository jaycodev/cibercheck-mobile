package com.example.cibercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import org.json.JSONArray

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

        adpCursos.replaceAll(loadCursos())

        val btnHistorialAsis: Button = findViewById(R.id.btn_historial)
        btnHistorialAsis.setOnClickListener {
            val intent = Intent(this, HistorialAsistenciasActivity::class.java)
            startActivity(intent)
        }

        val btnMarcarAsis: Button = findViewById(R.id.btn_marcarAsistencia)
        btnMarcarAsis.setOnClickListener {
            val intent = Intent(this, MarcarAsistenciaActivity::class.java)
            startActivity(intent)
        }
    }

    // Lógica para cargar lista de cursos (por ahora solo están en un json en assets/cursos.json)
    private fun loadCursos(): List<Curso> {
        val json = assets.open("cursos.json").bufferedReader().use { it.readText() }
        val arr = org.json.JSONArray(json)
        val list = ArrayList<Curso>(arr.length())
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            list += Curso(
                periodoId = o.getString("periodoId"),
                nombre = o.getString("nombre"),
                enCurso = o.optBoolean("enCurso", false),
                tiempo  = if (o.has("tiempo")) o.getString("tiempo") else null
            )
        }
        return list
    }
}