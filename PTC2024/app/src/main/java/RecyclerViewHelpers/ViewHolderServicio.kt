package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.R

class ViewHolderServicio(view: View) : RecyclerView.ViewHolder(view) {
    val txtTituloCardServicio: TextView = view.findViewById(R.id.txtTituloServicio)
    val iconInformacion: ImageView = view.findViewById(R.id.icoinfo) // Referencia al ImageView
}
