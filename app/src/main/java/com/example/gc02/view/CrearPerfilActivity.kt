package com.example.gc02.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gc02.database.BaseDatos

import com.example.gc02.databinding.ActivityCrearPerfilBinding
import com.example.gc02.model.User
import com.example.gc02.utils.CredentialCheck
import kotlinx.coroutines.launch

class CrearPerfilActivity : AppCompatActivity(){

    private lateinit var db: BaseDatos
    private lateinit var binding: ActivityCrearPerfilBinding

    companion object {
        const val USUARIO = "NEW_USUARIO"
        const val EMAIL = "NEW_EMAIL"
        const val PASSWORD = "NEW_PASSWORD"
        const val REPASSWORD = "NEW_REPASSWORD"

        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, CrearPerfilActivity::class.java)
            responseLauncher.launch(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BaseDatos.getInstance(applicationContext)!!
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btRegister.setOnClickListener {
                join()
            }
        }
    }

    private fun join() {
        with(binding) {
            val check = CredentialCheck.join(
                etUsername.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString(),
                etRePassword.text.toString()
            )
            if (check.fail) notifyInvalidCredentials(check.msg)
            else {
                lifecycleScope.launch{
                    val user = User(
                        null,
                        etUsername.text.toString(),
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                    val id =  db?.userDao()?.insert(user)

                    navigateBackWithResult(
                        User(
                            id,
                            etUsername.text.toString(),
                            etEmail.text.toString(),
                            etPassword.text.toString()
                        )
                    )
                }

            }
        }
    }

    private fun navigateBackWithResult(user: User){
        val intent = Intent().apply {
            putExtra(USUARIO, user.name)
            putExtra(EMAIL,user.email)
            putExtra(PASSWORD,user.password)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
    private fun notifyInvalidCredentials(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }




}