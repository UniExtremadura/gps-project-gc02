package com.example.gc02.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.gc02.databinding.ActivityModifyProfileBinding
import com.example.gc02.model.User
import com.example.gc02.utils.CredentialCheck

class ModifyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModifyProfileBinding


    companion object {
        const val USUARIO = "NEW_USUARIO"
        const val EMAIL = "NEW_EMAIL"
        const val PASSWORD = "NEW_PASSWORD"
        const val REPASSWORD = "NEW_REPASSWORD"

        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, ActivityModifyProfileBinding::class.java)
            responseLauncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            bt_modificar_perfil.setOnClickListener {
                val check = CredentialCheck.join_Modificar(
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
                if (check.fail) notifyInvalidCredentials(check.msg)
                else
                    navigateBackWithResult(
                        User(
                            etUsername.text.toString(),
                            etEmail.text.toString(),
                            etPassword.text.toString()
                        )
                    )
            }
        }
    }

    private fun navigateBackWithResult(user: User) {
        val intent = Intent().apply {
            putExtra(USUARIO, user.name)
            putExtra(EMAIL, user.email)
            putExtra(PASSWORD, user.password)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}