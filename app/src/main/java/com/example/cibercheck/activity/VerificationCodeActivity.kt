package com.example.cibercheck.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cibercheck.R
import com.google.android.material.appbar.MaterialToolbar

class VerificationCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)

        val toolbar = findViewById<MaterialToolbar>(R.id.tb_verification_code)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val et1 = findViewById<EditText>(R.id.et1)
        val et2 = findViewById<EditText>(R.id.et2)
        val et3 = findViewById<EditText>(R.id.et3)
        val et4 = findViewById<EditText>(R.id.et4)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnVolver.setOnClickListener {
            val code = et1.text.toString() + et2.text.toString() + et3.text.toString() + et4.text.toString()

            if (code.length == 4) {
                // Solo como prueba, navega a otra pantalla
                val intent = Intent(this, MisClasesActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ingresa los 4 dígitos del código", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
