package RecyclerViewHelpers

import hats.hats_user_ptc2024.R

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val title: TextView = view.findViewById(R.id.txtTituloCard)

}