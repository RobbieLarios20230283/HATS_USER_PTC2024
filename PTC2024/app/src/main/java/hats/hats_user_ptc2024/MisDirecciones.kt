package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import Modelo.tbDirecciones
import RecyclerViewHelpers.AdaptadorDirecciones
import RecyclerViewHelpers.AdaptadorServicio
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [infolegal.newInstance] factory method to
 * create an instance of this fragment.
 */
class MisDirecciones : Fragment() {
    // TODO: Rename and change types of parameters
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
        return inflater.inflate(R.layout.fragment_mis_direcciones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rcvDirecciones: RecyclerView = view.findViewById(R.id.rcvDireccionesC)
        rcvDirecciones.layoutManager = LinearLayoutManager(requireContext())

        // Cargar las direcciones en una corrutina en el hilo de IO
        CoroutineScope(Dispatchers.IO).launch {
            val direccionesDB = MisDirecciones()  // Aquí obtenemos la lista de direcciones desde la BD

            // Con esto movemos el resultado al hilo principal
            withContext(Dispatchers.Main) {
                // Usa el adaptador correcto para las direcciones
                val adapterD = AdaptadorDirecciones(direccionesDB)
                rcvDirecciones.adapter = adapterD  // Asignamos el adaptador a la vista
            }
        }
    }

    // Esta función obtiene las direcciones desde la base de datos
    private fun MisDirecciones(): List<tbDirecciones> {
        val objConexionD = ClaseConexion().CadenaConexion()

        // Verificamos si la conexión no es nula
        val statementD = objConexionD?.createStatement()
        val resultSetD = statementD?.executeQuery("SELECT * FROM tbdirecciones")

        val listaDirecciones = mutableListOf<tbDirecciones>()

        // Solo procedemos si resultSetD no es nulo
        while (resultSetD?.next() == true) {
            val uuidDirecciones = resultSetD.getString("uuidDirecciones")
            val NombreDireccion = resultSetD.getString("NombreDireccion")
            val Ubicacion = resultSetD.getString("Ubicacion")

            val valoresJuntosD = tbDirecciones(uuidDirecciones, NombreDireccion, Ubicacion)
            listaDirecciones.add(valoresJuntosD)
        }

        // Cerramos recursos
        resultSetD?.close()
        statementD?.close()
        objConexionD?.close()

        return listaDirecciones
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment infolegal.
         */
        // TODO: Rename and change types and number of parameters
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