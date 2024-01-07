package com.example.gc02.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.ActivityModificarArticuloBinding
import com.example.gc02.model.Article
import com.example.gc02.utils.ArticleCheck
import com.example.gc02.view.home.ConsultarDetallesArticuloFragment
import kotlinx.coroutines.launch

class ModificarArticuloActivity : AppCompatActivity() {
    private lateinit var db: BaseDatos
    private lateinit var binding: ActivityModificarArticuloBinding
    private lateinit var articulo: Article
    private lateinit var repository: Repository

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
        binding = ActivityModificarArticuloBinding.inflate(layoutInflater)
        db = BaseDatos.getInstance(applicationContext)!!
        repository = Repository.getInstance(db, getNetworkService())
        articulo = intent.getSerializableExtra("articulo") as Article
        setContentView(binding.root)
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            tituloProducto.setText(articulo.title)
            descripcionProducto.setText(articulo.description)
            precioArticulo.setText(articulo.price.toString())
            anadirButton.setOnClickListener {
                val check = ArticleCheck.modificar(
                    tituloProducto.text.toString(),
                    descripcionProducto.text.toString(),
                    precioArticulo.text.toString()
                )
                if (check.fail) notifyInvalidArticle(check.msg)
                else
                    lifecycleScope.launch {
                        articulo.title = tituloProducto.toString()
                        articulo.description = descripcionProducto.toString()
                        val precioString = precioArticulo.text.toString()
                        articulo.price = precioString.toDoubleOrNull() ?: 0.0
                        navigateBackWithResult(
                            articulo
                        )
                    }

            }
        }
    }

    private fun navigateBackWithResult(article: Article) {
        val intent = Intent().apply {
            putExtra(TITLE, article.title)
            putExtra(DESC, article.description)
            putExtra(PRICE, article.price)
        }
        setResult(RESULT_OK, intent)
        val intentModificarArticuloActivity= Intent(this, ConsultarDetallesArticuloFragment::class.java)
        startActivity(intentModificarArticuloActivity)
        finish()
    }
    private fun notifyInvalidArticle(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun notifyValidArticle(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}