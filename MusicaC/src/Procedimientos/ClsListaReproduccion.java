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
public class ClsListaReproduccion {
    private Connection connection=new ClsConexion().getConection();
    //--------------------------------------------------------------------------------------------------
    //-----------------------------------------METODOS--------------------------------------------------
    //-------------------------------------------------------------------------------------------------- 
    public void agregarLista(ClsEntidadListaReproduccion listaReproduccion){
        try{
            CallableStatement statement=connection.prepareCall("{call SP_I_ListadeReproduccion(?,?)}");
            statement.setString("pnombreLista",listaReproduccion.getNombrelista());
            statement.setString("pUsuario_Idusuario",listaReproduccion.getIdusuario());
            statement.execute();

            JOptionPane.showMessageDialog(null,"¡Lista Agregado con éxito!","Mensaje del Sistema",1);           

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }    
    public void modificarLista(String codigo,ClsEntidadListaReproduccion listaReproduccion){
        try{
            CallableStatement statement=connection.prepareCall("{call SP_U_ListadeReproduccion(?,?)}");
            statement.setString("pidLista_reproduccion",codigo);
            statement.setString("pnombreLista",listaReproduccion.getNombrelista());
            statement.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"¡Lista Actualizado!","Mensaje del Sistema",1);
    }
    
    
    public ArrayList<ClsEntidadListaReproduccion> listarListaReproduccion(){
        ArrayList<ClsEntidadListaReproduccion> clientelistas=new ArrayList<ClsEntidadListaReproduccion>();
        try{
            CallableStatement statement=connection.prepareCall("{call SP_S_Listadereproduccion}");
            ResultSet resultSet=statement.executeQuery();
            
            while (resultSet.next()){
                ClsEntidadListaReproduccion listaReproduccion=new ClsEntidadListaReproduccion();
                listaReproduccion.setIdLista_reproduccion(resultSet.getString("idLista_reproduccion"));
                listaReproduccion.setNombrelista(resultSet.getString("nombrelista"));
                listaReproduccion.setIdusuario(resultSet.getString("usuario_idusuario"));
                clientelistas.add(listaReproduccion);
            }
            return clientelistas;
         }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }      
    public ResultSet listarlistasPorParametro(String criterio, String busqueda) throws Exception{
        ResultSet rs = null;
        try{
            CallableStatement statement = connection.prepareCall("{call SP_S_ListaPorParametro(?,?)}");
            statement.setString("pcriterio", criterio);
            statement.setString("pbusqueda", busqueda);
            rs = statement.executeQuery();
            return rs;
        }catch(SQLException SQLex){
            throw SQLex;            
        }        
    }
    
}
