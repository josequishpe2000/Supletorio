package Presentacion;

import Conexion.ClsConexion;
import Entidad.ClsEntidadListaReproduccion;
import Entidad.ClsEntidadUsuario;
import static Presentacion.FrmUsuario.strCodigoU;
import Procedimientos.ClsListaReproduccion;
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
public class FrmListaReproduccion1 extends javax.swing.JInternalFrame {

    
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
    
    public FrmListaReproduccion1() {
        
        initComponents();
        tablista.setIconAt(tablista.indexOfComponent(pBuscar), new ImageIcon("src/iconos/busca_p1.png"));
        tablista.setIconAt(tablista.indexOfComponent(pNuevo), new ImageIcon("src/iconos/nuevo1.png"));
        buttonGroup1.add(rbtnCodigo);
        buttonGroup1.add(rbtNombreLista);
    
        mirar();
        //actualizarTabla();
        BuscarListaUsuario();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(707, 426);
        CrearTabla();
        //CantidadTotal();
    }
    
    void mirar(){
       tblLista.setEnabled(true);
       btnNuevo.setEnabled(true);
       btnModificar.setEnabled(true);
       btnGuardar.setEnabled(false);
       btnCancelar.setEnabled(false);
       btnSalir.setEnabled(true);
        txtIdLista.setEditable(false);
        txtIdLista.setEnabled(false);
          txtUsuario_idusuario.setEditable(false);
        txtUsuario_idusuario.setEnabled(false);
       txtNombre.setEnabled(false);
   }
    
     void actualizarTabla(){
       String titulos[]={"Id_Lista Reproduc","Nombre Lista","Id_Usuario"};
              
       ClsListaReproduccion listaReproduccion=new ClsListaReproduccion();
       ArrayList<ClsEntidadListaReproduccion> entidadListaReproduccions=listaReproduccion.listarListaReproduccion();
       Iterator iterator=entidadListaReproduccions.iterator();
       DefaultTableModel defaultTableModel=new DefaultTableModel(null,titulos);
       
       String fila[]=new String[3];
       
       while(iterator.hasNext()){
           ClsEntidadListaReproduccion entidadListaReproduccion=new ClsEntidadListaReproduccion();
           entidadListaReproduccion=(ClsEntidadListaReproduccion) iterator.next();
           fila[0]=entidadListaReproduccion.getIdLista_reproduccion();
           fila[1]=entidadListaReproduccion.getNombrelista();       
           fila[2]=entidadListaReproduccion.getIdusuario();
           //fila[4]=Usuario.getStrPasswordUsuario();
           defaultTableModel.addRow(fila);               
       }
       tblLista.setModel(defaultTableModel);
   }
     
     //----------------------VALIDACIÓN DE DATOS-------------------------------------
    public boolean validardatos(){
        if (txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el nombre de la lista");
            txtNombre.requestFocus();
            txtNombre.setBackground(Color.YELLOW);
            return false;

        }else{
            return true;
        }

    } 
    
    void limpiarCampos(){
    
       txtNombre.setText("");
       
       rbtnCodigo.setSelected(false);
       rbtNombreLista.setSelected(false);
       txtBusqueda.setText("");
   }
    
    void modificar(){
       tblLista.setEnabled(false);
       btnNuevo.setEnabled(false);
       btnModificar.setEnabled(false);
       btnGuardar.setEnabled(true);
       btnCancelar.setEnabled(true);
       btnSalir.setEnabled(false);
        
       txtNombre.setEnabled(true);
       txtNombre.requestFocus();
   }
     
     
     void CrearTabla(){
   //--------------------PRESENTACION DE JTABLE----------------------
      
        TableCellRenderer render = new DefaultTableCellRenderer() { 

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                //Determinar Alineaciones   
                    if(column==0 || column==1 || column==2 || column==3 ){
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
        for (int i=0;i<tblLista.getColumnCount();i++){
            tblLista.getColumnModel().getColumn(i).setCellRenderer(render);
        }
      
        //Activar ScrollBar
        tblLista.setAutoResizeMode(tblLista.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {50,200,80,80,150,80,200};
        for(int i = 0; i < tblLista.getColumnCount(); i++) {
            tblLista.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
     
     void BuscarLista(){
        String titulos[]={"Id_Lista Reproduc","Nombre Lista","Id_Usuario"};
        dtm.setColumnIdentifiers(titulos);
        
        ClsListaReproduccion categoria=new ClsListaReproduccion();
        busqueda=txtBusqueda.getText();
        if(rbtnCodigo.isSelected()){
            criterio="Id";
        }else if(rbtNombreLista.isSelected()){
            criterio="Nombre";
         }else if(rbtusuario.isSelected()){
            criterio="usuario";    
        }
        try{
            rs=categoria.listarlistasPorParametro(criterio,busqueda);
            boolean encuentra=false;
            String Datos[]=new String[3];
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
                
                dtm.addRow(Datos);
                encuentra=true;
            }
            if(encuentra=false){
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        tblLista.setModel(dtm);
    }
     
     void BuscarListaUsuario(){
        String titulos[]={"Id_Lista Reproduc","Nombre Lista","Id_Usuario"};
        dtm.setColumnIdentifiers(titulos);
        
        ClsListaReproduccion categoria=new ClsListaReproduccion();
        
        try{
            rs=categoria.listarlistasPorParametro("usuario",FrmUsuario.strCodigoU);
            boolean encuentra=false;
            String Datos[]=new String[3];
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

                dtm.addRow(Datos);
                encuentra=true;
            }
            if(encuentra=false){
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        tblLista.setModel(dtm);
    }
    

   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tablista = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        rbtnCodigo = new javax.swing.JRadioButton();
        rbtNombreLista = new javax.swing.JRadioButton();
        txtBusqueda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLista = new javax.swing.JTable();
        rbtusuario = new javax.swing.JRadioButton();
        pNuevo = new javax.swing.JPanel();
        lblIdUsuario = new javax.swing.JLabel();
        txtIdLista = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario_idusuario = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroup1.add(rbtnCodigo);
        rbtnCodigo.setText("Id");
        pBuscar.add(rbtnCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        buttonGroup1.add(rbtNombreLista);
        rbtNombreLista.setText("Nombre");
        rbtNombreLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtNombreListaActionPerformed(evt);
            }
        });
        pBuscar.add(rbtNombreLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });
        pBuscar.add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 51, 258, -1));

        tblLista.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLista);

        pBuscar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 430, -1));

        buttonGroup1.add(rbtusuario);
        rbtusuario.setText("usuario");
        pBuscar.add(rbtusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, -1));

        tablista.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIdUsuario.setText("Id Lista");
        pNuevo.add(lblIdUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));
        pNuevo.add(txtIdLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 60, -1));

        lblNombre.setText("Nombre");
        pNuevo.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));
        pNuevo.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 190, -1));

        jLabel3.setText("IdUsuario");
        pNuevo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));
        pNuevo.add(txtUsuario_idusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 50, -1));

        tablista.addTab("Nuevo/Modificar", pNuevo);

        getContentPane().add(tablista);
        tablista.setBounds(18, 11, 460, 320);
        tablista.getAccessibleContext().setAccessibleName("Buscar");

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nuevo1.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo);
        btnNuevo.setBounds(500, 60, 100, 23);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar);
        btnGuardar.setBounds(500, 90, 100, 23);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar);
        btnModificar.setBounds(500, 120, 100, 23);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar);
        btnCancelar.setBounds(500, 150, 100, 23);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir);
        btnSalir.setBounds(500, 180, 100, 23);

        jLabel1.setText("Controles");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(510, 30, 60, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        accion="Nuevo";
        modificar();
        limpiarCampos();
        txtUsuario_idusuario.setText(FrmUsuario.strCodigoU);
        tblLista.setEnabled(false);
        tablista.setSelectedIndex(tablista.indexOfComponent(pNuevo));
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if(validardatos()==true){  
            if(accion.equals("Nuevo")){
                ClsListaReproduccion listaReproduccion=new ClsListaReproduccion();
                ClsEntidadListaReproduccion entidadListaReproduccion=new ClsEntidadListaReproduccion();
                entidadListaReproduccion.setNombrelista(txtNombre.getText());
                entidadListaReproduccion.setIdusuario(txtUsuario_idusuario.getText());
                listaReproduccion.agregarLista(entidadListaReproduccion);
                actualizarTabla();
                //CantidadTotal();
            }
            if(accion.equals("Modificar")){
                ClsListaReproduccion listaReproduccion=new ClsListaReproduccion();
                ClsEntidadListaReproduccion entidadListaReproduccion=new ClsEntidadListaReproduccion();
                entidadListaReproduccion.setNombrelista(txtNombre.getText());
                entidadListaReproduccion.setIdusuario(txtUsuario_idusuario.getText());
                //listaReproduccion.agregarLista(entidadListaReproduccion);
                listaReproduccion.modificarLista(strCodigo,entidadListaReproduccion);
                actualizarTabla();
                limpiarCampos();
                modificar();
                //CantidadTotal();
            }
            //CrearTabla();
            mirar();
            tablista.setSelectedIndex(tablista.indexOfComponent(pBuscar)); 
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
         // TODO add your handling code here:
       if(tblLista.getSelectedRows().length > 0 ) { 
        accion="Modificar";
        modificar();
        tablista.setSelectedIndex(tablista.indexOfComponent(pNuevo));
        }else{
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
        } 
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        mirar();
        tablista.setSelectedIndex(tablista.indexOfComponent(pBuscar));
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        int fila;
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        fila = tblLista.getSelectedRow();

        if (fila == -1){
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        }else{
            defaultTableModel = (DefaultTableModel)tblLista.getModel();
            strCodigo =  ((String) defaultTableModel.getValueAt(fila, 0));
            txtIdLista.setText((String) defaultTableModel.getValueAt(fila, 0));
            txtNombre.setText((String) defaultTableModel.getValueAt(fila, 1));
            txtUsuario_idusuario.setText((String)defaultTableModel.getValueAt(fila,2));
            //txtPassword.setText((String)defaultTableModel.getValueAt(fila,4));
        }
        mirar();
    }//GEN-LAST:event_tblListaMouseClicked

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        // TODO add your handling code here:
        BuscarLista();
        CrearTabla();
        //BuscarCancionUsuario();
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void rbtNombreListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtNombreListaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtNombreListaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIdUsuario;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JRadioButton rbtNombreLista;
    private javax.swing.JRadioButton rbtnCodigo;
    private javax.swing.JRadioButton rbtusuario;
    private javax.swing.JTabbedPane tablista;
    private javax.swing.JTable tblLista;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtIdLista;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario_idusuario;
    // End of variables declaration//GEN-END:variables
}
