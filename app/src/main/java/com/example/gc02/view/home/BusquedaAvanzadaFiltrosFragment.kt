package com.example.gc02.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import  com.example.gc02.databinding.FragmentBusquedaAvanzadaFiltrosBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gc02.R
import com.example.gc02.data.dummyArticulos
import com.example.gc02.data.dummyComentarios
import com.example.gc02.model.Article

class BusquedaAvanzadaFiltrosFragment : Fragment(R.layout.fragment_busqueda_avanzada_filtros) {
    private lateinit var binding: FragmentBusquedaAvanzadaFiltrosBinding
    private lateinit var listaArticulos: List<Article>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentBusquedaAvanzadaFiltrosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_busqueda_avanzada_filtros, container, false)
        listaArticulos = dummyArticulos

        val editTextNombre = view.findViewById<EditText>(R.id.editTextName)
        val editTextPrecio = view.findViewById<EditText>(R.id.editPrice)
        val btnBuscar = view.findViewById<Button>(R.id.buttonSearch)

        btnBuscar.setOnClickListener {
            val nombreBusqueda = editTextNombre.text.toString()
            val precioBusqueda = editTextPrecio.text.toString()
            val resultados = filtrarArticulos(nombreBusqueda, precioBusqueda.toDoubleOrNull())
        }

        return view
    }
    private fun obtenerListaDeArticulos(): List<Article> {
        // Devuelve la lista de artículos (puedes cargarla de una base de datos, un archivo, etc.)
        return listOf(
            // ... Lista de artículos ...
        )
    }

    private fun filtrarArticulos(nombre: String, precio: Double?): List<Article> {
        return listaArticulos.filter { articulo ->
            // Filtra por nombre y/o precio según los criterios de búsqueda
            val cumpleNombre = nombre.isEmpty() || articulo.title.contains(nombre, ignoreCase = true)
            val cumplePrecio = precio == null || articulo.price.toDouble() <= precio
            cumpleNombre && cumplePrecio
        }
    }

}