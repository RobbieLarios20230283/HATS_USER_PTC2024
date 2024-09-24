package RecyclerViewHelpers

import Modelo.tbServicios
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.R

class AdaptadorServicio (var Datos: List<tbServicios>): RecyclerView.Adapter<ViewHolderServicio>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderServicio {
        val vistaServicio = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_servicios, parent, false)
        return ViewHolderServicio(vistaServicio)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderServicio, position: Int) {

        val item = Datos[position]
        holder.txtTituloCardServicio.text = item.NombreServicios

    }




}