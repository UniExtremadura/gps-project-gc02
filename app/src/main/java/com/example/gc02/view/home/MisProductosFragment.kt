package com.example.gc02.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.FragmentMisProductosBinding
import com.example.gc02.model.Article
import com.example.gc02.model.User
import com.example.gc02.view.AnadirArticuloALaVentaActivity
import kotlinx.coroutines.launch


class MisProductosFragment : Fragment() {
    private lateinit var db: BaseDatos
    private var shops : List<Article> = emptyList()
    private lateinit var articuloAdapter: ArticuloAdapter
    private lateinit var listener: MisFavoritosFragment.OnShopClickListener

    private lateinit var user: User

    private var _binding: FragmentMisProductosBinding? = null
    interface OnShopClickListener {
        fun onShopClick(article: Article)
    }

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
        db = BaseDatos.getInstance(context)!!

        if (context is MisFavoritosFragment.OnShopClickListener) {
            listener = context
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        val userProvider = activity as UserProvider
        user = userProvider.getUser()

        loadArticles()

    }
    private fun setUpRecyclerView() {
        articuloAdapter = ArticuloAdapter(
            shops = shops,
            onClick = {
                Toast.makeText(context, "click on: "+it.title, Toast.LENGTH_SHORT).show()
                //listener.onShopClick(it)
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
    private fun loadArticles(){
        lifecycleScope.launch {
            binding.spinner.visibility = View.VISIBLE
            shops = db.articleDao().getAllByUser(user.userId!!)
            articuloAdapter.updateData(shops)
            binding.spinner.visibility = View.GONE
        }
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
        fun newInstance(): MisProductosFragment {
            return MisProductosFragment()
        }
    }

}