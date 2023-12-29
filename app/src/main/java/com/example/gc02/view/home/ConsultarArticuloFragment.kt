package com.example.gc02.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.api.APIError
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.api.Shop
import com.example.gc02.data.dummyArticulos
import com.example.gc02.data.toShop
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.FragmentListaArticulosBinding
import com.example.gc02.model.Article
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val TAG = "ConsultarArticuloFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [ConsultarArticuloFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsultarArticuloFragment : Fragment() {

    private var _binding: FragmentListaArticulosBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: BaseDatos

    private var _shops : List<Article> = emptyList()

    private lateinit var listener: OnShopClickListener

    private val args: ConsultarArticuloFragmentArgs by navArgs()

    interface OnShopClickListener {
        fun onShopClick(shop: Article)
    }

    private lateinit var articuloAdapter: ArticuloAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaArticulosBinding.inflate(inflater, container, false)

        setUpRecyclerView()


        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = BaseDatos.getInstance(context)!!
        if (context is OnShopClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnShopClickListener")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private suspend fun fetchShops(): List<Shop> {
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
    }
    private fun setUpRecyclerView() {
        articuloAdapter = ArticuloAdapter(
            shops = _shops,
            onClick = {
                listener.onShopClick(it)
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

        lifecycleScope.launch {
            if (_shops.isEmpty()) {
                binding.spinner.visibility = View.VISIBLE
                try {
                    _shops = db.articleDao().getAll()
                   _shops = _shops + fetchShops().map(Shop::toShop)

                    Log.d("Consultar", "Consultar nombre $args.nombre y precio $args.precio")
                    if(!args.precio.equals("") && !args.nombre.equals("")) {
                        articuloAdapter.updateData(_shops.filter {
                            it.title.contains(
                                args.nombre,
                                ignoreCase = true
                            ) && it.price <= args.precio.toDouble()
                        })

                    } else {
                        if (!args.nombre.equals("")){
                            articuloAdapter.updateData(_shops.filter {
                                it.title.contains(
                                    args.nombre,
                                    ignoreCase = true
                                )
                            })

                        }else{
                        if(!args.precio.equals("")) {
                            articuloAdapter.updateData(_shops.filter {
                                it.price <= args.precio.toDouble()
                            })

                        }else{
                            articuloAdapter.updateData(_shops)

                        }
                        }
                    }
                } catch (error: APIError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                } finally {
                    binding.spinner.visibility = View.GONE

                }
            }
        }
        android.util.Log.d("ArticulosFragment", "setUpRecyclerView")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment ShowDetailFragment.
         */
        @JvmStatic
        fun newInstance(): ConsultarArticuloFragment {
            return ConsultarArticuloFragment()
        }
    }
}













