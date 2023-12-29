package com.example.gc02.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gc02.databinding.ActivityBorrarPerfilBinding
import com.example.gc02.model.User
import androidx.fragment.app.Fragment
import com.example.gc02.databinding.FragmentSettingBinding
import com.example.gc02.view.BorrarPerfilActivity
import com.example.gc02.view.LoginActivity
import com.example.gc02.view.ModifyProfileActivity
import com.example.gc02.view.home.HomeActivity

class BorrarPerfilActivity : AppCompatActivity(){
    private lateinit var binding: BorrarPerfilActivity

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding= ActivityBorrarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
        setUpListeners()
    }
    private fun setUpUI() {
        //get attributes from xml using binding
    }
    private fun setUpListeners() {

        with(binding) {

            borrarPerfilButton.setOnClickListener {

                navigateToLoginActivity()
                Toast.makeText(
                    applicationContext,
                    "Article deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }

            cancelarBorradoArticulobutton.setOnClickListener {
                navigateToJoin()
            }
        }
    }
    private fun navigateToLoginActivity() {

    }



    private fun navigateToJoin() {
        HomeActivity.start(this, user)
    }
}