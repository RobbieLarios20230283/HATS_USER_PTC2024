package hats.hats_user_ptc2024.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hats.hats_user_ptc2024.R
import hats.hats_user_ptc2024.categorias.VariableGlobal.miValor
import hats.hats_user_ptc2024.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(


        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnPerfil: Button = view.findViewById(R.id.btnNPerfil)
        val btnDirecciones: Button = view.findViewById(R.id.btnNPerfil)
        val btnConf: Button = view.findViewById(R.id.btnNConfiguraciones)

        btnPerfil.setOnClickListener {
            findNavController().navigate(R.id.miPerfil)
        }

        btnDirecciones.setOnClickListener {
            findNavController().navigate(R.id.direcciones)
        }
        btnConf.setOnClickListener {
            findNavController().navigate(R.id.configuracion)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}