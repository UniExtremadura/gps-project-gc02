package com.example.gc02.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gc02.model.Article

class SharedViewModel : ViewModel() {

    val listaArticulos = MutableLiveData<List<Article>>()
    val listaFavoritos = MutableLiveData<List<Article>>()

    // Método para agregar un artículo a la lista de favoritos
    fun agregarAFavoritos(articulo: Article) {
        val listaFavoritosActual = listaFavoritos.value ?: emptyList()
        val nuevaListaFavoritos = listaFavoritosActual + articulo
        listaFavoritos.value = nuevaListaFavoritos
    }
}
