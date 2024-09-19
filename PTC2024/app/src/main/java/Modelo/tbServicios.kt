package Modelo

data class tbServicios(
    val uuidSolicitud: String,
    val uuidEmpleador: String,
    val uuidServicios: String,
    val idServicio: Int,
    val Precio: Float,
    val Descripcion: String,
    val longitud: Double,
    val latitud: Double,
    val Fecha: String
)