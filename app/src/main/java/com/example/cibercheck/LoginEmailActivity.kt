package com.example.cibercheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.MaterialToolbar
import androidx.appcompat.app.AppCompatActivity

class LoginEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

        // Toolbar con botón atrás
        findViewById<MaterialToolbar>(R.id.tb_login_email)
            .setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

        // Botón "Siguiente"
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        btnSiguiente.setOnClickListener {
            // Intent para ir a otra actividad
            val intent = Intent(this, VerificationCodeActivity::class.java)
            startActivity(intent)
        }
    }
}
