package com.example.gc02.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gc02.databinding.FragmentSettingBinding
import com.example.gc02.model.User
import com.example.gc02.view.BorrarPerfilActivity
import com.example.gc02.view.LoginActivity
import com.example.gc02.view.ModifyProfileActivity


class SettingFragment : Fragment() {
    private lateinit var user : User
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        val userProvider = activity as UserProvider
        user = userProvider.getUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

            // Cierra la actividad actual del fragmento si es necesario
            requireActivity().finish()
        }
        binding.btnModificarPerfil.setOnClickListener{
            val intent = Intent(requireContext(),ModifyProfileActivity::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnBorrarPerfil.setOnClickListener{
            val intent = Intent(requireContext(),BorrarPerfilActivity::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
            requireActivity().finish()
        }

    }

}