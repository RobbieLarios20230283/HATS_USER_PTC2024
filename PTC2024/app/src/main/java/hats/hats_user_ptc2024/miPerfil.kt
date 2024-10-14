package hats.hats_user_ptc2024

import Modelo.ClaseConexion
import Modelo.tbPerfil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import hats.hats_user_ptc2024.activity_login.VariableGlobal.CorreoGlobal
import hats.hats_user_ptc2024.categorias.VariableGlobal.miValor

class miPerfil : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_mi_perfil, container, false)

        fun MiPerfil(): List<tbPerfil> {
            val CorreoGlobal = CorreoGlobal
            val objConexionS = ClaseConexion().CadenaConexion()
            val statementS = objConexionS?.createStatement()

            val resultSetS = statementS?.executeQuery("SELECT * FROM Empleador WHERE CorreoUS = '$CorreoGlobal'")

            val ListaPerfil = mutableListOf<tbPerfil>()

            if (resultSetS != null) {
                while (resultSetS.next()) {
                    val nombreEmpleador = resultSetS.getString("nombreEmpleador")
                    val apellidoEmpleador = resultSetS.getString("apellidoEmpleador")
                    val telefono = resultSetS.getString("Telefono")
                    val correoUS = resultSetS.getString("CorreoUS")
                    val fechaNac = resultSetS.getString("fechanac")
                    val dui = resultSetS.getString("DuiEmpleador")

                    val perfil = tbPerfil(nombreEmpleador, apellidoEmpleador, telefono, correoUS, fechaNac, dui)
                    ListaPerfil.add(perfil)
                }
            }

            return ListaPerfil
        }

        val perfilList = MiPerfil()
        if (perfilList.isNotEmpty()) {

            val perfil = perfilList[0]

            val lblNombre: TextView = root.findViewById(R.id.lblNombreMP)
            lblNombre.text = perfil.nombreEmpleador

            val lblApellido: TextView = root.findViewById(R.id.lblApellidoMP)
            lblApellido.text = perfil.apellidoEmpleador

            val lblCorreo: TextView = root.findViewById(R.id.lblCorreoMP)
            lblCorreo.text = perfil.CorreoUS

            val lblNumero: TextView = root.findViewById(R.id.lblNumeroMP)
            lblNumero.text = perfil.Telefono

            val lblFecha: TextView = root.findViewById(R.id.lblFechaMP)
            lblFecha.text = perfil.fechanac
            
            val lblDui: TextView = root.findViewById(R.id.lblDuiMP)
            lblDui.text = perfil.dui


        }



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditarMP: Button = view.findViewById(R.id.btnEditarMPP)

        btnEditarMP.setOnClickListener {
            findNavController().navigate(R.id.editarPerfil)
        }
    }



}
