package com.example.gc02.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gc02.databinding.FragmentSettingBinding
import com.example.gc02.view.LoginActivity

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

            // Cierra la actividad actual del fragmento si es necesario
            requireActivity().finish()
        }
        binding.btnVolver.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()

// Utiliza popBackStack() para navegar hacia atrás
            fragmentManager.popBackStack()

// Puedes añadir la transacción a la pila de retroceso (opcional)
            transaction.addToBackStack(null)

// Realiza el commit de la transacción
            transaction.commit()
        }
    }

}