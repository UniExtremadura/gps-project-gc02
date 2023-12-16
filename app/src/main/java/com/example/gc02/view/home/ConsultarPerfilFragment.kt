package com.example.gc02.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.FragmentConsultarPerfilBinding
import com.example.gc02.model.Comentario
import com.example.gc02.model.User
import kotlinx.coroutines.launch
/**
 * A simple [Fragment] subclass.
 * Use the [ConsultarPerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsultarPerfilFragment : Fragment() {
    private val TAG = "ComentarioFragment"
    private lateinit var db: BaseDatos
    private var _comentarios: List<Comentario> = emptyList()
    private lateinit var listener: OnPerfilClickListener

    interface OnPerfilClickListener {
        fun onPerfilClick(user:User)
    }
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentConsultarPerfilBinding? = null

    private val binding get() = _binding!!
    private lateinit var comentarioAdapter: ComentarioAdapter
    private var userInfo: User? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsultarPerfilBinding.inflate(inflater, container, false)

        binding.btEnviarComentario.setOnClickListener {
            // Utiliza la información del usuario según sea necesario
            if (userInfo != null) {
                enviarComentario(userInfo!!.name)
            }
        }

        // Configurar el botón para enviar un nuevo comentario
        setUpRecyclerView()
        // Configurar la carga de información del perfil (puedes obtener esta información desde tu base de datos u otro origen)
        cargarInformacionDePerfil()

        return binding.root
    }

    private fun cargarInformacionDePerfil() {
        // Agregar lógica para cargar y mostrar la información del perfil del usuario
        with(binding) {
            tvConsultarPerfil.text = userInfo?.name
            tvCorreoPerfil.text = userInfo?.email
        }
    }


    private fun enviarComentario(nameUser: String) {
        // Agregar lógica para enviar un nuevo comentario
        val nuevoComentario = binding.editTextComentario.text.toString()

        // Agregar lógica para almacenar el comentario en la base de datos
        lifecycleScope.launch {
            val comment = Comentario(
                null,
                nameUser,
                nuevoComentario
            )
            val id = db?.comentarioDao()?.insert(comment)
        }
        // Después de enviar el comentario, actualizar la lista de comentarios llamando a cargarComentarios()
        setUpRecyclerView()
        // Limpiar el EditText después de enviar el comentario
        binding.editTextComentario.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = BaseDatos.getInstance(requireContext())!!
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

    }
    private fun setUpRecyclerView() {
        comentarioAdapter = ComentarioAdapter(
            comentarios=_comentarios
        )
        with(binding) {
            layoutComentarios.layoutManager = LinearLayoutManager(context)
            layoutComentarios.adapter = comentarioAdapter
        }
        lifecycleScope.launch {
            val comentariosDB = db.comentarioDao().obtenerComentarios()
            comentarioAdapter.updateData(comentariosDB)
        }
        Toast.makeText(
            context,
            "Comentarios mostrados",
            Toast.LENGTH_SHORT
        ).show()
        android.util.Log.d("ComentarioFragment", "setUpRecyclerView")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
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
}