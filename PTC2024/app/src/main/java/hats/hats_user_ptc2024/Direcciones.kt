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
import hats.hats_user_ptc2024.categorias.VariableGlobal.miValor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Direcciones : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCerrarADN: Button = view.findViewById(R.id.btnCerrarADN)

        btnCerrarADN.setOnClickListener {
            findNavController().popBackStack()
        }

        val btnMisDireccionesNav: Button = view.findViewById(R.id.btnVerMisDirecciones)
        val txtNombreDireccion: EditText = view.findViewById(R.id.txtNombreDireccion)
        val txtDireccion: EditText = view.findViewById(R.id.txtMiDirrecion)

        val btnAgregarDireccion: Button = view.findViewById(R.id.btnGuardarDireccion)

        btnAgregarDireccion.setOnClickListener {
            try {
                val nombreDireccion = txtNombreDireccion.text.toString().trim()
                val direccion = txtDireccion.text.toString().trim()

                // Validar que no estén vacíos
                if (nombreDireccion.isEmpty() || direccion.isEmpty()) {
                    throw IllegalArgumentException("Todos los campos son obligatorios.")
                }

                // Validar que nombre tenga máximo 50 caracteres y dirección 200
                if (nombreDireccion.length > 50) {
                    throw IllegalArgumentException("El nombre de la dirección no puede tener más de 50 caracteres.")
                }

                if (direccion.length > 200) {
                    throw IllegalArgumentException("La dirección no puede tener más de 200 caracteres.")
                }

                CoroutineScope(Dispatchers.IO).launch {

                    val objConexion = ClaseConexion().CadenaConexion()

                    val agregarMiDireccion = objConexion?.prepareStatement(
                        "INSERT INTO tbdirecciones(uuidDirecciones, NombreDireccion, Ubicacion) VALUES (?, ?, ?)")!!

                    agregarMiDireccion.setString(1, UUID.randomUUID().toString())
                    agregarMiDireccion.setString(2, nombreDireccion)
                    agregarMiDireccion.setString(3, direccion)

                    agregarMiDireccion.executeUpdate()
                }

                Toast.makeText(requireContext(), "Dirección agregada exitosamente.", Toast.LENGTH_SHORT).show()

            } catch (e: IllegalArgumentException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al guardar la dirección.", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón de navegación
        btnMisDireccionesNav.setOnClickListener {
            findNavController().navigate(R.id.misDirecciones)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_direcciones, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Direcciones().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
