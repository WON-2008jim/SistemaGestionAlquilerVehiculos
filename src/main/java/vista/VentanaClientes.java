/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;
import com.mycompany.sistemagestionalquilervehiculos.Cliente;
import logica.GestorClientes;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author jimmy
 */
public class VentanaClientes extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaClientes.class.getName());
    private GestorClientes gestor = new GestorClientes();
    private DefaultTableModel modeloTabla;

    /**
     * Creates new form VentanaClientes
     */
    public VentanaClientes() {
        initComponents();
        setTitle("Módulo de Gestión de Clientes");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        try {
            java.net.URL imgURL = getClass().getResource("/icons/gestor.png");
            if (imgURL != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(imgURL);
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
                jLabel1.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT); // Texto a la derecha
                jLabel1.setIconTextGap(10); // Espacio entre el icono y el texto
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(70, 80, 95), 1, true),
            " ", // Título elegante integrado en la esquina superior
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", 1, 12),
            new java.awt.Color(255, 255, 153)
        ));
        try {
            // Registrar
            java.net.URL urlReg = getClass().getResource("/icons/btnRegistrar.png");
            if (urlReg != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(urlReg);
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
                btnRegistrar.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                btnRegistrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnRegistrar.setIconTextGap(8);
            }

            // Modificar
            java.net.URL urlMod = getClass().getResource("/icons/btnModificar.png");
            if (urlMod != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(urlMod);
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
                btnModificar.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnModificar.setIconTextGap(8);
            }

            // Eliminar
            java.net.URL urlEli = getClass().getResource("/icons/btnEliminar.png");
            if (urlEli != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(urlEli);
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
                btnEliminar.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnEliminar.setIconTextGap(8);
            }

            // Limpiar
            java.net.URL urlLim = getClass().getResource("/icons/btnLimpiar.png");
            if (urlLim != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(urlLim);
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
                btnLimpiar.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnLimpiar.setIconTextGap(8);
            }

        } catch (Exception e) {
            System.err.println("Error al cargar iconos en botones: " + e.getMessage());
        }
        
        jScrollPane2.getViewport().setBackground(new java.awt.Color(30, 34, 46)); // Fondo oscuro en la zona vacía
        jTable2.setBackground(new java.awt.Color(30, 34, 46));
        jTable2.setForeground(new java.awt.Color(255, 255, 255)); // Texto blanco
        jTable2.setSelectionBackground(new java.awt.Color(51, 153, 0)); // Verde esmeralda al seleccionar fila
        jTable2.setSelectionForeground(new java.awt.Color(255, 255, 255));
        
        // Configurar las columnas reales de tu tabla según tu Base de Datos
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "DPI", "NIT", "Teléfono", "Correo", "No. Licencia"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar edición directa de celdas en la JTable
            }
        };
        jTable2.setModel(modeloTabla);
        
        // Cargar los registros existentes desde PostgreSQL al iniciar
        refrescarTabla();
    }
    private void refrescarTabla() {
        gestor.cargarTablaClientes(modeloTabla);
    }
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDPI.setText("");
        txtNIT.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtLicencia.setText("");
        txtDPI.setEditable(true); // Permitir ingresar DPI nuevo
        jTable2.clearSelection();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDPI = new javax.swing.JTextField();
        txtNIT = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtLicencia = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Bauhaus 93", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 0));
        jLabel1.setText("MÓDULO DE GESTION DE CLIENTES");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(364, 364, 364)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(347, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(30, 34, 46));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 153));
        jLabel2.setText("Datos del Cliente:");

        jLabel3.setForeground(new java.awt.Color(164, 164, 164));
        jLabel3.setText("Nombre:");

        jLabel4.setForeground(new java.awt.Color(164, 164, 164));
        jLabel4.setText("DPI:");

        jLabel5.setForeground(new java.awt.Color(164, 164, 164));
        jLabel5.setText("NIT:");

        jLabel6.setForeground(new java.awt.Color(164, 164, 164));
        jLabel6.setText("Teléfono:");

        jLabel7.setForeground(new java.awt.Color(164, 164, 164));
        jLabel7.setText("Correo:");

        jLabel8.setForeground(new java.awt.Color(164, 164, 164));
        jLabel8.setText("No. Licencia:");

        txtNombre.setBackground(new java.awt.Color(51, 51, 51));
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));

        txtDPI.setBackground(new java.awt.Color(51, 51, 51));
        txtDPI.setForeground(new java.awt.Color(255, 255, 255));

        txtNIT.setBackground(new java.awt.Color(51, 51, 51));
        txtNIT.setForeground(new java.awt.Color(255, 255, 255));

        txtTelefono.setBackground(new java.awt.Color(51, 51, 51));
        txtTelefono.setForeground(new java.awt.Color(255, 255, 255));

        txtCorreo.setBackground(new java.awt.Color(51, 51, 51));
        txtCorreo.setForeground(new java.awt.Color(255, 255, 255));

        txtLicencia.setBackground(new java.awt.Color(51, 51, 51));
        txtLicencia.setForeground(new java.awt.Color(255, 255, 255));

        btnRegistrar.setBackground(new java.awt.Color(51, 102, 0));
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(this::btnRegistrarActionPerformed);

        btnModificar.setBackground(new java.awt.Color(0, 153, 204));
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);

        btnEliminar.setBackground(new java.awt.Color(255, 0, 0));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnLimpiar.setBackground(new java.awt.Color(102, 102, 102));
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNIT)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCorreo)
                                    .addComponent(txtLicencia, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                            .addComponent(txtDPI)
                            .addComponent(txtNombre))
                        .addGap(88, 88, 88)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(208, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrar))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(txtNIT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLicencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));

        jTable2.setBackground(new java.awt.Color(51, 51, 51));
        jTable2.setForeground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String nombre = txtNombre.getText().trim();
        String dpi = txtDPI.getText().trim();
        String nit = txtNIT.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String licencia = txtLicencia.getText().trim();

        // 1. Validación de campos obligatorios
        if (nombre.isEmpty() || dpi.isEmpty() || licencia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos Nombre, DPI y No. Licencia son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validación de longitud máxima (DPI <= 13 dígitos)
        if (dpi.length() > 13) {
            JOptionPane.showMessageDialog(this, "El DPI no puede tener más de 13 dígitos.", "Validación de Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Validación de longitud máxima (NIT <= 9 dígitos)
        if (!nit.isEmpty() && nit.length() > 9) {
            JOptionPane.showMessageDialog(this, "El NIT no puede tener más de 9 dígitos.", "Validación de Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente nuevo = new Cliente();
        nuevo.setNombre(nombre);
        nuevo.setDpi(dpi);
        nuevo.setNit(nit);
        nuevo.setTelefono(telefono);
        nuevo.setCorreo(correo);
        nuevo.setNumLicencia(licencia);

        if (gestor.agregarCliente(nuevo)) {
            JOptionPane.showMessageDialog(this, "¡Cliente registrado exitosamente en la BD!");
            refrescarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar. Verifique si el DPI ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int fila = jTable2.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtDPI.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtNIT.setText(modeloTabla.getValueAt(fila, 3) != null ? modeloTabla.getValueAt(fila, 3).toString() : "");
            txtTelefono.setText(modeloTabla.getValueAt(fila, 4) != null ? modeloTabla.getValueAt(fila, 4).toString() : "");
            txtCorreo.setText(modeloTabla.getValueAt(fila, 5) != null ? modeloTabla.getValueAt(fila, 5).toString() : "");
            txtLicencia.setText(modeloTabla.getValueAt(fila, 6).toString());
            
            txtDPI.setEditable(false); // Bloquear el DPI porque es la llave única de búsqueda
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        String dpi = txtDPI.getText().trim();
        String nit = txtNIT.getText().trim();

        if (dpi.isEmpty() || txtDPI.isEditable()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validación de longitud máxima para el NIT al modificar
        if (!nit.isEmpty() && nit.length() > 9) {
            JOptionPane.showMessageDialog(this, "El NIT no puede tener más de 9 dígitos.", "Validación de Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente c = new Cliente();
        c.setDpi(dpi);
        c.setNombre(txtNombre.getText().trim());
        c.setNit(nit);
        c.setTelefono(txtTelefono.getText().trim());
        c.setCorreo(txtCorreo.getText().trim());
        c.setNumLicencia(txtLicencia.getText().trim());

        if (gestor.actualizarClienteCompleto(c)) {
            JOptionPane.showMessageDialog(this, "¡Cliente modificado correctamente!");
            refrescarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        String dpi = txtDPI.getText().trim();
        if (dpi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al cliente con DPI: " + dpi + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestor.eliminarCliente(dpi)) {
                JOptionPane.showMessageDialog(this, "¡Cliente eliminado de la base de datos!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el cliente (puede tener contratos vinculados).", "Restricción", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VentanaClientes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDPI;
    private javax.swing.JTextField txtLicencia;
    private javax.swing.JTextField txtNIT;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
