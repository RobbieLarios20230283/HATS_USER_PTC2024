package Modelo

data class tbSolicitudes(
    val uuidSolicitud: String,
    val uuidEmpleador: String,
    var uuidServicio: String,
    val Precio: String,
    val Descripcion: String,
    val longitud: String,
    val latitud: String,
    val fecha: String
)