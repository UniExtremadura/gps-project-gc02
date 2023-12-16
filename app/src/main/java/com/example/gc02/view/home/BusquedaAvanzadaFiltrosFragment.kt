package com.example.gc02.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.gc02.R
import com.example.gc02.data.dummyArticulos
import com.example.gc02.databinding.FragmentBusquedaAvanzadaFiltrosBinding
import com.example.gc02.databinding.FragmentListaArticulosBinding
import com.example.gc02.model.Article

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
            val action = BusquedaAvanzadaFiltrosFragmentDirections.actionPageHomeToPageArticulos(
                nombreBusqueda,
                precioBusqueda
            )
            findNavController().navigate(action)
        }
            //val resultados = filtrarArticulos(nombreBusqueda, precioBusqueda.toDoubleOrNull())
        }
        return binding.root
    }

}