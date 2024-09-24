package RecyclerViewHelpers

import Modelo.tbDirecciones
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.R

class AdaptadorDirecciones(var DatosDirecciones: List<tbDirecciones>): RecyclerView.Adapter<ViewHolderDirecciones>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDirecciones {
        val vistaDirecciones = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_direcciones, parent, false)
        return ViewHolderDirecciones(vistaDirecciones)
    }

    override fun getItemCount()= DatosDirecciones.size

    override fun onBindViewHolder(holder: ViewHolderDirecciones, position: Int) {
        val itemD = DatosDirecciones[position]

        holder.txtTituloCardDirecciones.text = itemD.NombreDireccion
    }

}