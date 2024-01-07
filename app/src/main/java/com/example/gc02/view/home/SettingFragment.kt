package com.example.gc02.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gc02.databinding.FragmentSettingBinding
import com.example.gc02.view.BorrarPerfilActivity
import com.example.gc02.view.LoginActivity
import com.example.gc02.view.ModifyProfileActivity

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
           /* Toast.makeText(
                this@SettingFragment,
                "Sesion cerrada",
                Toast.LENGTH_SHORT
            ).show()*/
            // Cierra la actividad actual del fragmento si es necesario
            requireActivity().finish()
        }
        binding.btnModificarPerfil.setOnClickListener{
            val intent = Intent(requireContext(),ModifyProfileActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnBorrarPerfil.setOnClickListener{
            /*val intent = Intent(requireContext(),BorrarPerfilActivity::class.java)
            startActivity(intent)
            requireActivity().finish()*/
        }

    }

}