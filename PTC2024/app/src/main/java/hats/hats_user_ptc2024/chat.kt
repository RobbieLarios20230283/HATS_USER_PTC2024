package hats.hats_user_ptc2024

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class chat : AppCompatActivity() {

    private lateinit var btnEnviarCorreo: Button
    private lateinit var correoTrabajador: String // Variable para almacenar el correo del trabajador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Recuperar el correo del trabajador desde el Intent
        correoTrabajador = intent.getStringExtra("correoTrabajador") ?: ""

        // Referencias a los EditText
        val etNombreSolicitante = findViewById<EditText>(R.id.etNombreSolicitante)
        val etFechaHora = findViewById<EditText>(R.id.etFechaHora)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)

        // Botón para enviar el correo
        btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo)
        btnEnviarCorreo.setOnClickListener {
            val nombreSolicitante = etNombreSolicitante.text.toString()
            val fechaHora = etFechaHora.text.toString()
            val precio = etPrecio.text.toString()
            val descripcion = etDescripcion.text.toString()

            if (nombreSolicitante.isNotEmpty() && fechaHora.isNotEmpty() &&
                precio.isNotEmpty() && descripcion.isNotEmpty()) {

                // Deshabilitar el botón para evitar múltiples clics
                btnEnviarCorreo.isEnabled = false

                // Crear el mensaje del correo
                val mensajeCorreo = """
                    Nombre del solicitante: $nombreSolicitante
                    Fecha y hora: $fechaHora
                    Precio: $precio
                    Descripción: $descripcion
                """.trimIndent()

                // Enviar el correo usando coroutines
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Usa el correo del trabajador en lugar de un correo fijo
                        enviarCorreo(correoTrabajador, "Solicitud de Servicio", mensajeCorreo)
                        runOnUiThread {
                            Toast.makeText(this@chat, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@chat, "Error al enviar el correo", Toast.LENGTH_SHORT).show()
                            // Rehabilitar el botón en caso de error
                            btnEnviarCorreo.isEnabled = true
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
