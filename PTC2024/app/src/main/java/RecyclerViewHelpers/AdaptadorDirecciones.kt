package RecyclerViewHelpers


import Modelo.ClaseConexion
import Modelo.tbDirecciones
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.MisDirecciones
import hats.hats_user_ptc2024.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AdaptadorDirecciones(var DatosDirecciones: List<tbDirecciones>): RecyclerView.Adapter<ViewHolderDirecciones>() {

    fun actualicePantalla(uuid: String, nuevoNombre: String) {
        val index = DatosDirecciones.indexOfFirst { it.uuidDirecciones == uuid }
        DatosDirecciones[index].NombreDireccion = nuevoNombre
        notifyDataSetChanged()
    }


    fun eliminarDireccion(nombreDireccion: String, position: Int) {
        //NOTIFICAR AL ADAPTADOR DE DIRECCIONES
        val listDirecciones = DatosDirecciones.toMutableList()
        listDirecciones.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {


            val objConexionD = ClaseConexion().CadenaConexion()


            val deleteMiDireccion =
                objConexionD?.prepareStatement("delete from tbdirecciones where NombreDireccion = ?")!!
            deleteMiDireccion.setString(1, nombreDireccion)
            deleteMiDireccion.executeUpdate()


            val commitD = objConexionD.prepareStatement("commit")
            commitD.executeUpdate()


        }
        DatosDirecciones = listDirecciones.toList()
        notifyItemRemoved(position)


        notifyDataSetChanged()
    }

    fun actualizarDireccion(nombreDireccion: String, uuidD: String){

        GlobalScope.launch(Dispatchers.IO) {

            val objConexionD = ClaseConexion().CadenaConexion()

            val actualizarMiDireccion = objConexionD?.prepareStatement("update tbdirecciones set NombreDireccion = ? where uuidDirecciones = ?")!!
            actualizarMiDireccion.setString(1, nombreDireccion)
            actualizarMiDireccion.setString(2, uuidD)
            actualizarMiDireccion.executeUpdate()

            val commitD = objConexionD.prepareStatement("commit")
            commitD.executeUpdate()

            withContext(Dispatchers.Main) {
                actualicePantalla(uuidD, nombreDireccion)
            }
        }
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

        holder.imgEditarD.setOnClickListener {
            val contextoD = holder.txtTituloCardDirecciones.context

            val builder = AlertDialog.Builder(contextoD)
            builder.setTitle("Actualizar")
            builder.setMessage("¿Estás seguro de que deseas actualizar esta dirección?")

            val cuadroDeActualizar = EditText(contextoD)
            cuadroDeActualizar.setText(itemD.NombreDireccion)
            builder.setView(cuadroDeActualizar)

            builder.setPositiveButton("Actualizar") {dialog, which ->
                actualizarDireccion(cuadroDeActualizar.text.toString(),itemD.uuidDirecciones)
            }
            builder.setNegativeButton("Cancelar") {dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

    }
}

