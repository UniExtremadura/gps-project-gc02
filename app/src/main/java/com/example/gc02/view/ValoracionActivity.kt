package com.example.gc02.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gc02.R
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.ActivityCrearPerfilBinding
import com.example.gc02.databinding.ActivityValoracionBinding
import com.example.gc02.databinding.FragmentSettingBinding
import com.example.gc02.model.Article
import com.example.gc02.model.User
import com.example.gc02.model.Valuation
import com.example.gc02.utils.ArticleCheck
import kotlinx.coroutines.launch

class ValoracionActivity : AppCompatActivity() {
    private lateinit var db: BaseDatos
    private lateinit var binding: ActivityValoracionBinding

    companion object {

        const val RATING = "NEW_RATING"
        const val COMMENT = "NEW_COMMENT"

        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, ValoracionActivity::class.java)
            responseLauncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValoracionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BaseDatos.getInstance(applicationContext)!!
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btEnviar.setOnClickListener {
                lifecycleScope.launch {
                    val valuation = Valuation(
                        null,
                        ratingBar.rating.toInt(),
                        editTextComentario.text.toString(),
                        intent.getSerializableExtra("sellerId") as Long
                    )
                    val id = db?.valuationDao()?.insert(valuation)
                    if (id != null) {
                        Log.d(
                            "Anyadir",
                            "El id de la valoracion realizada es ${
                                db.valuationDao().findById(id.toInt()).valId
                            }"
                        )
                    }
                    navigateBackWithResult(
                        Valuation(
                            id!!,
                            ratingBar.rating.toInt(),
                            editTextComentario.text.toString(),
                            userId = null
                        )
                    )
                }
            }
        }
    }
    private fun navigateBackWithResult(valuation: Valuation) {
        val intent = Intent().apply {
            putExtra(RATING,valuation.points)
            putExtra(COMMENT,valuation.comment)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
