package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import hats.hats_user_ptc2024.activity_login.VariableGlobal.CorreoGlobal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarPerfil : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_editar_perfil, container, false)

        val txtNombreNuevo = root.findViewById<EditText>(R.id.txtNuevoNombre)
        val txtApellidoNuevo = root.findViewById<EditText>(R.id.txtNuevoApellido)
        val txtNumeroNuevo = root.findViewById<EditText>(R.id.txtNuevoNumero)
        val btnActuP = root.findViewById<Button>(R.id.btnActualizarEP)

        btnActuP.setOnClickListener {
            val correoG = CorreoGlobal
            val NuevoNombre = txtNombreNuevo.text.toString().trim()
            val NuevoApellido = txtApellidoNuevo.text.toString().trim()
            val NuevoNumero = txtNumeroNuevo.text.toString().trim()
            
            if (NuevoNombre.isEmpty() || NuevoNombre.length > 50) {
                Toast.makeText(requireContext(), "El nombre no puede estar vacío y debe tener máximo 50 caracteres.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (NuevoApellido.isEmpty() || NuevoApellido.length > 50) {
                Toast.makeText(requireContext(), "El apellido no puede estar vacío y debe tener máximo 50 caracteres.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (NuevoNumero.isEmpty() || !NuevoNumero.matches(Regex("\\d{8}"))) {
                Toast.makeText(requireContext(), "El número debe tener 8 dígitos y solo contener números.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Iniciar la actualización si pasa las validaciones
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val objConexion = ClaseConexion().CadenaConexion()
                    val ActualizarEP = objConexion?.prepareStatement(
                        "UPDATE Empleador SET nombreEmpleador = ?, apellidoEmpleador = ?, Telefono = ? WHERE CorreoUS = '$correoG'"
                    )

                    if (ActualizarEP != null) {
                        ActualizarEP.setString(1, NuevoNombre)
                        ActualizarEP.setString(2, NuevoApellido)
                        ActualizarEP.setString(3, NuevoNumero)

                        val filasAfectadas = ActualizarEP.executeUpdate()

                        withContext(Dispatchers.Main) {
                            if (filasAfectadas > 0) {
                                Toast.makeText(requireContext(), "Actualizado exitosamente.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "No se encontró el registro para actualizar.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        // Liberamos el recurso del PreparedStatement
                        ActualizarEP.close()
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Error de conexión.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al actualizar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCerrarEMP: Button = view.findViewById(R.id.btn_CerrarEMP)

        btnCerrarEMP.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
