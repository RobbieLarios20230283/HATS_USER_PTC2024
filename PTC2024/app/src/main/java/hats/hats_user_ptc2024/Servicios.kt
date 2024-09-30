package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import Modelo.tbServicios
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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Servicios : Fragment() {
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
        return inflater.inflate(R.layout.fragment_servicios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rcvServicios: RecyclerView = view.findViewById(R.id.rcvMisServicios)
        rcvServicios.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val serviciosDB = MisServicios()
            withContext(Dispatchers.Main) {
                val adapterS = AdaptadorServicio(serviciosDB)
                rcvServicios.adapter = adapterS
            }
        }
    }

    private fun MisServicios(): List<tbServicios> {
        val objConexionS = ClaseConexion().CadenaConexion()
        val statementS = objConexionS?.createStatement()
        val resultSetS = statementS?.executeQuery("SELECT * FROM tbservicios")

        val listaServicios = mutableListOf<tbServicios>()

        while (resultSetS?.next() == true) {
            val uuidServicios = resultSetS.getString("uuidServicios")
            val uuidCatalogo = resultSetS.getString("uuidCatalogo")
            val nombreServicios = resultSetS.getString("NombreServicios")
            val descripcionServicios = resultSetS.getString("Descripcion")
            val valoresJuntos = tbServicios(uuidServicios, uuidCatalogo, nombreServicios,descripcionServicios)
            listaServicios.add(valoresJuntos)
        }

        return listaServicios
    }

    companion object {
        /**
         * Usa este método para crear una nueva instancia del fragmento
         * usando los parámetros proporcionados.
         *
         * @param param1 Parámetro 1.
         * @param param2 Parámetro 2.
         * @return Una nueva instancia del fragmento Servicios.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Servicios().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }


            }
    }
}
