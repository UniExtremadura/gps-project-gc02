package com.example.gc02.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos
import androidx.lifecycle.lifecycleScope
import com.example.gc02.databinding.ActivityModifyProfileBinding
import com.example.gc02.model.User
import com.example.gc02.utils.CredentialCheck
import com.example.gc02.view.home.HomeActivity
import com.example.gc02.view.home.SettingFragment
import kotlinx.coroutines.launch

class ModifyProfileActivity : AppCompatActivity() {
    private lateinit var db: BaseDatos
    private lateinit var binding: ActivityModifyProfileBinding
    private lateinit var repository: Repository
    private lateinit var usuario: User
    companion object {
        const val USUARIO = "NEW_USUARIO"
        const val EMAIL = "NEW_EMAIL"
        const val PASSWORD = "NEW_PASSWORD"

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
        db = BaseDatos.getInstance(applicationContext)!!
        repository = Repository.getInstance(db, getNetworkService())

        usuario = intent.getSerializableExtra("user") as User
        setContentView(binding.root)
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            etUsername.setText(usuario.name)
            etEmail.setText(usuario.email)
            etPassword.setText(usuario.password)

            btModificarPerfil.setOnClickListener {
                val check = CredentialCheck.join_Modificar(
                    etUsername.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
                if (check.fail) notifyInvalidCredentials(check.msg)
                else
                    lifecycleScope.launch {
                        usuario.name = etUsername.text.toString()
                        usuario.email = etEmail.text.toString()
                        usuario.password = etPassword.text.toString()
                        repository.updateUser(usuario)
                        navigateBackWithResult(
                            usuario
                        )
                    }

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
        val intentHomeActivity= Intent(this, HomeActivity::class.java)
        intentHomeActivity.putExtra("USER_INFO", usuario)
        startActivity(intentHomeActivity)
        finish()
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}