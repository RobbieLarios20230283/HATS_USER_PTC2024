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
