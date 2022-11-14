package com.example.myfingerprintauthentication

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

object FingerprintAuthentication {

    lateinit var enrollIntent: Intent
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var authenticationResult: BiometricPrompt.AuthenticationResult


    fun createBiometricRequest(context: Context): Intent {

        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(context, "Fingerprint sensor does not exist", Toast.LENGTH_SHORT)
                    .show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(context, "Biometric hardware does not exist", Toast.LENGTH_SHORT)
                    .show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                enrollIntent.apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                //startActivityForResult(enrollIntent, REQUEST_CODE)
            }
        }
        return enrollIntent
    }

    fun authenticateWithFingerprint(
        context: Context,
        success: (BiometricPrompt.AuthenticationResult) -> Unit,
        failure: (CharSequence) -> Unit
    ) {
        val cont = context as FragmentActivity
        executor = ContextCompat.getMainExecutor(context)

        executor = ContextCompat.getMainExecutor(context)


        biometricPrompt = BiometricPrompt(cont, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d("fingerprint", "onAuthenticationError: $errString")
                    failure.invoke(errString)
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Log.d(
                        "fingerprint",
                        "onAuthenticationSucceeded: Authentication succeeded $result"
                    )
                    success.invoke(result)
                    authenticationResult = result
                }

            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)


    }

}