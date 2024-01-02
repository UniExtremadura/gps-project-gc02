package com.example.gc02.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.gc02.RetroReuseApplication
import com.example.gc02.data.Repository
import com.example.gc02.model.Article
import com.example.gc02.model.User
import kotlinx.coroutines.launch

class MisFavoritosViewModel(
    private val repository: Repository
) : ViewModel() {

    val misArticulosFavs = repository.shopsFavUser

    var user: User? = null
        set(value) {
            field = value
            repository.setUserid(value!!.userId!!)
        }
    fun setNoFavorite(shop: Article){
        viewModelScope.launch {
            shop.isFavorite = false
            repository.deleteShopFavorite(shop,user!!.userId!!)
        }
    }
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return MisFavoritosViewModel(
                    (application as RetroReuseApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}