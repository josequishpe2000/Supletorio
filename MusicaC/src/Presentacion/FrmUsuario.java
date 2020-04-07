/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import static musica.Principal.*;
import Conexion.ClsConexion;
import Entidad.ClsEntidadUsuario;
import Procedimientos.ClsUsuario;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
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
public class FrmUsuario extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmUsuario
     */
    
     private Connection connection=new ClsConexion().getConection();
    String Total;
    public static String strCodigoU;
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
    
    public FrmUsuario() {
        
        initComponents();
        tabUsuario.setIconAt(tabUsuario.indexOfComponent(pBuscar), new ImageIcon("src/iconos/busca_p1.png"));
        tabUsuario.setIconAt(tabUsuario.indexOfComponent(pNuevo), new ImageIcon("src/iconos/nuevo1.png"));
        buttonGroup1.add(rbtnCodigo);
        buttonGroup1.add(rbtnNombre);
        buttonGroup1.add(rbtnApellido);
        
        mirar();
        actualizarTabla();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(707, 426);
        CrearTabla();
        //CantidadTotal();
    }
    
    void mirar(){
       tblUsuario.setEnabled(true);
       btnNuevo.setEnabled(true);
       btnModificar.setEnabled(true);
       btnGuardar.setEnabled(false);
       btnCancelar.setEnabled(false);
       btnSalir.setEnabled(true);
        
       //txtIdUsuario.setEditable(false);
       //txtIdUsuario.setEnabled(false);
       txtNombre.setEnabled(false);
       txtApellido.setEnabled(false);
       txtCorreo.setEnabled(false);
       txtPassword.setEnabled(false);   
   }
    
     void actualizarTabla(){
       String titulos[]={"ID","Nombre","Apellido","Correo"};
              
       ClsUsuario clientes=new ClsUsuario();
       ArrayList<ClsEntidadUsuario> cliente=clientes.listarUsuario();
       Iterator iterator=cliente.iterator();
       DefaultTableModel defaultTableModel=new DefaultTableModel(null,titulos);
       
       String fila[]=new String[4];
       while(iterator.hasNext()){
           ClsEntidadUsuario Usuario=new ClsEntidadUsuario();
           Usuario=(ClsEntidadUsuario) iterator.next();
           fila[0]=Usuario.getStrIdUsuario();
           fila[1]=Usuario.getStrNombreUsuario();       
           fila[2]=Usuario.getStrApellidoUsuario();
           fila[3]=Usuario.getStrCorreoUsuario();
           //fila[4]=Usuario.getStrPasswordUsuario();
           defaultTableModel.addRow(fila);               
       }
       tblUsuario.setModel(defaultTableModel);
   }
     
     //----------------------VALIDACIÓN DE DATOS-------------------------------------
    public boolean validardatos(){
        if (txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese nombre del usuario");
            txtNombre.requestFocus();
            txtNombre.setBackground(Color.YELLOW);
            return false;

        }else{
            return true;
        }

    } 
    
    void limpiarCampos(){
       txtIdUsuario.setText("");
       txtNombre.setText("");
       txtApellido.setText("");
       txtCorreo.setText("");
       txtPassword.setText("");
     
       
       rbtnCodigo.setSelected(false);
       rbtnNombre.setSelected(false);
       rbtnApellido.setSelected(false);
       txtBusqueda.setText("");
   }
    
    void modificar(){
       tblUsuario.setEnabled(false);
       btnNuevo.setEnabled(false);
       btnModificar.setEnabled(false);
       btnGuardar.setEnabled(true);
       btnCancelar.setEnabled(true);
       btnSalir.setEnabled(false);
        
       txtNombre.setEnabled(true);
       txtApellido.setEnabled(true);
       txtCorreo.setEnabled(true);
       txtPassword.setEnabled(true);
       txtNombre.requestFocus();
   }
     
     
     void CrearTabla(){
   //--------------------PRESENTACION DE JTABLE----------------------
      
        TableCellRenderer render = new DefaultTableCellRenderer() { 

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                //Determinar Alineaciones   
                    if(column==0 || column==2 || column==3 || column==5){
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
        for (int i=0;i<tblUsuario.getColumnCount();i++){
            tblUsuario.getColumnModel().getColumn(i).setCellRenderer(render);
        }
      
        //Activar ScrollBar
        tblUsuario.setAutoResizeMode(tblUsuario.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {50,200,80,80,150,80,200};
        for(int i = 0; i < tblUsuario.getColumnCount(); i++) {
            tblUsuario.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
     void BuscarUsuario(){
        String titulos[]={"ID","nombre","apellido","correo","password"};
        dtm.setColumnIdentifiers(titulos);
        
        ClsUsuario categoria=new ClsUsuario();
        busqueda=txtBusqueda.getText();
        if(rbtnCodigo.isSelected()){
            criterio="id";
        }else if(rbtnNombre.isSelected()){
            criterio="nombre";
        }else if(rbtnApellido.isSelected()){
            criterio="apellido";
        }else if(rbtemail.isSelected()){
            criterio="correo";
        }
        try{
            rs=categoria.listarUsuarioPorParametro(criterio,busqueda);
            boolean encuentra=false;
            String Datos[]=new String[5];
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
                dtm.addRow(Datos);
                encuentra=true;

            }
            if(encuentra=false){
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        tblUsuario.setModel(dtm);
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabUsuario = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        rbtnCodigo = new javax.swing.JRadioButton();
        rbtnNombre = new javax.swing.JRadioButton();
        rbtnApellido = new javax.swing.JRadioButton();
        txtBusqueda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        rbtemail = new javax.swing.JRadioButton();
        pNuevo = new javax.swing.JPanel();
        lblIdUsuario = new javax.swing.JLabel();
        txtIdUsuario = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblApelliido = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnCancion = new javax.swing.JButton();
        btnlista = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rbtnCodigo.setText("Id");
        pBuscar.add(rbtnCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        rbtnNombre.setText("Nombre");
        pBuscar.add(rbtnNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        rbtnApellido.setText("Apellido");
        pBuscar.add(rbtnApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });
        pBuscar.add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 51, 258, -1));

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuario);

        pBuscar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 430, 210));

        buttonGroup1.add(rbtemail);
        rbtemail.setText("correo");
        rbtemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtemailActionPerformed(evt);
            }
        });
        pBuscar.add(rbtemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        tabUsuario.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIdUsuario.setText("IDUsuario");
        pNuevo.add(lblIdUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));
        pNuevo.add(txtIdUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 60, -1));

        lblNombre.setText("Nombre");
        pNuevo.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lblApelliido.setText("Apellido");
        pNuevo.add(lblApelliido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        lblCorreo.setText("Correo");
        pNuevo.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        lblPassword.setText("Password");
        pNuevo.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));
        pNuevo.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 190, -1));
        pNuevo.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 190, -1));
        pNuevo.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 190, -1));
        pNuevo.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 190, -1));

        btnCancion.setText("Canciones");
        btnCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancionActionPerformed(evt);
            }
        });
        pNuevo.add(btnCancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, -1));

        btnlista.setText("lista de reproduccion");
        btnlista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlistaActionPerformed(evt);
            }
        });
        pNuevo.add(btnlista, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, -1, 30));

        tabUsuario.addTab("Nuevo/Modificar", pNuevo);

        getContentPane().add(tabUsuario);
        tabUsuario.setBounds(18, 11, 500, 330);
        tabUsuario.getAccessibleContext().setAccessibleName("Buscar");

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo);
        btnNuevo.setBounds(520, 70, 110, 23);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar);
        btnGuardar.setBounds(520, 100, 110, 23);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar);
        btnModificar.setBounds(520, 130, 110, 23);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar);
        btnCancelar.setBounds(520, 160, 110, 23);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir);
        btnSalir.setBounds(520, 190, 110, 23);

        jLabel1.setText("Controles");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(550, 40, 60, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        accion="Nuevo";
        modificar();
        limpiarCampos();
        tblUsuario.setEnabled(false);
        tabUsuario.setSelectedIndex(tabUsuario.indexOfComponent(pNuevo));
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(validardatos()==true){  
            if(accion.equals("Nuevo")){
                ClsUsuario usuarios=new ClsUsuario();
                ClsEntidadUsuario usuario=new ClsEntidadUsuario();
                usuario.setStrNombreUsuario(txtNombre.getText());
                usuario.setStrApellidoUsuario(txtApellido.getText());
                usuario.setStrCorreoUsuario(txtCorreo.getText());
                usuario.setStrPasswordUsuario(txtPassword.getText());
                usuarios.agregarUsuario(usuario);
                actualizarTabla();
                //CantidadTotal();
            }
            if(accion.equals("Modificar")){
                ClsUsuario usuarios=new ClsUsuario();
                ClsEntidadUsuario usuario=new ClsEntidadUsuario();
                usuario.setStrNombreUsuario(txtNombre.getText());
                usuario.setStrApellidoUsuario(txtApellido.getText());
                usuario.setStrCorreoUsuario(txtCorreo.getText());
                usuario.setStrPasswordUsuario(txtPassword.getText());
                //usuarios.agregarUsuario(usuario);
                usuarios.modificarUsuario(strCodigoU, usuario);
                actualizarTabla();
                limpiarCampos();
                modificar();
                //CantidadTotal();
            }
            //CrearTabla();
            mirar();
            tabUsuario.setSelectedIndex(tabUsuario.indexOfComponent(pBuscar)); 
        }
       
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
   
       if(tblUsuario.getSelectedRows().length > 0 ) { 
        accion="Modificar";
        modificar();
        tabUsuario.setSelectedIndex(tabUsuario.indexOfComponent(pNuevo));
        }else{
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
        } 
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        mirar();
        tabUsuario.setSelectedIndex(tabUsuario.indexOfComponent(pBuscar));
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuarioMouseClicked
 
        int fila;
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        fila = tblUsuario.getSelectedRow();

        if (fila == -1){
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        }else{
            defaultTableModel = (DefaultTableModel)tblUsuario.getModel();
            strCodigoU =  ((String) defaultTableModel.getValueAt(fila, 0));
            txtIdUsuario.setText((String) defaultTableModel.getValueAt(fila, 0));
            txtNombre.setText((String) defaultTableModel.getValueAt(fila, 1));
            txtApellido.setText((String)defaultTableModel.getValueAt(fila,2));
            txtCorreo.setText((String)defaultTableModel.getValueAt(fila,3));
            //txtPassword.setText((String)defaultTableModel.getValueAt(fila,4));
        }
        mirar();
    }//GEN-LAST:event_tblUsuarioMouseClicked

    private void btnCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancionActionPerformed
        // TODO add your handling code here:
        Presentacion.FrmCancion Cancion=new Presentacion.FrmCancion();
        Escritorio.add(Cancion);
        //Cancion.show();
        Cancion.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnCancionActionPerformed

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        BuscarUsuario();
        CrearTabla();
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void rbtemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtemailActionPerformed

    private void btnlistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlistaActionPerformed
       Presentacion.FrmListaReproduccion1 lista=new Presentacion.FrmListaReproduccion1();
        Escritorio.add(lista);
        //Cancion.show();
        lista.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnlistaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancion;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnlista;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApelliido;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblIdUsuario;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JRadioButton rbtemail;
    private javax.swing.JRadioButton rbtnApellido;
    private javax.swing.JRadioButton rbtnCodigo;
    private javax.swing.JRadioButton rbtnNombre;
    private javax.swing.JTabbedPane tabUsuario;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtIdUsuario;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
