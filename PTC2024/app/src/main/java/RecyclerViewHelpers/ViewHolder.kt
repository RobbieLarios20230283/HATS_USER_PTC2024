package RecyclerViewHelpers

import hats.hats_user_ptc2024.R

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val title: TextView = view.findViewById(R.id.txtTituloCard)
    val tittleDirecciones: TextView = view.findViewById(R.id.txtTituloDireccion)
    val imgActualizar: Button = view.findViewById(R.id.imgActualizar)
    val imgBorrar: Button = view.findViewById(R.id.imgBorrar)

    val btnPintura : Button = view.findViewById(R.id.btnPintura)

}