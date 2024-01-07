package com.example.gc02.view.home

import android.content.Intent
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
import com.bumptech.glide.Glide
import com.example.gc02.R
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.FragmentConsultarArticuloBinding
import com.example.gc02.model.Article
import com.example.gc02.model.Comentario
import com.example.gc02.model.User
import com.example.gc02.view.BorrarArticuloActivity
import com.example.gc02.view.BorrarPerfilActivity
import com.example.gc02.view.ModificarArticuloActivity
import com.example.gc02.view.ModifyProfileActivity
import com.example.gc02.view.RealizarCompraActivity
import kotlinx.coroutines.launch
class ConsultarDetallesArticuloFragment : Fragment() {
    private var userInfo: User? = null
    private val viewModel: ConsultarDetallesArticuloViewModel by viewModels { ConsultarDetallesArticuloViewModel.Factory }
    private lateinit var db: BaseDatos
    private lateinit var comentarioAdapter: ComentarioAdapter
    private var _comentarios: List<Comentario> = emptyList()
    private var _binding: FragmentConsultarArticuloBinding? = null
    private val binding get() = _binding!!
    private val args: ConsultarDetallesArticuloFragmentArgs by navArgs()

    private lateinit var repository: Repository

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = BaseDatos.getInstance(requireContext())!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsultarArticuloBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Repository.getInstance(db, getNetworkService())
        // Obtén el Intent de la actividad
        val intent = activity?.intent

        // Verifica si el Intent no es nulo y si contiene la clave USER_INFO
        if (intent?.hasExtra(HomeActivity.USER_INFO) == true) {
            // Obtén el objeto User del Intent
            userInfo = intent.getSerializableExtra(HomeActivity.USER_INFO) as? User
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerViewComentario()

        val shop = args.shop

        viewModel.shop = shop
        viewModel.getComments()
        viewModel.user = userInfo
        // show the spinner when [spinner] is true
        viewModel.shopDetail.observe(viewLifecycleOwner){ shop ->
            shop?.let { showBinding(shop)}
            if(viewModel.shop!!.userId != viewModel.user?.userId){
                binding.btBorrar.visibility = View.GONE
                binding.btModificar.visibility = View.GONE
            } else{
                binding.btBorrar.visibility = View.VISIBLE
                binding.btModificar.visibility = View.VISIBLE
            }

        }
        viewModel.spinner.observe(viewLifecycleOwner) { show ->
            binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
        }

        // Show a Toast whenever the [toast] is updated a non-null value
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        subscribeUi()
        subscribeUiComment()

        setUpListeners()


    }

    private fun subscribeUiComment( ){
        viewModel.comentarios.observe(viewLifecycleOwner){ comentarios->
            comentarioAdapter.updateData(comentarios)
        }
    }

    private fun subscribeUi() {
        viewModel.shopDetail.observe(viewLifecycleOwner) { shop ->
            shop?.let{ showBinding(shop) }
        }
    }
    private fun enviarComentario(nameUser: String) {
        val nuevoComentario = binding.editTextComentario.text.toString()
    Log.d("Comentario","Agregar comentario a articulo : ${args.shop.articleId}")

        lifecycleScope.launch {
            viewModel.enviarComentario(nameUser,nuevoComentario)
            // Limpiar el EditText después de enviar el comentario
            binding.editTextComentario.text.clear()
        }
    }

    private fun setUpRecyclerViewComentario() {
        comentarioAdapter = ComentarioAdapter(
            comentarios=_comentarios,
            onLongClick = {
                if(userInfo?.name == it.autor) {
                    Toast.makeText(context, " DELETING COMMENT... !!!", Toast.LENGTH_SHORT).show()
                    deleteComment(it)
                }else Toast.makeText(context, " CANNOT DELETE THIS COMMENT...", Toast.LENGTH_SHORT).show()
            }
        )
        with(binding) {
            layoutComentarios.layoutManager = LinearLayoutManager(context)
            layoutComentarios.adapter = comentarioAdapter
        }
        Log.d("ComentarioFragment", "setUpRecyclerView")
    }
    private fun deleteComment(comment: Comentario){
        lifecycleScope.launch {
            repository.deleteComment(comment)
            viewModel.getComments()
        }
    }
    private fun showBinding(shop: Article) {
        binding.tvNombreArticulo.text = shop.title
        binding.tvDescripcionArticulo.text = shop.description
        binding.tvPrecio.text = shop.price.toString()
        binding.swFavorito.isChecked = shop.isFavorite


        Glide.with(this)
            .load(shop.image)
            .placeholder(R.drawable.placeholder)
            .into(binding.shImage)
        binding.swFavorito.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                if (isChecked) {
                    viewModel.setFavorite(shop)
                    Toast.makeText(
                        context,
                        "Artículo añadido a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.setNoFavorite(shop)
                    Toast.makeText(
                        context,
                        "Artículo borrado de favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }


    private fun setUpListeners() {
        with(binding) {

            btComprar.setOnClickListener {
                lifecycleScope.launch {
                    navigateToRealizarCompra()

                }

            }
            btEnviarComentario.setOnClickListener {
                if (userInfo != null) {
                    enviarComentario(userInfo!!.name)
                }
            }
            btModificar.setOnClickListener{
                val intent = Intent(requireContext(), ModificarArticuloActivity::class.java)
                intent.putExtra("articulo",viewModel.shop)
                startActivity(intent)
                requireActivity().finish()
            }
            btBorrar.setOnClickListener {
                val intent = Intent(requireContext(), BorrarArticuloActivity::class.java)
                intent.putExtra("articulo",viewModel.shop)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun navigateToRealizarCompra(){
        val intent = Intent(activity, RealizarCompraActivity::class.java)
        intent.putExtra("shop", args.shop)
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConsultarPerfilFragment.
         */
        private const val USER_INFO = "USER_INFO"
        @JvmStatic
        fun newInstance(userInfo:User):ConsultarPerfilFragment{
            val fragment=ConsultarPerfilFragment()
            val args = Bundle()
            args.putSerializable(USER_INFO,userInfo)
            fragment.arguments=args
            return fragment
        }
    }
    /**
    private fun notifyValidArticle(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }*/
}