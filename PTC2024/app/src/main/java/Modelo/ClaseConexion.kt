package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun CadenaConexion(): Connection? {

        try {
            val url = "jdbc:oracle:thin:@192.168.0.6:1521:xe"
            val usuario = "system"
            val contrasena = "itr2023"

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        }catch (e:Exception){
            println("error:$e")
            return null
        }
    }
}