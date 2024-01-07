package com.example.gc02.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.ActivityBorrarPerfilBinding
import com.example.gc02.model.User
import com.example.gc02.view.home.HomeActivity
import kotlinx.coroutines.launch

class BorrarPerfilActivity : AppCompatActivity(){
    private lateinit var binding: ActivityBorrarPerfilBinding
    private lateinit var repository: Repository
    private lateinit var db: BaseDatos
    private lateinit var user : User
    companion object {

        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, BorrarPerfilActivity::class.java)
            responseLauncher.launch(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding= ActivityBorrarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = BaseDatos.getInstance(applicationContext)!!
        repository = Repository.getInstance(db, getNetworkService())
        user = (intent.getSerializableExtra("user") as User)

        setUpListeners()
    }

    private fun setUpListeners() {

        with(binding) {

            borrarPerfilButton.setOnClickListener {
                lifecycleScope.launch {
                if(user != null){
                    repository.deleteUser(user)
                    Log.d("Borrado","Borrado correctamente")
                } else Log.d("Borrar Perfil", "No se ha podido borrar, user null")
                navigateToLoginActivity()
                Toast.makeText(
                    applicationContext,
                    "Perfil deleted",
                    Toast.LENGTH_SHORT
                ).show()
                }
            }

            cancelarBorradoPerfilbutton.setOnClickListener {
                navigateToJoin()
            }
        }
    }
    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }



    private fun navigateToJoin() {
        HomeActivity.start(this, user)
    }
}