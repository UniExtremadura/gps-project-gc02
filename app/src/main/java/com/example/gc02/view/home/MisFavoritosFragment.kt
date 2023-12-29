package com.example.gc02.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.FragmentMisFavoritosBinding
import com.example.gc02.model.Article
import com.example.gc02.model.User
import kotlinx.coroutines.launch


class MisFavoritosFragment : Fragment() {
    private lateinit var user: User

    private var _binding: FragmentMisFavoritosBinding? = null
    private lateinit var db: BaseDatos
    private var favShops : List<Article> = emptyList()
    private lateinit var articuloAdapter: ArticuloAdapter
    private lateinit var listener: OnShopClickListener
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var repository: Repository

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
        repository = Repository.getInstance(db.userDao(), db.articleDao(), getNetworkService())

        if (context is OnShopClickListener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        val userProvider = activity as UserProvider
        user = userProvider.getUser()

        sharedViewModel.listaFavoritos.observe(viewLifecycleOwner, Observer { nuevaListaFavoritos ->
            favShops = nuevaListaFavoritos
            articuloAdapter.updateData(favShops)
        })

        loadFavorites()
    }

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
            favShops = repository.getUserWithShopsFavorites(user.userId!!).shops
            sharedViewModel.listaFavoritos.value = repository.getUserWithShopsFavorites(user.userId!!).shops
            articuloAdapter.updateData(favShops)
            binding.spinner.visibility = View.GONE
        }
    }

    private fun setNoFavorite(shop: Article){
        lifecycleScope.launch {
            repository.deleteShopFavorite(shop, user.userId!!)
            eliminarDeFavoritos(shop)
        }
    }
    private fun eliminarDeFavoritos(articulo: Article) {
        sharedViewModel.eliminarDeFavoritos(articulo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}