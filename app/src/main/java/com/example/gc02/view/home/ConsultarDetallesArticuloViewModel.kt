package com.example.gc02.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.gc02.RetroReuseApplication
import com.example.gc02.api.APIError
import com.example.gc02.data.Repository
import com.example.gc02.data.toShop
import com.example.gc02.model.Article
import com.example.gc02.model.User
import com.example.gc02.model.UserwithShops
import kotlinx.coroutines.launch

class ConsultarDetallesArticuloViewModel (
    private val repository: Repository
) : ViewModel() {

    private val _shopDetail = MutableLiveData<Article>(null)
    val shopDetail: LiveData<Article>
    get() = _shopDetail

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
    get() = _toast

    var user: User? = null
    var shop: Article? = null
    set(value) {
        field = value
        getShop()
    }

    private fun getShop() {
        if (shop!=null)
            viewModelScope.launch{
                try{
                    val _shop = shop!!.articleId?.let { repository.getShop(it) }
                    if (_shop != null) {
                        _shop.isFavorite = shop!!.isFavorite
                    }
                    _shopDetail.value = _shop!!
                } catch (error: APIError) {
                    _toast.value = error.message
                }
            }
    }

    fun setFavorite(shop: Article){
        viewModelScope.launch {
            shop.isFavorite = true
            repository.insertShopFavorite(shop,user!!.userId!!)
        }
    }
    fun setNoFavorite(shop: Article){
        viewModelScope.launch {
            shop.isFavorite = false
            repository.deleteShopFavorite(shop,user!!.userId!!)
        }
    }

    fun onToastShown() {
        _toast.value = null
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

                return ConsultarDetallesArticuloViewModel(
                    (application as RetroReuseApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}