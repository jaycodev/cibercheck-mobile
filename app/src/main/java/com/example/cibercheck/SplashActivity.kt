package com.example.cibercheck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cibercheck.activity.LoginEmailActivity
import com.example.cibercheck.activity.SecurityCheckActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Determinar a qué actividad navegar
        val intent = determineNextActivity()
        startActivity(intent)
        finish() // Cierra esta actividad para que el usuario no pueda volver a ella
    }

    private fun determineNextActivity(): Intent {
        // Obtener las preferencias de seguridad
        val sharedPreferences = getSharedPreferences("CiberCheckPrefs", Context.MODE_PRIVATE)
        val isFingerprintEnabled = sharedPreferences.getBoolean("FINGERPRINT_ENABLED", false)

        // Si la huella está activada, ir a la pantalla de seguridad
        return if (isFingerprintEnabled) {
            Intent(this, SecurityCheckActivity::class.java)
        } else {
            // Si no, seguir el flujo normal hacia el login
            Intent(this, LoginEmailActivity::class.java)
        }
    }
}
