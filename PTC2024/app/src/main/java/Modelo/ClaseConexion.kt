package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun CadenaConexion(): Connection? {

        try {
            val url = "jdbc:oracle:thin:@AGREGAR IP:1521:xe"
            val usuario = "SYSTEM"
            val contrasena = "ITR2024"

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        }catch (e:Exception){
            println("ERROR:$e")
            return null
        }
    }
}