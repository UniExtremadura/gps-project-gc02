package com.example.gc02.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs

import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.ActivityRealizarCompraBinding
import com.example.gc02.model.Article
import com.example.gc02.view.home.ConsultarDetallesArticuloFragmentArgs
import kotlinx.coroutines.launch


class RealizarCompraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealizarCompraBinding
    private lateinit var db: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = BaseDatos.getInstance(applicationContext)!!

        //view binding and set content view
        binding = ActivityRealizarCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //views initialization and listeners
        //setUpUI()
        setUpListeners()

    }

    /**
    private fun setUpUI() {
        //get attributes from xml using binding
    }*/

    private fun setUpListeners() {
        val shop = intent.getSerializableExtra("shop") as Article
        with(binding) {

            comprarArtculoButton.setOnClickListener {
                lifecycleScope.launch {
                    if(shop != null) {
                        db.articleDao().delete(shop)
                        navigateToValoracion()
                        Toast.makeText(
                            this@RealizarCompraActivity,
                            "Articulo comprado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
            cancelarComprarArticulobutton.setOnClickListener{
                finish()
            }
        }
    }

    private fun navigateToValoracion() {
        val shop = intent.getSerializableExtra("shop") as Article
        val intent = Intent(this, ValoracionActivity::class.java)
        intent.putExtra("sellerId", "")
        startActivity(intent)
    }


}


