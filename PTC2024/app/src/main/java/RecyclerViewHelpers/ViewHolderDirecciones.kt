package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.R

class ViewHolderDirecciones(view: View) : RecyclerView.ViewHolder(view) {
    val txtTituloCardDirecciones = view.findViewById<TextView>(R.id.txtTituloDirecciones)
    val imgBorrarD= view.findViewById<ImageView>(R.id.imgBorrarD)
    val imgEditarD= view.findViewById<ImageView>(R.id.imgActualizarD)

}