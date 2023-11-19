package com.example.gc02.views

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import  com.example.gc02.databinding.ActivityBorrarPerfilBinding
import com.example.gc02.model.User
import com.example.gc02.R

class BorrarPerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBorrarPerfilBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityBorrarPerfilBinding.inflate(layoutInflater)
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

            borrarPerfilButton.setOnClickListener {
                navigateToLoginActivity()
            }

            cancelarBorradoPerfilbutton.setOnClickListener {
                navigateToJoin()
            }
        }
    }

    private fun navigateToLoginActivity() {
        // LoginActivity.start()
    }

    private fun navigateToJoin() {
        //   ConsultarPerfilActivity.start(this, responseLauncher)
    }


    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}