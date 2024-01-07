package com.example.gc02.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.api.APIError
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.api.Shop
import com.example.gc02.databinding.FragmentListaArticulosBinding
import com.example.gc02.model.Article
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch



private const val TAG = "ConsultarArticuloFragment"
/**
 * A simple [Fragment] subclass.
 * Use the [ConsultarArticuloFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsultarArticuloFragment : Fragment() {

    private var _binding: FragmentListaArticulosBinding? = null
    private val viewModel: ConsultarArticuloViewModel by viewModels { ConsultarArticuloViewModel.Factory }

    private val binding get() = _binding!!
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
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)

        if (context is OnShopClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnShopClickListener")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        val userProvider = activity as UserProvider
        val user = userProvider.getUser()
        viewModel.user = user
        // show the spinner when [spinner] is true
        viewModel.spinner.observe(viewLifecycleOwner) { show ->
            binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
        }

        // Show a Toast whenever the [toast] is updated a non-null value
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let { Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }
        subscribeUi(articuloAdapter)
    }

    private fun subscribeUi(adapter: ArticuloAdapter) {
        if (!args.precio.equals("") && !args.nombre.equals("")) {
            viewModel.shops.observe(viewLifecycleOwner) { shops ->
                adapter.updateData(shops.filter {
                    it.title.contains(
                        args.nombre,
                        ignoreCase = true
                    ) && it.price <= args.precio.toDouble()
                })
            }
        } else {
            if (!args.nombre.equals("")) {
                viewModel.shops.observe(viewLifecycleOwner) { shops ->
                    adapter.updateData(shops.filter {
                        it.title.contains(
                            args.nombre,
                            ignoreCase = true
                        )
                    })
                }
            } else {
                if (!args.precio.equals("")) {
                    viewModel.shops.observe(viewLifecycleOwner) { shops ->
                        adapter.updateData(shops.filter {
                            it.price <= args.precio.toDouble()
                        })
                    }
                } else {
                    viewModel.shops.observe(viewLifecycleOwner) { shops ->
                        adapter.updateData(shops)
                    }
                }
            }

        }
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
        Log.d("setUpRecyclerView", "Entro en el setUpRecyclerView")

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

                    Log.d("Consultar", "Consultar nombre $args.nombre y precio $args.precio")


        android.util.Log.d("ArticulosFragment", "setUpRecyclerView")
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
        return lifecycleScope.launch {
            try {
                binding.spinner.visibility = View.VISIBLE
                block()
            } catch (error: APIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            } finally {
                binding.spinner.visibility = View.GONE
            }
        }
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













