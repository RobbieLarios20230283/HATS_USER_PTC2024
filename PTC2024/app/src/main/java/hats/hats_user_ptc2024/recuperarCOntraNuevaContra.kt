package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class recuperarCOntraNuevaContra : AppCompatActivity() {
    fun hashSHA256(password:String):String{
        val bytes=java.security.MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString(""){ "%02x".format(it)}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contra_nueva_contra)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val txtNuevaContra = findViewById<EditText>(R.id.txtNuevaContra)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)



        btnContinuar.setOnClickListener {

            val contraDigi=txtNuevaContra.text.toString()
            var validacion= false

            if(contraDigi.isEmpty()){
                txtNuevaContra.error="Es obligatorio llenar este campo"
                validacion=true
            }else{
                txtNuevaContra.error=null
            }

            if (contraDigi.length <= 7) {
                txtNuevaContra.error="la contraseña debe de tener mas de 7 digitoss"
                validacion=true
            }else{
                txtNuevaContra.error=null
            }
            if(!validacion){
                try{
                    val pantallaNExt=Intent(this,MainActivity::class.java)
                    val nombreUser = recuperarcontracorreo.correoIngresado
                    CoroutineScope(Dispatchers.IO).launch {


                        val objconexion = ClaseConexion().CadenaConexion()

                        val contraEncriptada=hashSHA256(txtNuevaContra.text.toString())

                        val actualizarContra =
                            objconexion?.prepareStatement("UPDATE Empleador SET ContrasenaUS = ? WHERE CorreoUS = ?")!!
                        actualizarContra.setString(1, contraEncriptada)
                        actualizarContra.setString(2, nombreUser)
                        actualizarContra.executeUpdate()


                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@recuperarCOntraNuevaContra,
                                "Contraseña actualizada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        startActivity(pantallaNExt)
                    }
                }catch (e:Exception){
                    println("El error es este: $e")
                }
            }


        }

    }
}