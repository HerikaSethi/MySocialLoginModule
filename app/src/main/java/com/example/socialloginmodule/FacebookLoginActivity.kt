package com.example.socialloginmodule

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myfacebooksignin.FacebookSignUp
import com.example.socialloginmodule.databinding.ActivityFacebookLoginBinding

class FacebookLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityFacebookLoginBinding

    companion object {
        const val TAG = "FacebookLoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_facebook_login)

        binding.loginButton.setOnClickListener {
            FacebookSignUp.signUpWithFacebook(this,
                { profile ->
                    Toast.makeText(
                        this,
                        "facebook SignUp success ${profile.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                { exception ->
                    Toast.makeText(
                        this,
                        "facebook signup exception:${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                })

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FacebookSignUp.activityResult(requestCode, resultCode, data)
    }

}
