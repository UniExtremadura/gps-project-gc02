package com.example.gc02.view


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.gc02.R

class CrearPerfilActivity : AppCompatActivity(){

    private lateinit var binding: CrearPerfilActivity

    private lateinit var Et_nombre_usuario: EditText
    private lateinit var Et_email: EditText
    private lateinit var Et_password: EditText
    private lateinit var Btn_registrar: Button


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_perfil)
        supportActionBar!!.title = "Registro"
        InicializarVariables()


    }

    private fun InicializarVariables(){
        Et_nombre_usuario = findViewById(R.id.et_username)
        Et_email = findViewById(R.id.et_email)
        Et_password = findViewById(R.id.et_repassword)
        Btn_registrar = findViewById(R.id.bt_register)
    }


}