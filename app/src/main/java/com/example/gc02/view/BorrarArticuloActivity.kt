package com.example.gc02.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

import com.example.gc02.databinding.ActivityBorrarArticuloBinding

class BorrarArticuloActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBorrarArticuloBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityBorrarArticuloBinding.inflate(layoutInflater)
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

            borrarArtculoButton.setOnClickListener {
                navigateToLoginActivity()
            }

            cancelarBorradoArticulobutton.setOnClickListener {
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



}