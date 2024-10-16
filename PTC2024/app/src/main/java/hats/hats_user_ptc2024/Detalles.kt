package hats.hats_user_ptc2024

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Detalles.newInstance] factory method to
 * create an instance of this fragment.
 */

class Detalles : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    companion object {
        lateinit var direccionSeleccionada : String

        fun newInstance(
            NombreDireccion: String,
            Direccion: String
        ): Detalles {
            val fragment = Detalles()
            val args = Bundle()
            args.putString("NombreDireccion", NombreDireccion)
            args.putString("Ubicacion", Direccion)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_detalles, container, false)

        val btnSeleccionarDireccion = root.findViewById<Button>(R.id.btnSeleccionarDireccion)
        val Ic_Regresar = root.findViewById<Button>(R.id.btnRegresarDETA)

        val NombreDireccion_O = arguments?.getString("NombreDireccion")
        val Direcciones_O = arguments?.getString("Ubicacion")


        val LBL_NombreDireccion = root.findViewById<TextView>(R.id.lblNombreD_D)
        val LBL_Direccion = root.findViewById<TextView>(R.id.lblDireccion_D)

        LBL_NombreDireccion.text = NombreDireccion_O
        LBL_Direccion.text = Direcciones_O

        btnSeleccionarDireccion.setOnClickListener {
            if (NombreDireccion_O != null) {
                direccionSeleccionada = NombreDireccion_O
                Toast.makeText(requireContext(), "esta es la DIRECCION QUE QUEDA $direccionSeleccionada", Toast.LENGTH_LONG).show()
                println("esta es la DIRECCION $direccionSeleccionada")
            }
        }


        Ic_Regresar.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }
}