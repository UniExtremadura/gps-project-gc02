package com.example.gc02.view.home

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.gc02.RetroReuseApplication
import com.example.gc02.api.APIError
import com.example.gc02.data.Repository
import com.example.gc02.model.Article
import com.example.gc02.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
class ConsultarArticuloViewModel(
    private val repository: Repository
): ViewModel() {
    var user: User? = null
    val shops = repository.shops

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    init{
        refresh()
    }

    private fun refresh() {
        launchDataLoad { repository.tryUpdateRecentShopsCache() }
    }

    fun onToastShown() {
        _toast.value = null
    }

    fun setFavorite(shop: Article){
        viewModelScope.launch {
            shop.isFavorite = true
            repository.insertShopFavorite(shop,user!!.userId!!)
        }
    }

    /**
     * Helper function to call a data load function with a loading spinner; errors will trigger a
     * Toast.
     *
     * By marking [block] as [suspend] this creates a suspend lambda which can call suspend
     * functions.
     *
     * @param block lambda to actually load data. It is called in the lifecycleScope. Before calling
     *              the lambda, the loading spinner will display. After completion or error, the
     *              loading spinner will stop.
     */
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: APIError) {
                _toast.value = error.message
            } finally {
                _spinner.value = false
            }
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

                return ConsultarArticuloViewModel(
                    (application as RetroReuseApplication).appContainer.repository,

                    ) as T
            }
        }
    }


}


