/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.mycompany.sistemagestionalquilervehiculos.Vehiculo;
import com.mycompany.sistemagestionalquilervehiculos.CategoriaVehiculo;
import com.mycompany.sistemagestionalquilervehiculos.Sucursal;
import logica.GestorVehiculos;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author jimmy
 */
public class VentanaVehiculos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaVehiculos.class.getName());

    private GestorVehiculos gestor = new GestorVehiculos();
    private DefaultTableModel modeloTabla;
    /**
     * Creates new form VentanaVehiculos
     */
    public VentanaVehiculos() {
        initComponents();
        setTitle("Módulo de Gestión de Vehículos");
        setLocationRelativeTo(null);
        try {
            java.net.URL imgURL = getClass().getResource("/icons/vehiculos.png");
            if (imgURL != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(imgURL);
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
                jLabel1.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jLabel1.setIconTextGap(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        jScrollPane1.getViewport().setBackground(new java.awt.Color(30, 34, 46));
        jTable1.setBackground(new java.awt.Color(30, 34, 46));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setSelectionBackground(new java.awt.Color(51, 153, 0));
        jTable1.setSelectionForeground(new java.awt.Color(255, 255, 255));
        
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(70, 80, 95), 1, true),
            " ", 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", 1, 12),
            new java.awt.Color(255, 255, 153)
        ));
        
        
        
        // Configurar las columnas reales de la tabla según tu Base de Datos
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Marca", "Modelo", "Placa", "VIN", "Año", "Kilometraje", "Tarifa Q.", "Estado", "Cat.", "Suc."}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar edición directa en celdas de la JTable
            }
        };
        jTable1.setModel(modeloTabla);
        
        // Cargar los registros existentes desde PostgreSQL al iniciar la ventana
        refrescarTabla();
    }

    private void refrescarTabla() {
        gestor.cargarTablaVehiculos(modeloTabla);
    }
    
    private void limpiarFormulario() {
        txtMarca.setText("");
        txtModelo.setText("");
        txtPlaca.setText("");
        txtVIN.setText("");
        txtAnio.setText("");
        jTextField6.setText(""); // Kilometraje
        txtTarifa.setText("");
        txtSucursal.setText("");
        cbmEstado.setSelectedIndex(0);
        cmbCategoria.setSelectedIndex(0);
        txtPlaca.setEditable(true); // Permitir ingresar placa nueva
        jTable1.clearSelection();
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
        txtKilometraje = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        txtPlaca = new javax.swing.JTextField();
        txtVIN = new javax.swing.JTextField();
        txtAnio = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        txtTarifa = new javax.swing.JTextField();
        txtSucursal = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        cbmEstado = new javax.swing.JComboBox<>();
        cmbCategoria = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));

        jLabel1.setFont(new java.awt.Font("Bauhaus 93", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 0));
        jLabel1.setText("MÓDULO DE GESTION DE VEHICULOS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(254, 254, 254))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(30, 34, 46));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 51));
        jLabel2.setText("Datos del Vehículo");

        jLabel3.setForeground(new java.awt.Color(164, 164, 164));
        jLabel3.setText("Marca:");

        jLabel4.setForeground(new java.awt.Color(164, 164, 164));
        jLabel4.setText("Modelo:");

        jLabel5.setForeground(new java.awt.Color(164, 164, 164));
        jLabel5.setText("VIN:");

        jLabel6.setForeground(new java.awt.Color(164, 164, 164));
        jLabel6.setText("Placa:");

        jLabel7.setForeground(new java.awt.Color(164, 164, 164));
        jLabel7.setText("Año:");

        txtKilometraje.setForeground(new java.awt.Color(164, 164, 164));
        txtKilometraje.setText("Kilometraje:");

        jLabel9.setForeground(new java.awt.Color(164, 164, 164));
        jLabel9.setText("Tarifa Diaria Q:");

        jLabel10.setForeground(new java.awt.Color(164, 164, 164));
        jLabel10.setText("Estado:");

        jLabel11.setForeground(new java.awt.Color(164, 164, 164));
        jLabel11.setText("Sucursal:");

        jLabel12.setForeground(new java.awt.Color(164, 164, 164));
        jLabel12.setText("Categoria:");

        txtMarca.setBackground(new java.awt.Color(51, 51, 51));
        txtMarca.setForeground(new java.awt.Color(255, 255, 255));

        txtModelo.setBackground(new java.awt.Color(51, 51, 51));
        txtModelo.setForeground(new java.awt.Color(255, 255, 255));

        txtPlaca.setBackground(new java.awt.Color(51, 51, 51));
        txtPlaca.setForeground(new java.awt.Color(255, 255, 255));

        txtVIN.setBackground(new java.awt.Color(51, 51, 51));
        txtVIN.setForeground(new java.awt.Color(255, 255, 255));

        txtAnio.setBackground(new java.awt.Color(51, 51, 51));
        txtAnio.setForeground(new java.awt.Color(255, 255, 255));

        jTextField6.setBackground(new java.awt.Color(51, 51, 51));
        jTextField6.setForeground(new java.awt.Color(255, 255, 255));

        txtTarifa.setBackground(new java.awt.Color(51, 51, 51));
        txtTarifa.setForeground(new java.awt.Color(255, 255, 255));

        txtSucursal.setBackground(new java.awt.Color(51, 51, 51));
        txtSucursal.setForeground(new java.awt.Color(255, 255, 255));

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

        cbmEstado.setBackground(new java.awt.Color(51, 51, 51));
        cbmEstado.setForeground(new java.awt.Color(255, 255, 255));
        cbmEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "Alquilado", "En Mantenimiento" }));

        cmbCategoria.setBackground(new java.awt.Color(51, 51, 51));
        cmbCategoria.setForeground(new java.awt.Color(255, 255, 255));
        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SUV", "sedan", "Pick-Up", "Deportivo", " " }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(412, 412, 412)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMarca)
                    .addComponent(txtModelo, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(txtVIN)
                    .addComponent(txtAnio))
                .addGap(78, 78, 78)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtKilometraje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(21, 21, 21)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField6)
                    .addComponent(txtTarifa)
                    .addComponent(txtSucursal)
                    .addComponent(cbmEstado, 0, 210, Short.MAX_VALUE)
                    .addComponent(cmbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(107, 107, 107)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 72, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtKilometraje)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar)
                    .addComponent(cbmEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel12)
                    .addComponent(txtVIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar)
                    .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11)
                    .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));

        jTable1.setBackground(new java.awt.Color(51, 51, 51));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try {
            String marca = txtMarca.getText().trim();
            String modelo = txtModelo.getText().trim();
            String placa = txtPlaca.getText().trim();
            String vin = txtVIN.getText().trim();
            
            if (marca.isEmpty() || modelo.isEmpty() || placa.isEmpty() || vin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos Marca, Modelo, Placa y VIN son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int anio = Integer.parseInt(txtAnio.getText().trim());
            double kilometraje = Double.parseDouble(jTextField6.getText().trim());
            double tarifa = Double.parseDouble(txtTarifa.getText().trim());
            int idSucursal = Integer.parseInt(txtSucursal.getText().trim());
            
            String estado = cbmEstado.getSelectedItem().toString();
            int idCategoria = cmbCategoria.getSelectedIndex() + 1; // ID basado en la posición del ComboBox

            Vehiculo v = new Vehiculo();
            v.setMarca(marca);
            v.setModelo(modelo);
            v.setPlaca(placa);
            v.setVin(vin);
            v.setAnio(anio);
            v.setKilometraje(kilometraje);
            v.setTarifaDiaria(tarifa);
            v.setEstado(estado);
            
            CategoriaVehiculo cat = new CategoriaVehiculo();
            cat.setIdCategoria(idCategoria);
            v.setCategoria(cat);
            
            Sucursal suc = new Sucursal();
            suc.setIdSucursal(idSucursal);
            v.setSucursal(suc);

            if (gestor.agregarVehiculo(v)) {
                JOptionPane.showMessageDialog(this, "¡Vehículo registrado exitosamente en la BD!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar. Verifique si la placa o VIN ya existen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor verifique que los campos numéricos (Año, Kilometraje, Tarifa, Sucursal) sean válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            txtMarca.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtModelo.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtPlaca.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtVIN.setText(modeloTabla.getValueAt(fila, 4) != null ? modeloTabla.getValueAt(fila, 4).toString() : "");
            txtAnio.setText(modeloTabla.getValueAt(fila, 5).toString());
            jTextField6.setText(modeloTabla.getValueAt(fila, 6).toString()); // Kilometraje
            txtTarifa.setText(modeloTabla.getValueAt(fila, 7).toString());
            cbmEstado.setSelectedItem(modeloTabla.getValueAt(fila, 8).toString());
            
            // Cargar sucursal
            txtSucursal.setText(modeloTabla.getValueAt(fila, 10).toString());
            
            txtPlaca.setEditable(false); // Bloquear placa por ser llave única de búsqueda
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty() || txtPlaca.isEditable()) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo de la tabla para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Vehiculo v = new Vehiculo();
            v.setPlaca(placa);
            v.setMarca(txtMarca.getText().trim());
            v.setModelo(txtModelo.getText().trim());
            v.setVin(txtVIN.getText().trim());
            v.setAnio(Integer.parseInt(txtAnio.getText().trim()));
            v.setKilometraje(Double.parseDouble(jTextField6.getText().trim()));
            v.setTarifaDiaria(Double.parseDouble(txtTarifa.getText().trim()));
            v.setEstado(cbmEstado.getSelectedItem().toString());
            
            CategoriaVehiculo cat = new CategoriaVehiculo();
            cat.setIdCategoria(cmbCategoria.getSelectedIndex() + 1);
            v.setCategoria(cat);
            
            Sucursal suc = new Sucursal();
            suc.setIdSucursal(Integer.parseInt(txtSucursal.getText().trim()));
            v.setSucursal(suc);

            if (gestor.actualizarVehiculoCompleto(v)) {
                JOptionPane.showMessageDialog(this, "¡Vehículo modificado correctamente!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el vehículo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique los campos numéricos antes de modificar.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el vehículo con placa: " + placa + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestor.eliminarVehiculo(placa)) {
                JOptionPane.showMessageDialog(this, "¡Vehículo eliminado de la base de datos!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el vehículo (puede tener contratos vinculados).", "Restricción", JOptionPane.ERROR_MESSAGE);
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
        java.awt.EventQueue.invokeLater(() -> new VentanaVehiculos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cbmEstado;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JLabel txtKilometraje;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtSucursal;
    private javax.swing.JTextField txtTarifa;
    private javax.swing.JTextField txtVIN;
    // End of variables declaration//GEN-END:variables
}
