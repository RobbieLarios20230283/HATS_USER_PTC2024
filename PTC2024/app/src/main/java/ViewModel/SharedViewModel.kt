package ViewModel

import Modelo.ClaseConexion
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import Modelo.tbServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel : ViewModel() {

    private val _Servicios = MutableLiveData<List<tbServicios>>()
    val Servicios: LiveData<List<tbServicios>> get() = _Servicios

    init {
        loadServicio()
    }

    private fun loadServicio() {
        CoroutineScope(Dispatchers.IO).launch {

            val listaServicios = mutableListOf<tbServicios>()
            val objConnection = ClaseConexion().CadenaConexion()

            if (objConnection != null) {

                val query = objConnection.createStatement()
                val result = query.executeQuery("select * from tbservicios")

                while (result.next()) {
                    val uuidServicios = result.getString("uuidServicios")
                    val uuidCatalogo = result.getString("uuidCatalogo")
                    val nombreServicios = result.getString("NombreServicios")

                    val allValues = tbServicios(uuidServicios, uuidCatalogo, nombreServicios)
                    listaServicios.add(allValues)
                }
                result.close()
                query.close()
                objConnection.close()
            }

            withContext(Dispatchers.Main) {
                _Servicios.value = listaServicios
            }
        }

    }
}
