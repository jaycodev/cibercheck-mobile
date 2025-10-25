package com.example.cibercheck.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class SecurityCheckActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    navigateToNextScreen()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Autenticación requerida", Toast.LENGTH_SHORT).show()
                    finishAffinity() // Cierra la app completamente
                }
            })

        // --- CAMBIO CLAVE AQUÍ ---
        // Ahora permitimos que el usuario use su PIN/Patrón si cancela la huella.
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verificación de CiberCheck")
            .setSubtitle("Usa tu huella o rostro para continuar")
            .setDeviceCredentialAllowed(true) // ¡Esta es la línea mágica!
            .build()

        checkAndShowBiometricPrompt()
    }

    private fun checkAndShowBiometricPrompt() {
        val biometricManager = BiometricManager.from(this)
        // La comprobación ya incluía DEVICE_CREDENTIAL, por lo que es correcta.
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }
            else -> {
                // Si no hay biométricos ni PIN/Patrón configurado, no se puede bloquear la app.
                // Por seguridad, en un caso real, se podría forzar un logout.
                // Por ahora, continuamos al login.
                navigateToNextScreen()
            }
        }
    }

    private fun navigateToNextScreen() {
        val intent = Intent(this, LoginEmailActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
