package ViewModel

import Modelo.ClaseConexion
import Modelo.tbServicios
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel : ViewModel() {
    private val _Servicios = MutableLiveData<List<tbServicios>>()
    val servicios: LiveData<List<tbServicios>> get() = _Servicios

    init {
        loadServicio()
    }

    private fun loadServicio() {
        viewModelScope.launch(Dispatchers.IO) {
            val listaServicios = mutableListOf<tbServicios>()
            val objConnection = ClaseConexion().CadenaConexion()

            try {
                if (objConnection != null) {
                    val query = objConnection.createStatement()
                    val result = query.executeQuery("select * from Servicios")

                    while (result.next()) {
                        val uuidSolicitud = result.getString("uuidSolicitud")
                        val uuidEmpleador = result.getString("uuidEmpleador")
                        val uuidServicios = result.getString("uuidServicios")
                        val idServicio = result.getInt("idServicio")
                        val Precio = result.getFloat("Precio")
                        val Descripcion = result.getString("Descripcion")
                        val longitud = result.getDouble("longitud")
                        val latitud = result.getDouble("latitud")
                        val Fecha = result.getDate("Fecha").toString()

                        val allValues = tbServicios(
                            uuidSolicitud,uuidEmpleador,uuidServicios,idServicio,Precio,Descripcion,longitud,latitud,Fecha
                        )
                   listaServicios.add(allValues)
                    }

                    result.close()
                    query.close()
                    objConnection.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            withContext(Dispatchers.Main) {
                _Servicios.value = listaServicios
            }
        }
    }

    fun addServicio(servicios: tbServicios) {
        val currentList = _Servicios.value?.toMutableList() ?: mutableListOf()
        currentList.add(servicios)
        _Servicios.value = currentList
    }
}
