package com.example.gc02.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gc02.databinding.FragmentMisProductosBinding
import com.example.gc02.view.AnadirArticuloALaVentaActivity

class MisProductosFragment : Fragment() {

    private var _binding: FragmentMisProductosBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMisProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {

            buttonAnadirProducto.setOnClickListener {
                // Crear un Intent para iniciar AnadirArticuloALaVentaActivity
                val intent = Intent(requireContext(), AnadirArticuloALaVentaActivity::class.java)
                startActivity(intent)
            }
        }
    }

}