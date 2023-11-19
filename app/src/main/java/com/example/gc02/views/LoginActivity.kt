package com.example.gc02.views

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import  com.example.gc02.databinding.ActivityLoginBinding
import com.example.gc02.model.User
import com.example.gc02.utils.CredentialCheck
import com.example.gc02.views.HomeActivity
import com.example.gc02.R

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //views initialization and listeners
        setUpUI()
        setUpListeners()

    }


    private fun setUpUI() {
        //get attributes from xml using binding
    }

    private fun setUpListeners() {
        with(binding) {

            loginbutton.setOnClickListener {
                val check = CredentialCheck.login(username.text.toString(), password.text.toString())

                if (check.fail) notifyInvalidCredentials(check.msg)
                else navigateToHomeActivity(User(username.text.toString(), password.text.toString()), check.msg)
            }

            registerbutton.setOnClickListener {
                navigateToJoin()
            }
        }
    }

    private fun navigateToHomeActivity(user: User, msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
       // HomeActivity.start(this, user)
    }

    private fun navigateToJoin() {
     //   JoinActivity.start(this, responseLauncher)
    }


    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}