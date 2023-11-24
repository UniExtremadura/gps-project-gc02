package com.example.gc02.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gc02.databinding.FragmentConsultarPerfilBinding

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

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentConsultarPerfilBinding

    // Aquí puedes agregar cualquier otro código necesario, como variables de adaptador para la lista de comentarios, etc.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inicializar las vistas
        binding = FragmentConsultarPerfilBinding.inflate(inflater, container, false)
       // binding.lifecycleOwner = viewLifecycleOwner
      //  binding.viewModel = ConsultarPerfilViewModel()

        // Configurar la carga de información del perfil (puedes obtener esta información desde tu base de datos u otro origen)
        cargarInformacionDePerfil()

        // Configurar la carga de comentarios (puedes obtener esta información desde tu base de datos u otro origen)
        cargarComentarios()

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

    private fun cargarComentarios() {
        // Agregar lógica para cargar y mostrar los comentarios existentes
        // por ejemplo: configurar un adaptador para la listViewComentarios
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