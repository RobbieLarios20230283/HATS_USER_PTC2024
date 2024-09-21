package hats.hats_user_ptc2024

import RecyclerViewHelpers.Adaptador
import ViewModel.SharedViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.databinding.FragmentServicioSelectBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Servicio_Select : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    // Variable para el ViewModel
    private lateinit var sharedViewModel: SharedViewModel

    // Variable para el ViewBinding
    private var _binding: FragmentServicioSelectBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtén los argumentos si existen
        arguments?.let {
            param1 = it.getString(ARG_PARAM1) // Extraer param1 del bundle
            param2 = it.getString(ARG_PARAM2) // Extraer param2 del bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicioSelectBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val rcvServicios: RecyclerView = binding.rcvSolicitudes
        rcvServicios.layoutManager = LinearLayoutManager(requireContext())

        sharedViewModel.servicios.observe(viewLifecycleOwner) { datosServicios ->
            val adaptador = Adaptador(datosServicios)
            rcvServicios.adapter = adaptador
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Servicio_Select().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
