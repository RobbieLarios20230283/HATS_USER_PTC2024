package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.EditText
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Perfil : Fragment() {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        val txtUUIDP: EditText = view.findViewById(R.id.txtUUID_P)
        val txtNombreP: EditText = view.findViewById(R.id.txtNombre_p)
        val txtApellidosP: EditText = view.findViewById(R.id.txtApellidos_p)
        val txtTelefonoP: EditText = view.findViewById(R.id.txtTelefono_p)
        val txtCorreoP: EditText = view.findViewById(R.id.txtCorreo_p)
        val txtDuiP: EditText = view.findViewById(R.id.txtDui_p)
        val txtDireccionP: EditText = view.findViewById(R.id.txtDireccionP)
        val txtfechaP: EditText = view.findViewById(R.id.txtfecha_p)

        val btnActualizarP: Button = view.findViewById(R.id.btnActualizar)

        btnActualizarP.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val nombre = txtNombreP.text.toString()
                val apellidos = txtApellidosP.text.toString()
                val telefono = txtTelefonoP.text.toString().toIntOrNull()
                val dui = txtDuiP.text.toString()
                val direccion = txtDireccionP.text.toString()
                val fecha = txtfechaP.text.toString()
                val uuid = txtUUIDP.text.toString()

                if (nombre.isEmpty() || apellidos.isEmpty() || telefono == null || dui.isEmpty() || direccion.isEmpty() || fecha.isEmpty() || uuid.isEmpty()) {
                    Toast.makeText(context, "Por favor, Completa todos los campos", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                try {
                    withContext(Dispatchers.IO) {
                        val objConexion = ClaseConexion().CadenaConexion()
                        val sql = "UPDATE Empleador SET nombreEmpleador = ?, apellidoEmpleador = ?, Telefono = ?, DuiEmpleador = ?, Direccion = ?, fechanac = ? WHERE uuidEmpleador = ?"
                        val preparedStatement = objConexion?.prepareStatement(sql)

                        preparedStatement?.apply {
                            setString(1, nombre)
                            setString(2, apellidos)
                            setInt(3, telefono)
                            setString(4, dui)
                            setString(5, direccion)
                            setString(6, fecha)
                            setString(7, uuid)
                            executeUpdate()
                        }

                        preparedStatement?.close()
                        objConexion?.close()
                    }
                    Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al actualizar datos: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Perfil().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

