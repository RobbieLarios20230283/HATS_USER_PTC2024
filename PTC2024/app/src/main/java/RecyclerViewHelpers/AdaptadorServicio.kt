package RecyclerViewHelpers

import Modelo.tbServicios
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.R

class AdaptadorServicio(var Datos: List<tbServicios>) : RecyclerView.Adapter<ViewHolderServicio>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderServicio {
        val vistaServicio = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_servicios, parent, false)
        return ViewHolderServicio(vistaServicio)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderServicio, position: Int) {
        val item = Datos[position]
        holder.txtTituloCardServicio.text = item.NombreServicios

        // Configura el OnClickListener para la tarjeta completa
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("NombreServicios", item.NombreServicios)
                putString("Descripcion", item.Descripcion)
                putString("correoTrabajador", item.correoEmp) // Añadir correo del trabajador aquí
            }
            // Navegar al ChatFragment
            val navController = Navigation.findNavController(holder.itemView)
            navController.navigate(R.id.chat, bundle) // Asegúrate de que el ID sea el correcto para tu ChatFragment
        }

        // Configura el OnClickListener para el icono de información
        holder.iconInformacion.setOnClickListener {
            val bundle = Bundle().apply {
                putString("NombreServicios", item.NombreServicios)
                putString("Descripcion", item.Descripcion)
                putString("correoEmp", item.correoEmp)
            }
            val navController = Navigation.findNavController(holder.itemView)
            navController.navigate(R.id.detalleServicio, bundle)
        }
    }
}
