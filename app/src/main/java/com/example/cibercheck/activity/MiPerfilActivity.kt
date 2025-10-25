package com.example.cibercheck.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cibercheck.R
import com.google.android.material.appbar.MaterialToolbar

class MiPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<MaterialToolbar>(R.id.tb_miperfil)
            .setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

        // --- Datos Harcodeados ---
        val tvNombreUsuario: TextView = findViewById(R.id.tv_nombreUsuario)
        val tvCorreoUsuario: TextView = findViewById(R.id.tv_correoUsuario)
        tvNombreUsuario.text = "Angelo Pit Bieber"
        tvCorreoUsuario.text = "u202212345@cibertec.edu.pe"


        // --- Lógica para el Switch de Huella Digital ---

        val sharedPreferences = getSharedPreferences("CiberCheckPrefs", Context.MODE_PRIVATE)
        val swHuella: Switch = findViewById(R.id.sw_huella)

        // Cargar el estado guardado y establecerlo en el switch
        swHuella.isChecked = sharedPreferences.getBoolean("FINGERPRINT_ENABLED", false)

        // Listener para el switch de la Huella
        swHuella.setOnCheckedChangeListener { _, isChecked ->
            // Guardar la preferencia en SharedPreferences
            sharedPreferences.edit().putBoolean("FINGERPRINT_ENABLED", isChecked).apply()

            val message = if (isChecked) "Ingreso con huella activado" else "Ingreso con huella desactivado"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


        // --- Botón Cerrar Sesión ---
        val btnCerrarSesion: Button = findViewById(R.id.btn_cerrarSesion)
        btnCerrarSesion.setOnClickListener {
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginEmailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
