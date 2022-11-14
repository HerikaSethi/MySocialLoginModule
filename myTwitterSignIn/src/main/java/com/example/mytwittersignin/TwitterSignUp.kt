package com.example.mytwittersignin

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

object TwitterSignUp {
    lateinit var auth: FirebaseAuth
    var authenticationResult: AuthResult? = null

    fun signUpWithTwitter(context:Context,success:(AuthResult?)->Unit,failure:(Throwable)->Unit) {
        FirebaseApp.initializeApp(context)
        auth = FirebaseAuth.getInstance()

        val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("twitter.com")
        val pendingResultTask: Task<AuthResult>? = auth.pendingAuthResult
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener(
                    object : OnSuccessListener<AuthResult?> {
                        override fun onSuccess(authResult: AuthResult?) {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            // The OAuth access token can also be retrieved:
                            // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                            // The OAuth secret can be retrieved by calling:
                            // ((OAuthCredential)authResult.getCredential()).getSecret().
                            authenticationResult = authResult
                            success.invoke(authenticationResult)
                            Log.d("twitterlogin", "onSuccess: Twitter Login Successful")
                        }
                    })
                .addOnFailureListener(
                    object : OnFailureListener {
                        override fun onFailure(p0: java.lang.Exception) {
                            failure.invoke(p0)
                            Log.d("twitter", "onFailure:Twitter login ")
                        }
                    })
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            auth
                .startActivityForSignInWithProvider( /* activity= */context as Activity, provider.build())
                .addOnSuccessListener(
                    object : OnSuccessListener<AuthResult?> {

                        override fun onSuccess(authResult: AuthResult?) {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            // The OAuth access token can also be retrieved:
                            // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                            // The OAuth secret can be retrieved by calling:
                            // ((OAuthCredential)authResult.getCredential()).getSecret().
                            authenticationResult = authResult
                            success.invoke(authenticationResult)
                        }
                    })
                .addOnFailureListener(
                    OnFailureListener { exception ->
                        failure.invoke(exception)
                        Log.d("twitter", "signUpWithTwitter: faliure twitter login ")
                    })
        }

    }
}