/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procedimientos;

import Conexion.*;
import Entidad.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JUAN
 */
public class ClsUsuario {
    private Connection connection=new ClsConexion().getConection();
    //--------------------------------------------------------------------------------------------------
    //-----------------------------------------METODOS--------------------------------------------------
    //-------------------------------------------------------------------------------------------------- 
    public void agregarUsuario(ClsEntidadUsuario Usuario){
        try{
            CallableStatement statement=connection.prepareCall("{call SP_I_Usuario(?,?,?,?)}");
            statement.setString("pNombre",Usuario.getStrNombreUsuario());
            statement.setString("pApellido",Usuario.getStrApellidoUsuario());
            statement.setString("pCorreo",Usuario.getStrCorreoUsuario());
            statement.setString("pPassword",Usuario.getStrPasswordUsuario());
            statement.execute();

            JOptionPane.showMessageDialog(null,"¡Usuario Agregado con éxito!","Mensaje del Sistema",1);           

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }    
    public void modificarUsuario(String codigo,ClsEntidadUsuario Usuario){
        try{
            CallableStatement statement=connection.prepareCall("{call SP_U_Usuario(?,?,?,?,?)}");
            statement.setString("pIdUsuario",codigo);
            statement.setString("pNombre",Usuario.getStrNombreUsuario());
            statement.setString("pApellido",Usuario.getStrApellidoUsuario());
            statement.setString("pCorreo",Usuario.getStrCorreoUsuario());
            statement.setString("pPassword",Usuario.getStrPasswordUsuario());
            statement.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"¡Usuario Actualizado!","Mensaje del Sistema",1);
    }
    public ArrayList<ClsEntidadUsuario> listarUsuario(){
        ArrayList<ClsEntidadUsuario> clienteusuarios=new ArrayList<ClsEntidadUsuario>();
        try{
            CallableStatement statement=connection.prepareCall("{call SP_S_Usuario}");
            ResultSet resultSet=statement.executeQuery();
            
            while (resultSet.next()){
                ClsEntidadUsuario usuario=new ClsEntidadUsuario();
                usuario.setStrIdUsuario(resultSet.getString("IdUsuario"));
                usuario.setStrNombreUsuario(resultSet.getString("Nombre"));
                usuario.setStrApellidoUsuario(resultSet.getString("Apellido"));
                usuario.setStrCorreoUsuario(resultSet.getString("Correo"));
                usuario.setStrPasswordUsuario(resultSet.getString("Password"));
                clienteusuarios.add(usuario);
            }
            return clienteusuarios;
         }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }      
    public ResultSet listarUsuarioPorParametro(String criterio, String busqueda) throws Exception{
        ResultSet rs = null;
        try{
            CallableStatement statement = connection.prepareCall("{call SP_S_UsuarioPorParametro(?,?)}");
            statement.setString("pcriterio", criterio);
            statement.setString("pbusqueda", busqueda);
            rs = statement.executeQuery();
            return rs;
        }catch(SQLException SQLex){
            throw SQLex;            
        }        
    }
    
}
