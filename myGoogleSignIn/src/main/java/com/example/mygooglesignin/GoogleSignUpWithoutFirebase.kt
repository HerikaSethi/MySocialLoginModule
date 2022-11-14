package com.example.mygooglesignin

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

object GoogleSignUpWithoutFirebase {

   lateinit var signInResult: GoogleSignInAccount

    fun signUpWithGoogleWithoutFirebase(context: Context){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

        mGoogleSignInClient.signOut()

        //Fire intent to choose account
        val intent = mGoogleSignInClient.signInIntent
        (context as Activity).startActivityForResult(intent, 1001)

    }

    fun activityResult(requestCode: Int,
                       resultCode: Int,
                       data: Intent?,
                       success: (GoogleSignInAccount) -> Unit,
                       failure: (Throwable) -> Unit) {

            if (requestCode == 1001){
                try {
                    val task =  GoogleSignIn.getSignedInAccountFromIntent(data)
                    signInResult = task.result
                    success.invoke(task.result)
                    task.getResult(ApiException::class.java)!!
                }catch (e: Exception){
                    failure.invoke(e)
                }
            }

    }

}