package com.example.gc02.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import  com.example.gc02.databinding.FragmentBusquedaAvanzadaFiltrosBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gc02.R

class BusquedaAvanzadaFiltrosFragment : Fragment(R.layout.fragment_busqueda_avanzada_filtros) {
    private lateinit var binding: FragmentBusquedaAvanzadaFiltrosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentBusquedaAvanzadaFiltrosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_busqueda_avanzada_filtros, container, false)
    }
}