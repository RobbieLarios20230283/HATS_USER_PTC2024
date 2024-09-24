package RecyclerViewHelpers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.R

class ViewHolderServicio (view : View) : RecyclerView.ViewHolder(view){
    val txtTituloCardServicio = view.findViewById<TextView>(R.id.txtTituloServicio)
}