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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_facebook_login)

        binding.loginButton.setOnClickListener {
          FacebookSignUp.signUpWithFacebook(this,
              {
                  Toast.makeText(this, "facebook SignUp success ${it.name}", Toast.LENGTH_SHORT).show()
              },
              {
                  Toast.makeText(this, "facebook signup exception:${it.message}", Toast.LENGTH_SHORT).show()
              })

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FacebookSignUp.activityResult(requestCode,resultCode,data)
    }

}
