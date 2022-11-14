package com.example.socialloginmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mygithubsignin.GithubSignUp
import com.example.socialloginmodule.databinding.ActivityGithubEmailBinding

class GithubEmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityGithubEmailBinding

    companion object{
        const val TAG = "GithubEmailActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_email)

        binding.login.setOnClickListener {
            val email = binding.email.text.toString()

            GithubSignUp.signInWithGithub(this@GithubEmailActivity, email, { result ->
                /** Retrieve user Info */
                Toast.makeText(
                    this,
                    "Github signUp success: ${result?.additionalUserInfo?.username} ",
                    Toast.LENGTH_SHORT
                ).show()
            }, {
                Toast.makeText(this, "Github signUp exception :${it.message}", Toast.LENGTH_SHORT)
                    .show()
            })
        }
    }
}