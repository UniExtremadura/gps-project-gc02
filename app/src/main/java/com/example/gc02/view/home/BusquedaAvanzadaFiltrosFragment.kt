package com.example.gc02.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gc02.R
import com.example.gc02.databinding.FragmentBusquedaAvanzadaFiltrosBinding

class BusquedaAvanzadaFiltrosFragment : Fragment(R.layout.fragment_busqueda_avanzada_filtros) {

    private var _binding: FragmentBusquedaAvanzadaFiltrosBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusquedaAvanzadaFiltrosBinding.inflate(inflater, container, false)

        with(binding){
        buttonSearch.setOnClickListener {
            Log.d("Busqueda","setOnCLickListener")
            val nombreBusqueda = editTextName.text.toString()
            val precioBusqueda = editPrice.text.toString()
            val action = BusquedaAvanzadaFiltrosFragmentDirections.actionPageHomeToPageArticulosFiltros(
                nombreBusqueda,
                precioBusqueda
            )
            Toast.makeText(
                context,
                "Filtros correctos",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(action)
        }
        }
        return binding.root
    }
}