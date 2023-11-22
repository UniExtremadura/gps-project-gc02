package com.example.gc02.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.gc02.Database.BaseDatos

import  com.example.gc02.databinding.ActivityLoginBinding
import com.example.gc02.model.User
import com.example.gc02.utils.CredentialCheck
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var db: BaseDatos

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                with(result.data) {
                    val name = this?.getStringExtra(CrearPerfilActivity.USUARIO).orEmpty()
                    val pass = this?.getStringExtra(CrearPerfilActivity.PASSWORD).orEmpty()

                    with(binding) {
                        password.setText(pass)
                        username.setText(name)
                    }

                    Toast.makeText(
                        this@LoginActivity,
                        "New user ($name/$pass) created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding and set content view
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BaseDatos.getInstance(applicationContext)!!
        //views initialization and listeners
        setUpUI()
        setUpListeners()

    }


    private fun setUpUI() {
        //get attributes from xml using binding
    }

    private fun checkLogin(){
        val check = CredentialCheck.login(binding.username.text.toString(), binding.password.text.toString())
        if (!check.fail){
            lifecycleScope.launch{
                val user = db?.userDao()?.findByName(binding.username.text.toString()) //?: User(-1, etUsername.text.toString(), etPassword.text.toString())
                if (user != null) {
                    // db.userDao().insert(User(-1, etUsername.text.toString(), etPassword.text.toString()))
                    val check = CredentialCheck.passwordOk(binding.password.text.toString(), user.password)
                    if (check.fail) notifyInvalidCredentials(check.msg)
                    else navigateToHomeActivity(user!!, check.msg)
                }
                else notifyInvalidCredentials("Invalid username")
            }
        }
        else notifyInvalidCredentials(check.msg)
    }
    private fun setUpListeners() {
        with(binding) {

            loginbutton.setOnClickListener {
                checkLogin()
            }

            registerbutton.setOnClickListener {
                navigateToJoin()
            }
        }
    }

    private fun navigateToHomeActivity(user: User, msg: String) {
        // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        HomeActivity.start(this, user)
    }

    private fun navigateToJoin() {
        CrearPerfilActivity.start(this, responseLauncher)
    }


    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}