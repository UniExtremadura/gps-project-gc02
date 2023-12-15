package com.example.gc02.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.gc02.R
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.ActivityCrearPerfilBinding
import com.example.gc02.databinding.ActivityValoracionBinding
import com.example.gc02.databinding.FragmentSettingBinding
import com.example.gc02.model.Valuation

class ValoracionActivity : AppCompatActivity() {
    private lateinit var db: BaseDatos
    private lateinit var binding: ActivityValoracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValoracionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BaseDatos.getInstance(applicationContext)!!
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            //val valoracion = Valuation(null, ratingBar.rating, editTextComentario.text.toString())


        }
    }
}
