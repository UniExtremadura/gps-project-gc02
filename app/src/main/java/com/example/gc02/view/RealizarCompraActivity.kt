package com.example.gc02.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository

import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.ActivityRealizarCompraBinding
import com.example.gc02.model.Article
import kotlinx.coroutines.launch


class RealizarCompraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealizarCompraBinding
    private lateinit var db: BaseDatos
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = BaseDatos.getInstance(applicationContext)!!
        repository = Repository.getInstance(db, getNetworkService())
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
                        repository.deleteArticulo(shop)
                        navigateToValoracion()
                        Toast.makeText(
                            this@RealizarCompraActivity,
                            "Articulo comprado",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
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
        intent.putExtra("sellerId", shop.userId)
        startActivity(intent)
    }


}


