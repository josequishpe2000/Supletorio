/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Conexion.ClsConexion;
import Entidad.ClsEntidadCancion;
import Entidad.ClsEntidadUsuario;
import static Presentacion.FrmUsuario.strCodigoU;
import Procedimientos.ClsCancion;
import Procedimientos.ClsUsuario;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author JUAN
 */
public class FrmCancion extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmUsuario
     */
    
     private Connection connection=new ClsConexion().getConection();
    String Total;
    String strCodigo;
    String accion;
    int registros;
    String id[]=new String[50];
    static int intContador;
    
    //-----------------------------------------------
    public String codigo;
    static Connection conn=null;
    static ResultSet rs=null;
    DefaultTableModel dtm=new DefaultTableModel();
    String criterio,busqueda;
    
    public FrmCancion() {
        
        initComponents();
        tabCancion.setIconAt(tabCancion.indexOfComponent(pBuscar), new ImageIcon("src/iconos/busca_p1.png"));
        tabCancion.setIconAt(tabCancion.indexOfComponent(pNuevo), new ImageIcon("src/iconos/nuevo1.png"));
        buttonGroup1.add(rbtnCodigo);
        buttonGroup1.add(rbtnGenero);
        buttonGroup1.add(rbtnAutor);
        buttonGroup1.add(rbtnTitulo);
        buttonGroup1.add(rbtnUsuario);
        
        mirar();
        //actualizarTabla();
        BuscarCancionUsuario();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(707, 426);
        CrearTabla();
        //CantidadTotal();
    }
    
    void mirar(){
       tblCancion.setEnabled(true);
       btnNuevo.setEnabled(true);
       btnModificar.setEnabled(true);
       btnGuardar.setEnabled(false);
       btnCancelar.setEnabled(false);
       btnSalir.setEnabled(true);
        
        txtIdCancion.setEditable(false);
        txtIdCancion.setEnabled(false);
        txtUsuario_idusuario.setEditable(false);
        txtUsuario_idusuario.setEnabled(false);
       txtGenero.setEnabled(false);
       txtAutor.setEnabled(false);
       txtTitulo.setEnabled(false);
       txtFecha.setEnabled(false);   
   }
    
     void actualizarTabla(){
       String titulos[]={"ID_Cancion","Genero","Autor","Titulo","Fecha","Duración", "Id_Usuario"};
              
       ClsCancion canciones=new ClsCancion();
       ArrayList<ClsEntidadCancion> cancion=canciones.listarCancion();
       Iterator iterator=cancion.iterator();
       DefaultTableModel defaultTableModel=new DefaultTableModel(null,titulos);
       
       String fila[]=new String[7];
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
       
       while(iterator.hasNext()){
           ClsEntidadCancion Cancion=new ClsEntidadCancion();
           Cancion=(ClsEntidadCancion) iterator.next();
           fila[0]=Cancion.getStridcancion();
           fila[1]=Cancion.getStrgenero();       
           fila[2]=Cancion.getStrautor();
           fila[3]=Cancion.getStrtitulo();
           fila[4]=sdf.format(Cancion.getStrfecha());
           fila[5]=Cancion.getStrduracion();
           fila[6]=Cancion.getStrusuario_idusuario();
           //fila[4]=Usuario.getStrPasswordUsuario();
           defaultTableModel.addRow(fila);               
       }
       tblCancion.setModel(defaultTableModel);
   }
     
     //----------------------VALIDACIÓN DE DATOS-------------------------------------
    public boolean validardatos(){
        if (txtGenero.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el genero de la cancion");
            txtGenero.requestFocus();
            txtGenero.setBackground(Color.YELLOW);
            return false;

        }else{
            return true;
        }

    } 
    
    void limpiarCampos(){
       txtIdCancion.setText("");
       txtGenero.setText("");
       txtAutor.setText("");
       txtTitulo.setText("");
       txtFecha.setDateFormatString(null);
     
       
       rbtnCodigo.setSelected(false);
       rbtnGenero.setSelected(false);
       rbtnAutor.setSelected(false);
       txtBusqueda.setText("");
   }
    
    void modificar(){
       tblCancion.setEnabled(false);
       btnNuevo.setEnabled(false);
       btnModificar.setEnabled(false);
       btnGuardar.setEnabled(true);
       btnCancelar.setEnabled(true);
       btnSalir.setEnabled(false);
        
       txtGenero.setEnabled(true);
       txtAutor.setEnabled(true);
       txtTitulo.setEnabled(true);
       txtFecha.setEnabled(true);
       txtGenero.requestFocus();
   }
     
     
     void CrearTabla(){
   //--------------------PRESENTACION DE JTABLE----------------------
      
        TableCellRenderer render = new DefaultTableCellRenderer() { 

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                //Determinar Alineaciones   
                    if(column==0 || column==2 || column==3 || column==5 || column==7){
                        l.setHorizontalAlignment(SwingConstants.CENTER); 
                    }else{
                        l.setHorizontalAlignment(SwingConstants.LEFT);
                    }

                //Colores en Jtable        
                if (isSelected) {
                    l.setBackground(new Color(203, 159, 41));
                    //l.setBackground(new Color(168, 198, 238));
                    l.setForeground(Color.WHITE); 
                }else{
                    l.setForeground(Color.BLACK);
                    if (row % 2 == 0) {
                        l.setBackground(Color.WHITE);
                    } else {
                        //l.setBackground(new Color(232, 232, 232));
                        l.setBackground(new Color(254, 227, 152));
                    }
                }         
                return l; 
            } 
        }; 
        
        //Agregar Render
        for (int i=0;i<tblCancion.getColumnCount();i++){
            tblCancion.getColumnModel().getColumn(i).setCellRenderer(render);
        }
      
        //Activar ScrollBar
        tblCancion.setAutoResizeMode(tblCancion.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {50,200,80,80,150,80,200};
        for(int i = 0; i < tblCancion.getColumnCount(); i++) {
            tblCancion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
     
     void BuscarCancion(){
        String titulos[]={"ID","Genero","Autor","Tituto","Fecha","Duración","IdUsuario"};
        dtm.setColumnIdentifiers(titulos);
        
        ClsCancion categoria=new ClsCancion();
        busqueda=txtBusqueda.getText();
        if(rbtnCodigo.isSelected()){
            criterio="Id";
        }else if(rbtnGenero.isSelected()){
            criterio="genero";
        }else if(rbtnAutor.isSelected()){
            criterio="autor";
        }else if(rbtnTitulo.isSelected()){
            criterio="titulo";
        }
        else if(rbtnUsuario.isSelected()){
            criterio="usuario";
        }
        try{
            rs=categoria.listarCancionPorParametro(criterio,busqueda);
            boolean encuentra=false;
            String Datos[]=new String[7];
            int f,i;
            f=dtm.getRowCount();
            if(f>0){
                for(i=0;i<f;i++){
                    dtm.removeRow(0);
                }
            }
            while(rs.next()){
                Datos[0]=(String) rs.getString(1);
                Datos[1]=(String) rs.getString(2);
                Datos[2]=(String) rs.getString(3);
                Datos[3]=(String) rs.getString(4);
                Datos[4]=(String) rs.getString(5);
                Datos[5]=(String) rs.getString(6);
                Datos[6]=(String) rs.getString(7);

                dtm.addRow(Datos);
                encuentra=true;

            }
            if(encuentra=false){
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        tblCancion.setModel(dtm);
    }
     
     void BuscarCancionUsuario(){
        String titulos[]={"ID","Genero","Autor","Tituto","Fecha","Duración","IdUsuario"};
        dtm.setColumnIdentifiers(titulos);
        
        ClsCancion categoria=new ClsCancion();
        
        try{
            rs=categoria.listarCancionPorParametro("usuario",FrmUsuario.strCodigoU);
            boolean encuentra=false;
            String Datos[]=new String[7];
            int f,i;
            f=dtm.getRowCount();
            if(f>0){
                for(i=0;i<f;i++){
                    dtm.removeRow(0);
                }
            }
            while(rs.next()){
                Datos[0]=(String) rs.getString(1);
                Datos[1]=(String) rs.getString(2);
                Datos[2]=(String) rs.getString(3);
                Datos[3]=(String) rs.getString(4);
                Datos[4]=(String) rs.getString(5);
                Datos[5]=(String) rs.getString(6);
                Datos[6]=(String) rs.getString(7);

                dtm.addRow(Datos);
                encuentra=true;

            }
            if(encuentra=false){
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        tblCancion.setModel(dtm);
    }
    
 @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabCancion = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        rbtnCodigo = new javax.swing.JRadioButton();
        rbtnGenero = new javax.swing.JRadioButton();
        rbtnAutor = new javax.swing.JRadioButton();
        rbtnTitulo = new javax.swing.JRadioButton();
        txtBusqueda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCancion = new javax.swing.JTable();
        rbtnUsuario = new javax.swing.JRadioButton();
        pNuevo = new javax.swing.JPanel();
        lblIdUsuario = new javax.swing.JLabel();
        txtIdCancion = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblApelliido = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtGenero = new javax.swing.JTextField();
        txtAutor = new javax.swing.JTextField();
        txtTitulo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDuracion = new javax.swing.JTextField();
        txtUsuario_idusuario = new javax.swing.JTextField();
        txtFecha = new com.toedter.calendar.JDateChooser();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rbtnCodigo.setText("Id");
        pBuscar.add(rbtnCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        rbtnGenero.setText("Genero");
        pBuscar.add(rbtnGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        rbtnAutor.setText("Autor");
        pBuscar.add(rbtnAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        rbtnTitulo.setText("Titulo");
        pBuscar.add(rbtnTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });
        pBuscar.add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 51, 258, -1));

        tblCancion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCancion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCancionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCancion);

        pBuscar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 480, 170));

        rbtnUsuario.setText("Usuario");
        pBuscar.add(rbtnUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

        tabCancion.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIdUsuario.setText("IDCancion");
        pNuevo.add(lblIdUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        pNuevo.add(txtIdCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 60, -1));

        lblNombre.setText("Genero");
        pNuevo.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        lblApelliido.setText("Autor");
        pNuevo.add(lblApelliido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lblCorreo.setText("Titulo");
        pNuevo.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        lblPassword.setText("Fecha");
        pNuevo.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));
        pNuevo.add(txtGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 190, -1));
        pNuevo.add(txtAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 190, -1));
        pNuevo.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 190, -1));

        jLabel2.setText("Duración");
        pNuevo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel3.setText("IdUsuario");
        pNuevo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));
        pNuevo.add(txtDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 80, -1));
        pNuevo.add(txtUsuario_idusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 50, -1));
        pNuevo.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        tabCancion.addTab("Nuevo/Modificar", pNuevo);

        getContentPane().add(tabCancion);
        tabCancion.setBounds(18, 11, 530, 290);
        tabCancion.getAccessibleContext().setAccessibleName("Buscar");

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nuevo2.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo);
        btnNuevo.setBounds(560, 60, 100, 23);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar);
        btnGuardar.setBounds(560, 90, 100, 23);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar);
        btnModificar.setBounds(560, 120, 100, 23);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar);
        btnCancelar.setBounds(560, 150, 100, 23);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir);
        btnSalir.setBounds(560, 180, 100, 23);

        jLabel1.setText("Controles");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(560, 30, 60, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        accion="Nuevo";
        modificar();
        limpiarCampos();
        txtUsuario_idusuario.setText(FrmUsuario.strCodigoU);
        tblCancion.setEnabled(false);
        tabCancion.setSelectedIndex(tabCancion.indexOfComponent(pNuevo));
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if(validardatos()==true){  
            if(accion.equals("Nuevo")){
                ClsCancion canciones=new ClsCancion();
                ClsEntidadCancion cancion=new ClsEntidadCancion();
                cancion.setStrgenero(txtGenero.getText());
                cancion.setStrautor(txtAutor.getText());
                cancion.setStrtitulo(txtTitulo.getText());
                cancion.setStrfecha(txtFecha.getDate());
                cancion.setStrduracion(txtDuracion.getText());
                cancion.setStrusuario_idusuario(txtUsuario_idusuario.getText());
                canciones.agregarCancion(cancion);
                //actualizarTabla();
                BuscarCancionUsuario();
                //CantidadTotal();
            }
            if(accion.equals("Modificar")){
                ClsCancion canciones=new ClsCancion();
                ClsEntidadCancion cancion=new ClsEntidadCancion();
                cancion.setStrgenero(txtGenero.getText());
                cancion.setStrautor(txtAutor.getText());
                cancion.setStrtitulo(txtTitulo.getText());
                cancion.setStrfecha(txtFecha.getDate());
                cancion.setStrduracion(txtDuracion.getText());
                cancion.setStrusuario_idusuario(txtUsuario_idusuario.getText());
                //canciones.agregarCancion(cancion);
                canciones.modificarCancion(strCodigo, cancion);
                //actualizarTabla();
                BuscarCancionUsuario();
                limpiarCampos();
                modificar();
                //CantidadTotal();
            }
            //CrearTabla();
            mirar();
            tabCancion.setSelectedIndex(tabCancion.indexOfComponent(pBuscar)); 
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
         // TODO add your handling code here:
       if(tblCancion.getSelectedRows().length > 0 ) { 
        accion="Modificar";
        modificar();
        tabCancion.setSelectedIndex(tabCancion.indexOfComponent(pNuevo));
        }else{
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
        } 
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        mirar();
        tabCancion.setSelectedIndex(tabCancion.indexOfComponent(pBuscar));
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblCancionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCancionMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        int fila;
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        fila = tblCancion.getSelectedRow();

        if (fila == -1){
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        }else{
            defaultTableModel = (DefaultTableModel)tblCancion.getModel();
            strCodigo =  ((String) defaultTableModel.getValueAt(fila, 0));
            txtIdCancion.setText((String) defaultTableModel.getValueAt(fila, 0));
            txtGenero.setText((String) defaultTableModel.getValueAt(fila, 1));
            txtAutor.setText((String)defaultTableModel.getValueAt(fila,2));
            txtTitulo.setText((String)defaultTableModel.getValueAt(fila,3));
            txtFecha.setDateFormatString((String)defaultTableModel.getValueAt(fila,4));
            txtDuracion.setText((String)defaultTableModel.getValueAt(fila,5));
            txtUsuario_idusuario.setText((String)defaultTableModel.getValueAt(fila,6));
            //txtPassword.setText((String)defaultTableModel.getValueAt(fila,4));
        }
        mirar();
    }//GEN-LAST:event_tblCancionMouseClicked

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        // TODO add your handling code here:
        BuscarCancion();
        CrearTabla();
        //BuscarCancionUsuario();
    }//GEN-LAST:event_txtBusquedaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApelliido;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblIdUsuario;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JRadioButton rbtnAutor;
    private javax.swing.JRadioButton rbtnCodigo;
    private javax.swing.JRadioButton rbtnGenero;
    private javax.swing.JRadioButton rbtnTitulo;
    private javax.swing.JRadioButton rbtnUsuario;
    private javax.swing.JTabbedPane tabCancion;
    private javax.swing.JTable tblCancion;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtDuracion;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtGenero;
    private javax.swing.JTextField txtIdCancion;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtUsuario_idusuario;
    // End of variables declaration//GEN-END:variables
}
