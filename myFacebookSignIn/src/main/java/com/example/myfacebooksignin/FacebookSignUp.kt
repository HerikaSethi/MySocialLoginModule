package com.example.myfacebooksignin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import org.json.JSONException

object FacebookSignUp {

    private lateinit var callbackManager: CallbackManager

    fun signUpWithFacebook(
        context: Context,
        success: (Profile) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d("onSuccess", "onSuccess: User Login Successful")
                    Profile.getCurrentProfile()?.let { success.invoke(it) }

                    val bundle = Bundle()
                    bundle.putString("fields", "id, email, first_name, last_name, gender,age_range")

                    //Graph API to access the data of user's facebook account
                    val request = GraphRequest.newMeRequest(
                        result.accessToken
                    ) { _, _ ->

                        //For safety measure enclose the request with try and catch
                        try {
                            result.accessToken.let {
                                //get current instance of logged profile
                                val profile = Profile.getCurrentProfile()
                                //get current id
                                val id = Profile.getCurrentProfile()?.id
                                //get current user name
                                val lastname = Profile.getCurrentProfile()?.name
                                Log.d("onSuccess", "onSuccess: profile is:: $profile")
                                Log.d("onSuccess", "onSuccess: profile is:: $id")
                                Log.d("onSuccess", "onSuccess: profile is:: $lastname")
                            }
                        } catch (e: JSONException) {
                            failure.invoke(e)
                        }
                    }
                    request.parameters = bundle
                    request.executeAsync()
                }

                override fun onCancel() {
                    Log.d("onCancel", "onCancel: Dialog cancel")
                    LoginManager.getInstance().logOut()
                }

                override fun onError(error: FacebookException) {
                    Log.d("onError", "Error logged")
                    failure.invoke(error)
                    return
                }
            })
        LoginManager.getInstance()
            .logInWithReadPermissions(context as Activity, listOf("public_profile","email","user_friends"))
    }

    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}