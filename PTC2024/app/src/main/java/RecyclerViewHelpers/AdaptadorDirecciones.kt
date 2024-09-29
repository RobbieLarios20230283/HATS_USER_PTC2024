package RecyclerViewHelpers


import Modelo.ClaseConexion
import Modelo.tbDirecciones
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.MisDirecciones
import hats.hats_user_ptc2024.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AdaptadorDirecciones(var DatosDirecciones: List<tbDirecciones>): RecyclerView.Adapter<ViewHolderDirecciones>() {

    //Refrescar despues de editar (Pendiente)
    fun actualicePantalla(uuid: String, nuevoNombre: String, nuevaUbicacion: String) {
        val index = DatosDirecciones.indexOfFirst { it.uuidDirecciones == uuid }
        DatosDirecciones[index].NombreDireccion = nuevoNombre
        DatosDirecciones[index].Ubicacion = nuevaUbicacion
        notifyDataSetChanged()
    }

    //Funcion funcionando correctamente (No tocar)
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

    //Funcion funcionando correctamente (No tocar)
    fun actualizarDirect(NombreD: String, DireccionD: String?, uuidD: String, scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            val objConexionD = ClaseConexion().CadenaConexion()

            val ActuDireccion = objConexionD?.prepareStatement(
                "UPDATE tbdirecciones SET " + "NombreDireccion = ?, Ubicacion = ? WHERE uuidDirecciones = ?")!!
            ActuDireccion?.apply {
                setString(1, NombreD)
                setString(2, DireccionD)
                setString(3, uuidD)
                executeUpdate()
            }
            val commitD = objConexionD?.prepareStatement("commit")
            commitD?.executeUpdate()

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

        //No tocar (Funcionando)
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

        //No tocar (Funcionando)
        holder.imgEditarD.setOnClickListener {
            val context = holder.txtTituloCardDirecciones.context
            val dialogView = LayoutInflater.from(context).inflate(R.layout.activity_actualizar, null)
            val txtNombreDireccion_A = dialogView.findViewById<EditText>(R.id.txtNombreDireccion_A)
            val txtDireccion_A = dialogView.findViewById<EditText>(R.id.txtDireccion_A)

            txtNombreDireccion_A.setText(itemD.NombreDireccion)
            txtDireccion_A.setText(itemD.Ubicacion)

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar este Paciente")
            builder.setView(dialogView)

            builder.setPositiveButton("Actualizar") { dialog, _ ->
                val txtNombreDireccion_A = txtNombreDireccion_A.text.toString()
                val txtDireccion_A = txtDireccion_A.text.toString()
                actualizarDirect(txtNombreDireccion_A, txtDireccion_A, itemD.uuidDirecciones, GlobalScope)
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }

        //No tocar (No funcionando)
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("NombreDireccion", itemD.NombreDireccion)
                putString("Ubicacion", itemD.Ubicacion)
            }
            val navController = findNavController(holder.itemView)
            navController.navigate(R.id.detalles, bundle)
        }
    }
}

