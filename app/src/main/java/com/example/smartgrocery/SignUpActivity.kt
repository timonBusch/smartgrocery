package com.example.smartgrocery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.smartgrocery.repositories.SignUpRepository
import com.example.smartgrocery.repositories.SignUpRepository.body
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    var register_Button: Button? = null
    var signupUserNameText: EditText? = null
    var signupPasswordText: EditText? = null
    var signupRePasswordText: EditText? = null
    var signupEmailText: EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        progressBar = findViewById(R.id.signup_progress)
        register_Button = findViewById(R.id.btn_register)
        signupPasswordText = findViewById(R.id.signup_password)
        signupRePasswordText = findViewById(R.id.signup_repassword)
        signupUserNameText = findViewById(R.id.signup_name_input)
        signupEmailText = findViewById(R.id.email_input)

        register_Button!!.setOnClickListener{
            progressBar!!.visibility = View.VISIBLE
            signUp()

        }

    }

    fun signUp() {
        if(!validate()) {
            onSignUpFailed()
            return
        }

        register_Button!!.isEnabled = false

        // TODO: Progress bar

        val userName = signupUserNameText!!.text.toString()
        val password = signupPasswordText!!.text.toString()
        val email = signupEmailText!!.text.toString()

        val corretlySingedUp = validate()

        // TODO: SignUp logic (send information to server)
        SignUpRepository.sendUserData(userName, password, email)

        android.os.Handler().postDelayed(
            {
                val serverResponse = body
                val corretlySingedUp = serverResponse.equals("saved")

                if(corretlySingedUp) {
                    onSignUpSuccess()
                } else {
                    onSignUpFailed()
                }
            }, 3000
        )

    }

    private fun onSignUpSuccess() {
        progressBar!!.visibility = View.GONE
        register_Button!!.isEnabled = true
        Toast.makeText(baseContext, "Registration successfull",
            Toast.LENGTH_LONG).show()

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun onSignUpFailed() {
        progressBar!!.visibility = View.GONE
        Toast.makeText(baseContext, "Signup failed", Toast.LENGTH_LONG).show()
    }

    private fun validate(): Boolean {

        var valid = true

        val userName = signupUserNameText!!.text.toString()
        val password = signupPasswordText!!.text.toString()
        val rePassword = signupRePasswordText!!.text.toString()
        val email = signupEmailText!!.text.toString()

        // Check if the user name has the right form
        if (userName.isEmpty() || userName.length < 3) {
            signupUserNameText!!.error = "at least 3 characters"
            valid = false
        } else {
            signupUserNameText!!.error = null
        }

        // Check if email is valid
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_input!!.error = "enter a valid email address"
            valid = false
        } else {
            email_input!!.error = null
        }


        // Check if the password has the right form
        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            signupPasswordText!!.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            signupPasswordText!!.error = null
        }

        // Check if the retype password matches with the password
        if (rePassword.isEmpty() || rePassword.length < 4
            || rePassword.length > 10 || rePassword != password) {
            signupRePasswordText!!.error = "Passwords do not match"
            valid = false
        } else {
            signupRePasswordText!!.error = null
        }

        return valid
    }

}
