package com.example.gc02.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gc02.databinding.ActivityAnadirArticuloVentaBinding
import com.example.gc02.model.Article
import com.example.gc02.utils.ArticleCheck
import com.example.gc02.database.BaseDatos
import kotlinx.coroutines.launch

class AnadirArticuloALaVentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirArticuloVentaBinding

    private lateinit var db: BaseDatos

    companion object {

        const val TITLE = "NEW_TITLE"
        const val DESC = "NEW_DESC"
        const val PRICE = "NEW_PRICE"

        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, AnadirArticuloALaVentaActivity::class.java)
            responseLauncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnadirArticuloVentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BaseDatos.getInstance(applicationContext)!!

        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding){
            anadirButton.setOnClickListener {
               join()
            }
        }
    }

    private fun join() {
        with(binding) {
            val check = ArticleCheck.insert(
                tituloProducto.text.toString(),
                descripcionProducto.text.toString(),
                precioArticulo.text.toString()
            )
            if (check.fail) notifyInvalidArticle(check.msg)
            else {
                lifecycleScope.launch{
                    val article = Article(
                        0,
                        tituloProducto.text.toString(),
                        descripcionProducto.text.toString(),
                        precioArticulo.text.toString()
                    )
                    val id =  db?.articleDao()?.insert(article)

                    navigateBackWithResult(
                        Article(
                            id,
                            tituloProducto.text.toString(),
                            descripcionProducto.text.toString(),
                            precioArticulo.text.toString()
                        )
                    )
                }

            }
        }
    }

    private fun navigateBackWithResult(article: Article) {
        val intent = Intent().apply {
            putExtra(TITLE,article.title)
            putExtra(DESC,article.description)
            putExtra(PRICE,article.price)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun notifyInvalidArticle(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}