package com.example.gc02.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.databinding.FragmentMisProductosBinding
import com.example.gc02.model.Article
import com.example.gc02.model.User
import com.example.gc02.view.AnadirArticuloALaVentaActivity
import kotlinx.coroutines.launch


class MisProductosFragment : Fragment() {
    private var shops : List<Article> = emptyList()
    private lateinit var articuloAdapter: ArticuloAdapter
    private lateinit var listener: TabLayoutFragment.OnShopClickListener

    private lateinit var user: User
    private val viewModel:  MisProductosViewModel by viewModels { MisProductosViewModel.Factory }

    private var _binding: FragmentMisProductosBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMisProductosBinding.inflate(inflater, container, false)
        setUpListeners()

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
        viewModel.misArticulos.observe(viewLifecycleOwner) { misArticulos ->
            adapter.updateData(misArticulos)
        }
    }
    private fun setUpRecyclerView() {
        articuloAdapter = ArticuloAdapter(
            shops = shops,
            onClick = {
                listener.onShopClickProductosPerfil(it)
            },
            onLongClick = {
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
    private fun setUpListeners() {
        with(binding) {

            buttonAnadirProducto.setOnClickListener {
                // Crear un Intent para iniciar AnadirArticuloALaVentaActivity
                val intent = Intent(requireContext(), AnadirArticuloALaVentaActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
        }
    }
    private fun loadArticles(misArticles:List<Article>){
        lifecycleScope.launch {
            binding.spinner.visibility = View.VISIBLE
            articuloAdapter.updateData(misArticles)
            binding.spinner.visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(): MisProductosFragment {
            return MisProductosFragment()
        }
    }

}