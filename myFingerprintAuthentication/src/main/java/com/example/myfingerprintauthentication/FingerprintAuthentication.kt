package com.example.myfingerprintauthentication

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

object FingerprintAuthentication {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var authenticationResult: BiometricPrompt.AuthenticationResult

    fun authenticateWithFingerprint(
        context: Context,
        success: (BiometricPrompt.AuthenticationResult) -> Unit,
        failure: (CharSequence) -> Unit
    ) {
        val cont = context as FragmentActivity
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