package com.example.gc02.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.lifecycleScope
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos

import com.example.gc02.databinding.ActivityBorrarArticuloBinding
import com.example.gc02.databinding.ActivityBorrarPerfilBinding
import com.example.gc02.databinding.ActivityModificarArticuloBinding
import com.example.gc02.model.Article
import com.example.gc02.model.User
import com.example.gc02.view.home.HomeActivity
import kotlinx.coroutines.launch

class BorrarArticuloActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBorrarArticuloBinding
    private lateinit var repository: Repository
    private lateinit var db: BaseDatos
    private lateinit var user : User
    private lateinit var articulo: Article

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding= ActivityBorrarArticuloBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = BaseDatos.getInstance(applicationContext)!!
        repository = Repository.getInstance(db, getNetworkService())
        articulo = (intent.getSerializableExtra("articulo") as Article)

        setUpListeners()
    }

    companion object {
        const val TITLE = "NEW_TITLE"
        const val DESC = "NEW_DESC"
        const val PRICE = "NEW_PRICE"

        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, ActivityModificarArticuloBinding::class.java)
            responseLauncher.launch(intent)
        }
    }

    private fun setUpListeners() {

        with(binding) {

            borrarArtculoButton.setOnClickListener {
                lifecycleScope.launch {
                    if(articulo!=null){
                        repository.deleteArticulo(articulo)
                        Log.d("Borrado","Borrado correctamente")
                    } else Log.d("Borrar Articulo", "No se ha podido borrar, articulo null")
                    navigateToHomeActivity()
                    Toast.makeText(
                        applicationContext,
                        "Article deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            cancelarBorradoArticulobutton.setOnClickListener {
                navigateToHomeActivity()
            }
        }
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }




}