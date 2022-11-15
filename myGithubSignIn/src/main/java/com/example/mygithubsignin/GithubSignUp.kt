package com.example.mygithubsignin

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider


object GithubSignUp {
    private lateinit var auth: FirebaseAuth
    var authenticationResult: AuthResult? = null

    fun signInWithGithub(context: Context,
                         email: String,
                         success: (AuthResult?) -> Unit,
                         failure: (Throwable) -> Unit){
        FirebaseApp.initializeApp(context)
        auth = FirebaseAuth.getInstance()

        //OAuth Provider
        val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")

        //Custom parameter
        provider.addCustomParameter("login", email)

        //Request read access to user's email address
        val scopes: List<String?> = object : ArrayList<String?>() {
            init {
                add("user:email")
            }
        }
        provider.scopes = scopes

        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener { authResult ->
                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                    success.invoke(authResult)
                    authenticationResult = authResult
                }
                .addOnFailureListener {
                    failure.invoke(it)
                    // Handle failure.
                }
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
           auth
                .startActivityForSignInWithProvider(context as Activity, provider.build())
                .addOnSuccessListener(
                    object : OnSuccessListener<AuthResult?> {

                        override fun onSuccess(authResult: AuthResult?) {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            // The OAuth access token can also be retrieved:
                            // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                            // authResult?.getAdditionalUserInfo()?.getProfile()
                            // authResult?.additionalUserInfo?.providerId
                            //authResult?.additionalUserInfo?.username
                            success.invoke(authResult)
                            authenticationResult = authResult

                        }
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        // Handle failure.
                        failure.invoke(it)
                        Log.d("githubresult", "signInWithGithub: failure in github login")
                        authenticationResult = null
                    })
        }

    }
}