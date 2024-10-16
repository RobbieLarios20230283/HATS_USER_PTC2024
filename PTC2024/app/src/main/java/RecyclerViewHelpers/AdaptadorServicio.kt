package RecyclerViewHelpers

import Modelo.tbServicios
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
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

        // Encuentra el ImageView por su ID dentro del ViewHolder
        holder.iconInformacion.setOnClickListener {
            val bundle = Bundle().apply {
                putString("NombreServicios", item.NombreServicios)
                putString("Descripcion", item.Descripcion)
            }
            val navController = findNavController(holder.itemView)
            navController.navigate(R.id.detalleServicio, bundle)
        }
    }
}
