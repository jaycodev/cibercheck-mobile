package com.example.cibercheck.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.MaterialToolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.cibercheck.R

class LoginEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

        findViewById<MaterialToolbar>(R.id.tb_login_email)
            .setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        btnSiguiente.setOnClickListener {

            val intent = Intent(this, VerificationCodeActivity::class.java)
            startActivity(intent)
        }
    }
}
