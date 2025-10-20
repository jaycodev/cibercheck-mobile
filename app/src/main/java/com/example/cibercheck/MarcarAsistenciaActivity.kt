package com.example.cibercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MarcarAsistenciaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marcar_asistencias)

        findViewById<Button>(R.id.btnVolverMarcar).setOnClickListener {
            finish()
        }
    }
}