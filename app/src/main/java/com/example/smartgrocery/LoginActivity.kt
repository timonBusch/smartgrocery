package com.example.smartgrocery

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    var loginButton: Button? = null
    var signUpButton: Button? = null
    var userNameText: EditText? = null
    var passwordText: EditText? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton =  findViewById(R.id.login_button)
        signUpButton = findViewById(R.id.signup_button)
        userNameText = findViewById(R.id.input_name)
        passwordText = findViewById(R.id.input_password)
        progressBar = findViewById(R.id.login_progress)


        loginButton!!.setOnClickListener{
            progressBar!!.visibility = View.VISIBLE

            login()
        }

        signUpButton!!.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    fun login() {
        
        if(!validate()) {
            onLoginFailed()
            return
        }

        val userName = userNameText!!.text.toString()
        val password = passwordText!!.text.toString()

        val corretUserName = userName == "Test" && password == "1234"

        // TODO: Authentication logic

        android.os.Handler().postDelayed(
            {
                if(corretUserName) {
                    progressBar!!.visibility = View.GONE
                    onLoginSuccess()
                } else{
                    onLoginFailed()
                }

            }, 3000
        )


    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == Activity.RESULT_OK) {

                // TODO: Registrieren

            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    fun onLoginSuccess() {
        loginButton!!.isEnabled = true

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun onLoginFailed() {
        progressBar!!.visibility = View.GONE
        Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_LONG).show()

        loginButton!!.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true

        var userName = userNameText!!.text.toString()
        var password = passwordText!!.text.toString()

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            passwordText!!.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            passwordText!!.error = null
        }
        return valid
    }


    companion object {
        private val TAG = "LoginActivity"
        private val REQUEST_SIGNUP = 0
    }


}
