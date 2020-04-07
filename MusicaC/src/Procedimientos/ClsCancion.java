/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procedimientos;

import Conexion.ClsConexion;
import Entidad.ClsEntidadCancion;
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
public class ClsCancion {
    private Connection connection=new ClsConexion().getConection();
    //--------------------------------------------------------------------------------------------------
    //-----------------------------------------METODOS--------------------------------------------------
    //-------------------------------------------------------------------------------------------------- 
    public void agregarCancion(ClsEntidadCancion Cancion){
        try{
            CallableStatement statement=connection.prepareCall("{call SP_I_Cancion(?,?,?,?,?,?)}");
            statement.setString("pgenero",Cancion.getStrgenero());
            statement.setString("pautor",Cancion.getStrautor());
            statement.setString("ptitulo",Cancion.getStrtitulo());
            statement.setDate ("pfecha", new java.sql.Date(Cancion.getStrfecha().getTime()));
            statement.setString("pduracion",Cancion.getStrduracion());
            statement.setString("pusuario_idusuario",Cancion.getStrusuario_idusuario());
            statement.execute();

            JOptionPane.showMessageDialog(null,"¡Cancion Agregado con éxito!","Mensaje del Sistema",1);           

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }    
    public void modificarCancion(String codigo,ClsEntidadCancion Cancion){
        try{
            CallableStatement statement=connection.prepareCall("{call SP_U_Cancion(?,?,?,?,?,?)}");
            statement.setString("pidcancion",codigo);
            statement.setString("pgenero",Cancion.getStrgenero());
            statement.setString("pautor",Cancion.getStrautor());
            statement.setString("ptitulo",Cancion.getStrtitulo());
            statement.setDate ("pfecha", new java.sql.Date(Cancion.getStrfecha().getTime()));
            statement.setString("pduracion",Cancion.getStrduracion());
            statement.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"¡Cancion Actualizada!","Mensaje del Sistema",1);
    }
    public ArrayList<ClsEntidadCancion> listarCancion(){
        ArrayList<ClsEntidadCancion> canciones=new ArrayList<ClsEntidadCancion>();
        try{
            CallableStatement statement=connection.prepareCall("{call SP_S_Cancion}");
            ResultSet resultSet=statement.executeQuery();
            
            while (resultSet.next()){
                ClsEntidadCancion cancion=new ClsEntidadCancion();
                cancion.setStridcancion(resultSet.getString("idcancion"));
                cancion.setStrgenero(resultSet.getString("genero"));
                cancion.setStrautor(resultSet.getString("autor"));
                cancion.setStrtitulo(resultSet.getString("titulo"));
                cancion.setStrfecha(resultSet.getDate("fecha"));
                cancion.setStrduracion(resultSet.getString("duracion"));
                cancion.setStrusuario_idusuario(resultSet.getString("usuario_idusuario"));
                canciones.add(cancion);
            }
            return canciones;
         }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }      
    public ResultSet listarCancionPorParametro(String criterio, String busqueda) throws Exception{
        ResultSet rs = null;
        try{
            CallableStatement statement = connection.prepareCall("{call SP_S_CancionPorParametro(?,?)}");
            statement.setString("pcriterio", criterio);
            statement.setString("pbusqueda", busqueda);
            rs = statement.executeQuery();
            return rs;
        }catch(SQLException SQLex){
            throw SQLex;            
        }        
    }
}
