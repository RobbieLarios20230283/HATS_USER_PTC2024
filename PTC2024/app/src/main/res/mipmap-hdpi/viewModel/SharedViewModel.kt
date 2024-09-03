package `mipmap-hdpi`.viewModel

import Modelo.ClaseConexion
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import Modelo.tbSolicitudes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel : ViewModel() {
    private val _solicitudes = MutableLiveData<List<tbSolicitudes>>()
    val solicitudes: LiveData<List<tbSolicitudes>> get() = _solicitudes

    init {
        loadSolicitudes()
    }

    private fun loadSolicitudes() {
        CoroutineScope(Dispatchers.IO).launch {
            val listaSolicitudes = mutableListOf<tbSolicitudes>()
            val objConnection = ClaseConexion().CadenaConexion()

            if (objConnection != null) {
                val query = objConnection.createStatement()
                val result = query.executeQuery("select * from Solicitudes")

                while (result.next()) {
                    val uuidSolicitud = result.getString("uuidSolicitud")
                    val uuidEmpleador = result.getString("uuidEmpleador")
                    val uuidServicio = result.getString("uuidServicio")
                    val Precio = result.getString("Precio")
                    val Descripcion = result.getString("Descripcion")
                    val longitud = result.getString("longitud")
                    val latitud = result.getString("latitud")
                    val fecha = result.getString("fecha")

                    val allValues = tbSolicitudes(uuidSolicitud, uuidEmpleador, uuidServicio, Precio, Descripcion, longitud, latitud, fecha)
                    listaSolicitudes.add(allValues)
                }

                result.close()
                query.close()
                objConnection.close()
            }

            withContext(Dispatchers.Main) {
                _solicitudes.value = listaSolicitudes
            }
        }
    }

    fun addSolicitud(solicitudes: tbSolicitudes) {
        val currentList = _solicitudes.value?.toMutableList() ?: mutableListOf()
        currentList.add(solicitudes)
        _solicitudes.value = currentList
    }
}