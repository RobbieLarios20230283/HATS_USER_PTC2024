package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.tbDirecciones
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import hats.hats_user_ptc2024.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdaptadorD (private var Data: List<tbDirecciones>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_direcciones, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = Data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Data[position]
        holder.tittleDirecciones.text = item.uuidDirecciones

        holder.imgBorrar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmación")
            builder.setMessage("¿Está seguro que quiere borrar?")

            builder.setPositiveButton("Si") { dialog, wich ->
                eliminarServicios(item.NombreDireccion, position)
            }
            builder.setNegativeButton("No") { dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        holder.imgActualizar.setOnClickListener {
            val context = holder.itemView.context
            val dialogView = LayoutInflater.from(context).inflate(R.layout.activity_actualizar, null)
            val txtNombreDirecciones_A = dialogView.findViewById<EditText>(R.id.txtNombreDireccion_A)
            val txtDireccion_A = dialogView.findViewById<EditText>(R.id.txtDireccion_A)



            txtNombreDirecciones_A.setText(item.NombreDireccion)
            txtDireccion_A.setText(item.Direccion)

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar esta Direccion")
            builder.setView(dialogView)

            builder.setPositiveButton("Actualizar") { dialog, _ ->
                val NombreDireccion = txtNombreDirecciones_A.text.toString()
                val Direccion = txtDireccion_A.text.toString()
                actualizarServicios(NombreDireccion, Direccion, item.uuidDirecciones, GlobalScope)
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("NombreDireccion", item.NombreDireccion)
                putString("Direccion", item.Direccion)
            }
            val navController = findNavController(holder.itemView)
            navController.navigate(R.id.detalles, bundle)
        }
    }

    private fun findNavController(view: View): NavController {
        val fragment = view.findFragment<Fragment>()
        return NavHostFragment.findNavController(fragment)
    }


    fun actualizarServicios(
        NombreDireccion: String,
        Direccion: String?,
        uuid: String,
        scope: CoroutineScope
    ) {
        scope.launch(Dispatchers.IO) {
            // 1- Creo un obj de la clase conexion
            val objConexion = ClaseConexion().CadenaConexion()

            // 2- Creo una variable que contenga un PrepareStatement
            val updatePaciente = objConexion?.prepareStatement(
                "UPDATE tbdirecciones SET " + "NombreDireccion = ?,Direccion = ? WHERE uuidDirecciones = ?"
            )
            updatePaciente?.apply {
                setString(1, NombreDireccion)
                setString(2, Direccion)
                setString(3, uuid)
                executeUpdate()
            }

        }
    }


    fun eliminarServicios(nombrePaciente: String, posicion: Int){
        val listaDatos = Data.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().CadenaConexion()

            val deletePaciente = objConexion?.prepareStatement("delete tbdirecciones where NombreDireccion = ?")!!
            deletePaciente.setString(1, nombrePaciente)
            deletePaciente.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }
        Data = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    fun setData(newData: List<tbDirecciones>) {
        Data = newData
        notifyDataSetChanged()
    }

}
