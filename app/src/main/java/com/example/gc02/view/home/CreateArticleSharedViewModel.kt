package com.example.gc02.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gc02.model.Article

class CreateArticleSharedViewModel : ViewModel() {
    val listaMisProductos = MutableLiveData<List<Article>>()
}