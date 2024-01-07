package com.example.gc02.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gc02.database.BaseDatos
import com.example.gc02.databinding.FragmentConsultarPerfilBinding
import com.example.gc02.model.User
import com.example.gc02.model.Valuation
import kotlinx.coroutines.launch
/**
 * A simple [Fragment] subclass.
 * Use the [ConsultarPerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsultarPerfilFragment : Fragment() {
    private lateinit var db: BaseDatos

    private var _valoracion: List<Valuation> = emptyList()
    private val viewModel: ValoracionViewModel by viewModels { ValoracionViewModel.Factory }

    private var _binding: FragmentConsultarPerfilBinding? = null

    private val binding get() = _binding!!

    private lateinit var valoracionAdapter: ValoracionAdapter
    private var userInfo: User? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsultarPerfilBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun cargarInformacionDePerfil() {
        // Agregar lógica para cargar y mostrar la información del perfil del usuario
        with(binding) {
            tvConsultarPerfil.text = userInfo?.name
            tvCorreoPerfil.text = userInfo?.email
        }
    }

    private fun subscribeUi(adapter: ValoracionAdapter) {
        viewModel.valuations.observe(viewLifecycleOwner) {  valuations ->
            adapter.updateData(valuations)
        }
    }

    private fun setUpRecyclerViewValoracion() {
        valoracionAdapter = ValoracionAdapter(
            valoracion=_valoracion
        )
        with(binding) {
            layoutValoracion.layoutManager = LinearLayoutManager(context)
            layoutValoracion.adapter = valoracionAdapter
        }
        android.util.Log.d("ValoracionFragment", "setUpRecyclerView")
    }

    private fun dataLoad(valoraciones:List<Valuation>) {
        lifecycleScope.launch {
            binding.spinner.visibility = View.VISIBLE

            valoracionAdapter.updateData(valoraciones)
            binding.spinner.visibility = View.GONE

            Toast.makeText(
                context,
                "Valoraciones mostradas",
                Toast.LENGTH_SHORT
            ).show()
        }
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
        setUpRecyclerViewValoracion()
        // Configurar la carga de información del perfil (puedes obtener esta información desde tu base de datos u otro origen)
        cargarInformacionDePerfil()
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
        subscribeUi(valoracionAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
    companion object {

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