package com.example.smartgrocery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar

class SignUpActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    var register_Button: Button? = null
    var signupUserNameText: EditText? = null
    var signupPasswordText: EditText? = null
    var signupRePasswordText: EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        progressBar = findViewById(R.id.signup_progress)
        register_Button = findViewById(R.id.btn_register)
        signupPasswordText = findViewById(R.id.signup_password)
        signupRePasswordText = findViewById(R.id.signup_repassword)
        signupUserNameText = findViewById(R.id.signup_name_input)

        register_Button!!.setOnClickListener{
            signUp()

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
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
        val rePassword = signupRePasswordText!!.text.toString()

        val corretlySingedUp: Boolean = false

        // TODO: SignUp logic

        android.os.Handler().postDelayed(
            {
                if(corretlySingedUp) {

                }
            }, 3000
        )

    }

    private fun validate(): Boolean {

        return false
    }

    private fun onSignUpFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
