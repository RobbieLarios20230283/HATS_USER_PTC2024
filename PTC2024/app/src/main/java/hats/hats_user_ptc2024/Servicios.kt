package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import Modelo.tbServicios
import RecyclerViewHelpers.Adaptador
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


class Servicios : Fragment() {
    private var miAdaptador: Adaptador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_servicios, container, false)


        val rcvSolicitudes = root.findViewById<RecyclerView>(R.id.rcvServicios)
        rcvSolicitudes.layoutManager = LinearLayoutManager(requireContext())


        suspend fun MostrarDatos(): List<tbServicios> {
            val objConexion = ClaseConexion().CadenaConexion()

            val statement = objConexion?.createStatement()
            val ResultSet = statement?.executeQuery("select * from tbservicios")!!

            val listaServicios = mutableListOf<tbServicios>()

            while (ResultSet.next()) {
                val uuidServicios = ResultSet.getString("uuidServicios")
                val uuidCatalogo = ResultSet.getString("uuidCatalogo")
                val nombreServicios = ResultSet.getString("NombreServicios")

                val allValues = tbServicios(uuidServicios, uuidCatalogo, nombreServicios)
                listaServicios.add(allValues)
            }
            return listaServicios
        }

        CoroutineScope(Dispatchers.IO).launch{
            val ServiciosDB = MostrarDatos()
            withContext(Dispatchers.Main){
                miAdaptador = Adaptador(ServiciosDB)
                rcvSolicitudes.adapter = miAdaptador
            }
        }
        return root
    }
}