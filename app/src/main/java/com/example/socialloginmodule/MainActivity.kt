package com.example.socialloginmodule

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myfingerprintauthentication.FingerprintAuthentication
import com.example.mygooglesignin.GoogleSignUp.activityResult
import com.example.mygooglesignin.GoogleSignUp.signInWithGoogle
import com.example.mygooglesignin.GoogleSignUpWithoutFirebase
import com.example.socialloginmodule.databinding.ActivityMainBinding
import com.example.socialloginmodule.utils.HelperConstants


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        /** Google Sign In */
        binding.btnGoogleSignIn.setOnClickListener {
            signInWithGoogle(this@MainActivity, HelperConstants.REQUEST_TOKEN, { success ->
                Toast.makeText(
                    this,
                    "Google signIn with firebase success : ${success.displayName}",
                    Toast.LENGTH_SHORT
                ).show()
            }, { exception ->
                Toast.makeText(
                    this,
                    "Google signIn with firebase exception : ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            })
        }

        /** Github Sign In */
        binding.btnGithubSignIn.setOnClickListener {
            val intent = Intent(this, GithubEmailActivity::class.java)
            startActivity(intent)
        }

        /** Fingerprint Sign In */
        binding.btnFingerprintSignIn.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= 28) {

                FingerprintAuthentication.authenticateWithFingerprint(this@MainActivity, {
                    Toast.makeText(
                        this,
                        "Fingerprint authentication success::${it}",
                        Toast.LENGTH_SHORT
                    ).show()
                }, {
                    Toast.makeText(
                        this,
                        "fingerprint authentication failure:: $it",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            } else {
                Toast.makeText(this, "Feature not available", Toast.LENGTH_SHORT).show()
            }
        }

        /** Facebook Sign In */
        binding.btnfacebook.setOnClickListener {
            val intent = Intent(this, FacebookLoginActivity::class.java)
            startActivity(intent)
        }

        /** Google Sign In Without Firebase */
        binding.btnGoogleWithoutFirebase.setOnClickListener {
            GoogleSignUpWithoutFirebase.signUpWithGoogleWithoutFirebase(this@MainActivity,
                { success ->
                    Toast.makeText(
                        this,
                        "GoogleSignIn without Firebase success ${success.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                { exception ->
                    Toast.makeText(
                        this,
                        "GoogleSignIn without Firebase exception: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResult(requestCode, resultCode, data)
        GoogleSignUpWithoutFirebase.activityResult(requestCode, resultCode, data)
    }

}