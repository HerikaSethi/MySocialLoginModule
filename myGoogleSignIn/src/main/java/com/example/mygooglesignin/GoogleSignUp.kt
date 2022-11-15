package com.example.mygooglesignin


import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

object GoogleSignUp {

    lateinit var auth: FirebaseAuth
    lateinit var success: (GoogleSignInAccount) -> Unit
    lateinit var failure: (Throwable) -> Unit

    fun signInWithGoogle(context: Context, requestToken: String, success: (GoogleSignInAccount) -> Unit,failure: (Throwable) -> Unit) {
        this.success = success
        this.failure = failure

        FirebaseApp.initializeApp(context)
        auth = FirebaseAuth.getInstance()

        //create request
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(requestToken)
            .requestEmail()
            .build()

        val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

        //To clear previous signedIn accounts
        googleSignInClient.signOut()


        //Fire intent to choose account
        val intent = googleSignInClient.signInIntent
        (context as Activity).startActivityForResult(intent, 1001)

    }



    fun activityResult(requestCode: Int,
                       resultCode: Int,
                       data: Intent?,
                       ){

            if (requestCode == 1001){
                try {
                val task =  GoogleSignIn.getSignedInAccountFromIntent(data)
                    success.invoke(task.result)
                    task.getResult(ApiException::class.java)!!
                }catch (e: Exception){
                    failure.invoke(e)
                }
            }
    }

}