package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostLog : AppCompatActivity() {
    private lateinit var uuidEmpleador: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post_log)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener elementos de la vista
        val nombreEmpleador = findViewById<EditText>(R.id.txtNombre_p)
        val apellidoEmpleador = findViewById<EditText>(R.id.txtApellidos_p)
        val telefono = findViewById<EditText>(R.id.txtTelefono_p)
        val duiEmpleador = findViewById<EditText>(R.id.txtDui_p)
        val direccion = findViewById<EditText>(R.id.txtDireccionP)
        val fechanac = findViewById<EditText>(R.id.txtfecha_p)
        val btnGuardar = findViewById<Button>(R.id.btnActualizar)
        val btnVolver = findViewById<ImageView>(R.id.backlogin)

        // Inicializar el botón de "Volver" como deshabilitado
        btnVolver.isEnabled = false

        // Obtener el UUID del Intent
        uuidEmpleador = intent.getStringExtra("uuidEmpleador") ?: ""

        if (uuidEmpleador.isEmpty()) {
            Toast.makeText(this, "UUID del empleador no disponible", Toast.LENGTH_SHORT).show()
            return
        }

        btnGuardar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().CadenaConexion()

                // Sentencia SQL para actualizar los datos
                val updateQuery = """
                    UPDATE Empleador
                    SET nombreEmpleador = ?, apellidoEmpleador = ?, Telefono = ?, 
                        DuiEmpleador = ?, Direccion = ?, fechanac = ?
                    WHERE uuidEmpleador = ?
                """.trimIndent()

                val actualizarDatos = objConexion?.prepareStatement(updateQuery)!!

                // Establecer los valores de los campos
                actualizarDatos.setString(1, nombreEmpleador.text.toString())
                actualizarDatos.setString(2, apellidoEmpleador.text.toString())
                actualizarDatos.setString(3, telefono.text.toString())
                actualizarDatos.setString(4, duiEmpleador.text.toString())
                actualizarDatos.setString(5, direccion.text.toString())
                actualizarDatos.setString(6, fechanac.text.toString())
                actualizarDatos.setString(7, uuidEmpleador)

                val filasActualizadas = actualizarDatos.executeUpdate()

                withContext(Dispatchers.Main) {
                    if (filasActualizadas > 0) {
                        Toast.makeText(this@PostLog, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                        // Habilitar el botón de "Volver" después de la actualización
                        btnVolver.isEnabled = true
                    } else {
                        Toast.makeText(this@PostLog, "No se pudo actualizar los datos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Botón de "Volver" que lleva a la pantalla de login
        btnVolver.setOnClickListener {
            val pantallaLogin = Intent(this, activity_login::class.java)
            startActivity(pantallaLogin)
        }
    }
}
