package com.example.gc02.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.gc02.R
import com.example.gc02.databinding.ActivityCrearPerfilBinding
import com.example.gc02.model.User
import com.example.gc02.utils.CredentialCheck

class CrearPerfilActivity : AppCompatActivity(){

    private lateinit var binding: ActivityCrearPerfilBinding

    private lateinit var Et_nombre_usuario: EditText
    private lateinit var Et_email: EditText
    private lateinit var Et_password: EditText
    private lateinit var Btn_registrar: Button

    companion object {
        const val USUARIO = "NEW_USUARIO"
        const val EMAIL = "NEW_EMAIL"
        const val PASSWORD = "NEW_PASSWORD"

        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, ActivityCrearPerfilBinding::class.java)
            responseLauncher.launch(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
           Btn_registrar.setOnClickListener{
                val check = CredentialCheck.join(
                    Et_nombre_usuario.text.toString(),
                    Et_email.text.toString(),
                    Et_password.text.toString()
                )
               if(check.fail) notifyInvalidCredentials(check.msg)
               else
                   navigateBackWithResult(
                       User(Et_nombre_usuario.text.toString(),
                           Et_email.text.toString(),
                           Et_password.text.toString())
                   )
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