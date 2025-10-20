package com.example.cibercheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class MiPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<MaterialToolbar>(R.id.tb_miperfil)
            .setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
    }
}