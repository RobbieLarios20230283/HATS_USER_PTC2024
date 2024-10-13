package hats.hats_user_ptc2024

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

class DetalleServicio : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    companion object {
        fun newInstance(
            NombreServicios: String,
            Descripcion: String
        ): Detalles {
            val fragment = Detalles()
            val args = Bundle()
            args.putString("NombreServicios", NombreServicios)
            args.putString("Descripcion", Descripcion)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_detalle_servicio, container, false)

        val Ic_RegresarS = root.findViewById<Button>(R.id.btnRegresarRCV)

        val NombreServicios_O = arguments?.getString("NombreServicios")
        val Descripcion_O = arguments?.getString("Descripcion")


        val LBL_NombreServicios = root.findViewById<TextView>(R.id.lblNombreD_S)
        val LBL_Descripcion = root.findViewById<TextView>(R.id.lblDireccion_D_S)

        LBL_NombreServicios.text = NombreServicios_O
        LBL_Descripcion.text = Descripcion_O


        Ic_RegresarS.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }
}