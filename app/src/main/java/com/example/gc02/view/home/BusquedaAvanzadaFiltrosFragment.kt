package com.example.gc02.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import  com.example.gc02.databinding.FragmentBusquedaAvanzadaFiltrosBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class BusquedaAvanzadaFiltrosFragment : Fragment() {
    private var _binding: FragmentBusquedaAvanzadaFiltrosBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusquedaAvanzadaFiltrosBinding.inflate(inflater, container, false)
        return binding.root
    }
}