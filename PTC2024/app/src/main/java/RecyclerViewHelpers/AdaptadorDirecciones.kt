package RecyclerViewHelpers


import Modelo.ClaseConexion
import Modelo.tbDirecciones
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.MisDirecciones
import hats.hats_user_ptc2024.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AdaptadorDirecciones(var DatosDirecciones: List<tbDirecciones>): RecyclerView.Adapter<ViewHolderDirecciones>() {


    fun eliminarDireccion(nombreDireccion: String, position: Int) {
        //NOTIFICAR AL ADAPTADOR DE DIRECCIONES
        val listDirecciones = DatosDirecciones.toMutableList()
        listDirecciones.removeAt(position)

        GlobalScope.launch (Dispatchers.IO) {


            val objConexionD = ClaseConexion().CadenaConexion()


            val deleteMiDireccion = objConexionD?.prepareStatement("delete from tbdirecciones where NombreDireccion = ?")!!
            deleteMiDireccion.setString(1, nombreDireccion)
            deleteMiDireccion.executeUpdate()


            val commitD = objConexionD.prepareStatement("commit")
            commitD.executeUpdate()


        }
        DatosDirecciones = listDirecciones.toList()
        notifyItemRemoved(position)


        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDirecciones {
        val vistaDirecciones = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_direcciones, parent, false)
        return ViewHolderDirecciones(vistaDirecciones)
    }


    override fun getItemCount()= DatosDirecciones.size


    override fun onBindViewHolder(holder: ViewHolderDirecciones, position: Int) {
        val itemD = DatosDirecciones[position]


        holder.txtTituloCardDirecciones.text = itemD.NombreDireccion


        holder.imgBorrarD.setOnClickListener {
            val contextoD = holder.txtTituloCardDirecciones.context


            val builder = AlertDialog.Builder(contextoD)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estás seguro de que deseas eliminar esta dirección?")


            builder.setPositiveButton("Sí") {
                    dialog, which ->


                eliminarDireccion(itemD.NombreDireccion,position)
            }
            builder.setNegativeButton("No") {
                    dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }


    }
}

