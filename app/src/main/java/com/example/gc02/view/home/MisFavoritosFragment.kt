package com.example.gc02.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.databinding.FragmentMisFavoritosBinding
import com.example.gc02.model.Article
import com.example.gc02.model.User


class MisFavoritosFragment : Fragment() {
    private lateinit var user: User

    private var _binding: FragmentMisFavoritosBinding? = null
    private var favShops : List<Article> = emptyList()
    private lateinit var articuloAdapter: ArticuloAdapter
    private lateinit var listener: TabLayoutFragment.OnShopClickListener
    private val viewModel:  MisFavoritosViewModel by viewModels { MisFavoritosViewModel.Factory }

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

        if (context is TabLayoutFragment.OnShopClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnShopClickListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        val userProvider = activity as UserProvider
        user = userProvider.getUser()
        viewModel.user = user

        subscribeUi(articuloAdapter)
    }
    private fun subscribeUi(adapter: ArticuloAdapter) {
        viewModel.misArticulosFavs.observe(viewLifecycleOwner) { misArticulosFavs ->
            adapter.updateData(misArticulosFavs.shops)
        }
    }
    private fun setUpRecyclerView() {
        articuloAdapter = ArticuloAdapter(
            shops = favShops,
            onClick = {
                //Toast.makeText(context, "click on: "+it.title, Toast.LENGTH_SHORT).show()
                listener.onShopClickProductosPerfil(it)
            },
            onLongClick = {
                setNoFavorite(it)
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



    private fun setNoFavorite(shop: Article){
        viewModel.setNoFavorite(shop)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment ShowDetailFragment.
         */
        @JvmStatic
        fun newInstance(): MisFavoritosFragment {
            return MisFavoritosFragment()
        }
    }
}