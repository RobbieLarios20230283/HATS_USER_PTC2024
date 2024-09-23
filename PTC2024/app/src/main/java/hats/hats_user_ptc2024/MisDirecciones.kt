package hats.hats_user_ptc2024
import Modelo.ClaseConexion
import Modelo.tbDirecciones
import Modelo.tbServicios
import RecyclerViewHelpers.Adaptador
import RecyclerViewHelpers.AdaptadorD
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

class MisDirecciones : Fragment() {
    private var AdaptadorD: AdaptadorD? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val root = inflater.inflate(R.layout.fragment_mis_direcciones, container, false)

        val rcvServiciosD = root.findViewById<RecyclerView>(R.id.rcvDireccionesC)
        rcvServiciosD.layoutManager = LinearLayoutManager(requireContext())

        fun MostrarDirecciones(): List<tbDirecciones> {

            val objConexion = ClaseConexion().CadenaConexion()
            val statement = objConexion?.createStatement()

            val ResultSet = statement?.executeQuery("select * from tbDirecciones")!!
            val listaServicios = mutableListOf<tbDirecciones>()

            while (ResultSet.next()) {
                val uuidDirecciones = ResultSet.getString("uuidDirecciones")
                val NombreDireccion = ResultSet.getString("NombreDireccion")
                val Ubicacion = ResultSet.getString("Ubicacion")
                val allDirection = tbDirecciones(uuidDirecciones, NombreDireccion, Ubicacion)
                listaServicios.add(allDirection)
            }
            return listaServicios
        }
        CoroutineScope(Dispatchers.IO).launch{
            val DireccionesDB = MostrarDirecciones()
            withContext(Dispatchers.Main){
                AdaptadorD = AdaptadorD(DireccionesDB)
                rcvServiciosD.adapter = AdaptadorD
            }
        }
        return root
    }
}