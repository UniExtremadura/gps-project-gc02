package com.example.gc02.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.gc02.R
import com.example.gc02.databinding.ActivityModificarArticuloBinding
import com.example.gc02.model.User

class ModificarArticuloActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModificarArticuloBinding


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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarArticuloBinding.inflate(R.layout.activity_modificar_articulo)
        setContentView(binding,savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            añadirButton.setOnClickListener {
                val check = CredentialCheck.join_Modificar(
                    tituloProducto.text.toString(),
                    descripcionProducto.text.toString(),
                    precioArticulo.text.toString()
                )
                    navigateBackWithResult(
                        Article(
                            tituloProducto.text.toString(),
                            descripcionProducto.text.toString(),
                            precioArticulo.text.toString()
                        )
                    )
            }
        }
    }

    private fun navigateBackWithResult(user: User) {
        val intent = Intent().apply {
            putExtra(TITLE, article.title)
            putExtra(DESC, article.description)
            putExtra(PRICE, article.price)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

}