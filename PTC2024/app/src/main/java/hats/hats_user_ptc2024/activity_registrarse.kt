package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.text.InputType
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
import java.security.MessageDigest
import java.util.UUID

class activity_registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener elementos de la vista
        val imgAtrasregistrarse = findViewById<ImageView>(R.id.imgbacklog)
        val txtCorreoRegistro = findViewById<EditText>(R.id.txtcorreoregis)
        val txtPasswordRegistro = findViewById<EditText>(R.id.txtcontraregis)
        val txtConfirmarPassword = findViewById<EditText>(R.id.txtconfrpass)
        val imgVerPassword = findViewById<ImageView>(R.id.imgverpass)
        val imgVerConfirmacionPassword = findViewById<ImageView>(R.id.imgverpass1)
        val btnCrearCuenta = findViewById<Button>(R.id.btnregistrarse)
        val btnRegresarLogin = findViewById<Button>(R.id.btnvolverlog)

        // Función para encriptar la contraseña
        fun hashSHA256(contraseniaEncriptada: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(contraseniaEncriptada.toByteArray())
            return bytes.joinToString("") { String.format("%02x", it) }
        }

        // Botón para crear la cuenta
        btnCrearCuenta.setOnClickListener {
            val correo = txtCorreoRegistro.text.toString()
            val contrasena = txtPasswordRegistro.text.toString()
            val confirmarContrasena = txtConfirmarPassword.text.toString()
            var validacionCam = false

            if (correo.isEmpty()) {
                txtCorreoRegistro.error = "El correo es obligatorio"
                validacionCam = true
            } else {
                txtCorreoRegistro.error = null
            }
            if (contrasena.isEmpty()) {
                txtPasswordRegistro.error = "La contraseña es obligatoria"
                validacionCam = true
            } else {
                txtPasswordRegistro.error = null
            }
            if (confirmarContrasena.isEmpty()) {
                txtConfirmarPassword.error = "La confirmación de la contraseña es obligatoria"
                validacionCam = true
            } else {
                txtConfirmarPassword.error = null
            }
            if (!correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.][a-z]+"))) {
                txtCorreoRegistro.error = "El correo no tiene un formato válido"
                validacionCam = true
            } else {
                txtCorreoRegistro.error = null
            }
            if (contrasena.length <= 7) {
                txtPasswordRegistro.error = "La contraseña debe ser mayor a 7 dígitos"
                validacionCam = true
            } else {
                txtPasswordRegistro.error = null
            }
            if (contrasena != confirmarContrasena) {
                txtConfirmarPassword.error = "Las contraseñas no coinciden"
                validacionCam = true
            } else {
                txtConfirmarPassword.error = null
            }

            if (!validacionCam) {
                GlobalScope.launch(Dispatchers.IO) {
                    val objConexion = ClaseConexion().CadenaConexion()

                    // Generar un UUID para el nuevo empleador
                    val uuidEmpleador = UUID.randomUUID().toString()

                    // Insertar datos en la base de datos
                    val crearUsuario = objConexion?.prepareStatement(
                        "INSERT INTO Empleador(uuidEmpleador, CorreoUS, ContrasenaUS) VALUES (?, ?, ?)"
                    )!!
                    crearUsuario.setString(1, uuidEmpleador)
                    crearUsuario.setString(2, correo)
                    crearUsuario.setString(3, hashSHA256(contrasena))  // Encriptación de la contraseña
                    crearUsuario.executeUpdate()

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@activity_registrarse, "Usuario creado", Toast.LENGTH_SHORT).show()

                        // Iniciar la actividad PostLog y pasar el UUID
                        val intent = Intent(this@activity_registrarse, PostLog::class.java)
                        intent.putExtra("uuidEmpleador", uuidEmpleador)
                        startActivity(intent)

                        // Limpiar campos
                        txtCorreoRegistro.setText("")
                        txtPasswordRegistro.setText("")
                        txtConfirmarPassword.setText("")

                        // Finalizar la actividad de registro
                        finish()
                    }
                }
            }
        }

        // Mostrar u ocultar contraseñas
        imgVerPassword.setOnClickListener {
            if (txtPasswordRegistro.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                txtPasswordRegistro.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtPasswordRegistro.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        imgVerConfirmacionPassword.setOnClickListener {
            if (txtConfirmarPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                txtConfirmarPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtConfirmarPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        // Regresar al Login
        imgAtrasregistrarse.setOnClickListener {
            val pantallaLogin = Intent(this, activity_login::class.java)
            startActivity(pantallaLogin)
        }
        btnRegresarLogin.setOnClickListener {
            val pantallaLogin = Intent(this, activity_login::class.java)
            startActivity(pantallaLogin)
        }
    }
}
