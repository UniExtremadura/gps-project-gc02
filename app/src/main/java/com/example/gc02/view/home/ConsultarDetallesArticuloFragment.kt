package com.example.gc02.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.gc02.R
import com.example.gc02.api.APIError
import com.example.gc02.api.getNetworkService
import com.example.gc02.data.Repository
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.FragmentConsultarArticuloBinding
import com.example.gc02.model.Article
import com.example.gc02.model.Comentario
import com.example.gc02.model.User
import com.example.gc02.view.RealizarCompraActivity
import kotlinx.coroutines.launch
class ConsultarDetallesArticuloFragment : Fragment() {
    private var userInfo: User? = null
    private lateinit var db: BaseDatos
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var comentarioAdapter: ComentarioAdapter
    private var _comentarios: List<Comentario> = emptyList()
    private var _binding: FragmentConsultarArticuloBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: Repository

    private val args: ConsultarDetallesArticuloFragmentArgs by navArgs()

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = BaseDatos.getInstance(requireContext())!!
        repository = Repository.getInstance(db.userDao(), db.articleDao(), getNetworkService())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentConsultarArticuloBinding.inflate(inflater, container, false)
        binding.btEnviarComentario.setOnClickListener {
            // Utiliza la información del usuario según sea necesario
            if (userInfo != null) {
                enviarComentario(userInfo!!.name)
            }
        }

        // Configurar el botón para enviar un nuevo comentario
        setUpRecyclerViewComentario()
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val shop = args.shop

        lifecycleScope.launch{
            try{
                // val _shop = fetchShopDetail(shop.articleId).toShop()
                //_shop.isFavorite = shop.isFavorite
                showBinding(shop)
            } catch (error: APIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }

        setUpListeners()


    }
    private fun enviarComentario(nameUser: String) {
        // Agregar lógica para enviar un nuevo comentario
        val nuevoComentario = binding.editTextComentario.text.toString()
    Log.d("Comentario","Agregar comentario a articulo : ${args.shop.articleId}")
        // Agregar lógica para almacenar el comentario en la base de datos
        lifecycleScope.launch {
            val comment = Comentario(
                null,
                nameUser,
                nuevoComentario,
                args.shop.articleId,
                args.shop.userId
            )
            val id = db?.comentarioDao()?.insert(comment)
        }
        // Después de enviar el comentario, actualizar la lista de comentarios llamando a cargarComentarios()
        setUpRecyclerViewComentario()
        // Limpiar el EditText después de enviar el comentario
        binding.editTextComentario.text.clear()
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
        lifecycleScope.launch {
            val shop = args.shop.articleId
            val comentariosDB = db.comentarioDao().obtenerComentariosByArticleAndUser(shop!!, args.shop.userId!!)
            comentarioAdapter.updateData(comentariosDB)
        }
        Toast.makeText(
            context,
            "Comentarios mostrados",
            Toast.LENGTH_SHORT
        ).show()
        Log.d("ComentarioFragment", "setUpRecyclerView")
    }
    private fun deleteComment(comment: Comentario){
        lifecycleScope.launch {
            db.comentarioDao().delete(comment)
            setUpRecyclerViewComentario()
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
                    shop.isFavorite = true
                    repository.insertShopFavorite(shop, userInfo?.userId!!)
                    agregarAFavoritos(shop)
                    Toast.makeText(
                        context,
                        "Artículo añadido a favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    shop.isFavorite = false
                    repository.deleteShopFavorite(shop, userInfo?.userId!!)
                    eliminarDeFavoritos(shop)
                    Toast.makeText(
                        context,
                        "Artículo borrado de favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun agregarAFavoritos(articulo: Article) {
        sharedViewModel.agregarAFavoritos(articulo)
    }

    private fun eliminarDeFavoritos(articulo: Article) {
        sharedViewModel.eliminarDeFavoritos(articulo)
        lifecycleScope.launch {
            sharedViewModel.listaFavoritos.value = repository.getUserWithShopsFavorites(userInfo?.userId!!).shops
        }
    }


    private fun setUpListeners() {
        val shop = args.shop
        with(binding) {

            btComprar.setOnClickListener {
                lifecycleScope.launch {
                    // shop.articleId?.let { it1 -> deleteProduct(it1) }
                    //     shop.articleId?.let { it1 -> updateProduct(it1) }
                    navigateToRealizarCompra()

                }

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