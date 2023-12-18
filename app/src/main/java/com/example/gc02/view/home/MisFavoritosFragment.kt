package com.example.gc02.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.R
import com.example.gc02.api.APIError
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.api.Shop
import com.example.gc02.data.toShop
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.FragmentMisFavoritosBinding
import com.example.gc02.databinding.FragmentMisProductosBinding
import com.example.gc02.model.Article
import com.example.gc02.model.User
import com.example.gc02.view.AnadirArticuloALaVentaActivity
import kotlinx.coroutines.launch


class MisFavoritosFragment : Fragment() {
    private lateinit var user: User

    private var _binding: FragmentMisFavoritosBinding? = null
    private lateinit var db: BaseDatos
    private var favShops : List<Article> = emptyList()
    private lateinit var articuloAdapter: ArticuloAdapter
    private lateinit var listener: OnShopClickListener

    interface OnShopClickListener {
        fun onShopClick(article: Article)
    }

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMisFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = BaseDatos.getInstance(context)!!

        if (context is OnShopClickListener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        val userProvider = activity as UserProvider
        user = userProvider.getUser()

        loadFavorites()
    }
    /*    private suspend fun fetchShops(): List<Shop> {
            var apiShops = listOf<Shop>()
            Log.d("API"," FETCHSHOPS")
            try {
                apiShops = getNetworkService().getCategories()
                Log.d("API","Shops:$apiShops")
            } catch (cause: Throwable) {
                Log.e("API", "Error fetching shop data", cause)
                throw APIError("Unable to fetch data from API", cause)
            }
            return apiShops
        }*/

    private fun setUpRecyclerView() {
        articuloAdapter = ArticuloAdapter(
            shops = favShops,
            onClick = {
                Toast.makeText(context, "click on: "+it.title, Toast.LENGTH_SHORT).show()
                //listener.onShopClick(it)
            },
            onLongClick = {
                setNoFavorite(it)
                loadFavorites()
                Toast.makeText(context, "long click on: "+it.title, Toast.LENGTH_SHORT).show()
            },
            context = this.context
        )
        with(binding) {
            rvShopList.layoutManager = LinearLayoutManager(context)
            rvShopList.adapter = articuloAdapter
        }
        android.util.Log.d("ArticulosFragment", "setUpRecyclerView")
    }

    private fun loadFavorites(){
        lifecycleScope.launch {
            binding.spinner.visibility = View.VISIBLE
            favShops = db.articleDao().getUserWithShops(user.userId!!).shops
            articuloAdapter.updateData(favShops)
            binding.spinner.visibility = View.GONE
        }
    }
    private fun setNoFavorite(shop: Article){
        lifecycleScope.launch {
            shop.isFavorite = false
            //delete show and userShow is deleted by cascade
            db.articleDao().delete(shop)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}