package com.example.cibercheck

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Instala el SplashScreen (Android 12+)
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Opcional: mantener el splash hasta que cargue algo
        splashScreen.setKeepOnScreenCondition {
            false // poner true si quieres retrasar un poco
        }

        // Abrir siguiente actividad después del splash
        startActivity(Intent(this, com.example.cibercheck.activity.LoginEmailActivity::class.java))
        finish() // para que el usuario no vuelva a SplashActivity con "back"
    }
}
