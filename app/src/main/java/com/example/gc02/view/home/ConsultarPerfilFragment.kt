package com.example.gc02.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.data.dummyComentarios
import com.example.gc02.databinding.FragmentConsultarPerfilBinding
import com.example.gc02.model.Comentario

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConsultarPerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsultarPerfilFragment : Fragment() {
    private val TAG = "ComentarioFragment"

    private var _comentarios: List<Comentario> = emptyList()
    private lateinit var listener: OnComentarioClickListener
    interface OnComentarioClickListener {
        fun onComentarioClick(comentario: Comentario)
    }
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentConsultarPerfilBinding? = null

    private val binding get() = _binding!!
    private lateinit var comentarioAdapter: ComentarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsultarPerfilBinding.inflate(inflater, container, false)


        // Configurar la carga de información del perfil (puedes obtener esta información desde tu base de datos u otro origen)
        cargarInformacionDePerfil()

        // Configurar el botón para enviar un nuevo comentario
        binding.btEnviarComentario.setOnClickListener {
            enviarComentario()
        }

        return binding.root
    }

    private fun cargarInformacionDePerfil() {
        // Agregar lógica para cargar y mostrar la información del perfil del usuario
        // por ejemplo: textViewNombre.text = "Nombre del usuario obtenido de base de datos"
    }


    private fun enviarComentario() {
        // Agregar lógica para enviar un nuevo comentario
        val nuevoComentario = binding.editTextComentario.text.toString()

        // Agregar lógica para almacenar el comentario en la base de datos

        // Después de enviar el comentario, actualizar la lista de comentarios llamando a cargarComentarios()

        // Limpiar el EditText después de enviar el comentario
        binding.editTextComentario.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }
    private fun setUpRecyclerView() {
        comentarioAdapter = ComentarioAdapter(comentarios = dummyComentarios)
        with(binding) {
            layoutComentarios.layoutManager = LinearLayoutManager(context)
            layoutComentarios.adapter = comentarioAdapter
        }
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsultarPerfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}