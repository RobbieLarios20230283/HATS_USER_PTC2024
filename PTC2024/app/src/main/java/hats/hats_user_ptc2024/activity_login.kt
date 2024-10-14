package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.MessageDigest

class activity_login : AppCompatActivity() {

    companion object VariableGlobal{
        lateinit var CorreoGlobal : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun hashSHA256(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        val txtCorreoLogin = findViewById<EditText>(R.id.txtcorreo)
        val txtPasswordLogin = findViewById<EditText>(R.id.txtcontraseña)
        val txtRecuperarContrasena = findViewById<TextView>(R.id.txtrecupcontra)
        val btnIngresar = findViewById<Button>(R.id.btniniciar)
        val btnRegistrarme = findViewById<TextView>(R.id.txtregistrarse)


        btnIngresar.setOnClickListener {

            CorreoGlobal = txtCorreoLogin.text.toString()

            val pantallaPrincipal = Intent(this, MainActivity::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().CadenaConexion()
                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM Empleador WHERE CorreoUS = ? AND ContrasenaUS = ?")!!
                comprobarUsuario.setString(1, txtCorreoLogin.text.toString())
                comprobarUsuario.setString(2, hashSHA256(txtPasswordLogin.text.toString()))
                println(hashSHA256(txtPasswordLogin.text.toString()))
                val resultado = comprobarUsuario.executeQuery()
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    println("Usuario inexistentente")
                }
            }
        }

        btnRegistrarme.setOnClickListener {
            //Cambio de pantalla
            val pantallaRegistrarme = Intent(this, activity_registrarse::class.java)
            startActivity(pantallaRegistrarme)
        }

        txtRecuperarContrasena.setOnClickListener {
            //Cambio de pantalla
            val pantallaRecuperarContra = Intent(this, recuperarcontracorreo::class.java)
            startActivity(pantallaRecuperarContra)
        }
    }
}