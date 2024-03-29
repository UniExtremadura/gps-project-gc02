package com.example.gc02.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.gc02.RetroReuseApplication
import com.example.gc02.api.APIError
import com.example.gc02.data.Repository
import com.example.gc02.model.Article
import com.example.gc02.model.Comentario
import com.example.gc02.model.User
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

    private val _comentario = MutableLiveData<List<Comentario>>()
    val comentarios: LiveData<List<Comentario>>
        get() = _comentario
    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner
    init{
        _spinner.value = false
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

    fun getComments(){
        viewModelScope.launch {
            try{
                _spinner.value = true
                _comentario.value = repository.getAllByArticleComment(
                    shop?.articleId ?:0,
                    shop?.userId ?:0
                )
            } catch (e: Exception){
                _toast.value = "Error al obtener comentarios"
            } finally {
                _spinner.value = false
            }
        }
    }

    fun enviarComentario(nameUser: String, nuevoComentario: String) {
        viewModelScope.launch {
            try {
                val comment = Comentario(
                    null,
                    nameUser,
                    nuevoComentario,
                    shop?.articleId ?: 0,
                    shop?.userId ?: 0
                )
                val id = repository.insertComment(comment)

                // Después de enviar el comentario con éxito, actualiza la lista de comentarios
                getComments()

            } catch (e: Exception) {
                _toast.value = "Error al enviar el comentario"
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