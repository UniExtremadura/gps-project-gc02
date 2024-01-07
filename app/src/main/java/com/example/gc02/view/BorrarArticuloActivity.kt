package com.example.gc02.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos

import com.example.gc02.databinding.ActivityBorrarArticuloBinding
import com.example.gc02.model.User

class BorrarArticuloActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBorrarArticuloBinding
    private lateinit var repository: Repository
    private lateinit var db: BaseDatos
    private lateinit var user : User

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
                Toast.makeText(
                    applicationContext,
                    "Article deleted",
                    Toast.LENGTH_SHORT
                ).show()
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