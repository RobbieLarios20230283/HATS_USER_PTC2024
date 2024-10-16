package hats.hats_user_ptc2024

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import hats.hats_user_ptc2024.categorias.VariableGlobal.miValor

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class infolegal : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_infolegal, container, false)
    }

    // Aquí es donde puedes acceder a las vistas
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encuentra el botón después de que la vista se haya creado
        val btnCerrarADNaa: Button = view.findViewById(R.id.btnVolverifno)

        // Configurar el listener para el botón
        btnCerrarADNaa.setOnClickListener {
            // Navegar hacia atrás en la pila de navegación
            findNavController().popBackStack()
        }

        val btn1: Button = view.findViewById(R.id.btnTTT1)
        val btn2: Button = view.findViewById(R.id.btnTTT2)
        val btn3: Button = view.findViewById(R.id.btnTTT3)

        btn1.setOnClickListener {
            findNavController().navigate(R.id.TCN_F)
        }
        btn2.setOnClickListener {
            findNavController().navigate(R.id.TCP_f)
        }
        btn3.setOnClickListener {
            findNavController().navigate(R.id.PLYS_f)
        }
    }

    companion object {
        /**
         * Usa este método para crear una nueva instancia del fragmento usando los parámetros proporcionados.
         *
         * @param param1 Parámetro 1.
         * @param param2 Parámetro 2.
         * @return Una nueva instancia del fragmento infolegal.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            infolegal().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
