/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.mycompany.sistemagestionalquilervehiculos.Sucursal;
import com.mycompany.sistemagestionalquilervehiculos.Vehiculo;
import com.mycompany.sistemagestionalquilervehiculos.Empleado;
import logica.GestorMantenimientos;
import logica.GestorSucursales;
import logica.GestorVehiculos;
import logica.GestorEmpleados;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author jimmy
 */
public class VentanaMantenimiento extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaMantenimiento.class.getName());
    private GestorMantenimientos gestor = new GestorMantenimientos();
    private GestorSucursales gestorSucursales = new GestorSucursales();
    private GestorVehiculos gestorVehiculos = new GestorVehiculos();
    private GestorEmpleados gestorEmpleados = new GestorEmpleados();
    
    private DefaultTableModel modeloTabla;
    private int idMantenimientoSeleccionado = -1;

    /**
     * Creates new form VentanaMantenimiento
     */
    public VentanaMantenimiento() {
        initComponents();
        setTitle("Módulo de Gestión de Mantenimiento de Vehículos");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Estética oscura idéntica a tus otras ventanas
        jScrollPane1.getViewport().setBackground(new java.awt.Color(30, 34, 46));
        jTable1.setBackground(new java.awt.Color(30, 34, 46));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setSelectionBackground(new java.awt.Color(51, 153, 0));
        jTable1.setSelectionForeground(new java.awt.Color(255, 255, 255));

        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Sucursal", "Vehículo", "Mecánico", "Servicio", "F. Ingreso", "F. Estimada", "Costo (Q)", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(modeloTabla);

        // Fecha de ingreso por defecto
        txtFinicio.setText(LocalDate.now().toString());

        // Llenar combo de Tipos de Servicio
        cbxServicio.removeAllItems();
        cbxServicio.addItem("Mantenimiento Preventivo");
        cbxServicio.addItem("Cambio de Aceite y Filtros");
        cbxServicio.addItem("Revisión y Cambio de Frenos");
        cbxServicio.addItem("Alineación y Balanceo");
        cbxServicio.addItem("Reparación Sistema Eléctrico");

        // Cargar Sucursales y Vehículos disponibles
        cargarCombosSucursales();
        cargarCombosVehiculosDisponibles();
        
        refrescarTabla();

        // Evento dinámico: Al cambiar de sucursal, filtra los mecánicos correspondientes
        cbxSucursal.addActionListener(e -> cargarMecanicosPorSucursal());

        // Capturar selección de fila en la tabla
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = jTable1.getSelectedRow();
                if (fila >= 0) {
                    idMantenimientoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                }
            }
        });        
    }
    
    private void cargarCombosSucursales() {
        cbxSucursal.removeAllItems();
        for (Sucursal s : gestorSucursales.getListaSucursales()) {
            cbxSucursal.addItem(s.getNombre()); // O addItem(s) si cambias el generador a <Sucursal>
        }
        cargarMecanicosPorSucursal();
    }

    private void cargarCombosVehiculosDisponibles() {
        cbxVehiculo.removeAllItems();
        for (Vehiculo v : gestorVehiculos.getListaVehiculos()) {
            if ("Disponible".equalsIgnoreCase(v.getEstado())) {
                cbxVehiculo.addItem(v.toString()); // Muestra marca, modelo y placa
            }
        }
    }

    private void cargarMecanicosPorSucursal() {
        cbxMecanico.removeAllItems();
        String nombreSucursalSel = (String) cbxSucursal.getSelectedItem();
        if (nombreSucursalSel == null) return;

        // Buscar ID de la sucursal seleccionada por nombre
        int idSucursalSel = -1;
        for (Sucursal s : gestorSucursales.getListaSucursales()) {
            if (s.getNombre().equals(nombreSucursalSel)) {
                idSucursalSel = s.getIdSucursal();
                break;
            }
        }

        for (Empleado e : gestorEmpleados.getListaEmpleados()) {
            if (e.getRol() != null && e.getRol().toLowerCase().contains("mecanico") && e.getIdSucursal() == idSucursalSel) {
                cbxMecanico.addItem(e.getNombre());
            }
        }
    }

    private void refrescarTabla() {
        gestor.cargarTablaMantenimientos(modeloTabla);
        // Actualiza tu etiqueta lblTotal con el conteo actual de registros en la tabla
        lblTotal.setText(String.valueOf(modeloTabla.getRowCount()));
        cargarCombosVehiculosDisponibles();
    }
    private void limpiarFormulario() {
        txtFinicio.setText(LocalDate.now().toString());
        txtFestimado.setText("");
        txtCosto.setText("");
        idMantenimientoSeleccionado = -1;
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
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnFinalizar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cbxSucursal = new javax.swing.JComboBox<>();
        cbxVehiculo = new javax.swing.JComboBox<>();
        cbxMecanico = new javax.swing.JComboBox<>();
        cbxServicio = new javax.swing.JComboBox<>();
        txtFinicio = new javax.swing.JTextField();
        txtFestimado = new javax.swing.JTextField();
        txtCosto = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btnGenerar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel3.setBackground(new java.awt.Color(43, 43, 43));

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("LISTADO DE MANTENIMIENTOS");

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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(43, 43, 43));

        btnFinalizar.setBackground(new java.awt.Color(153, 0, 0));
        btnFinalizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        btnFinalizar.setText("Finalizar Servicio");
        btnFinalizar.addActionListener(this::btnFinalizarActionPerformed);

        btnReporte.setBackground(new java.awt.Color(102, 102, 102));
        btnReporte.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setText("Imprimir Reporte");
        btnReporte.addActionListener(this::btnReporteActionPerformed);

        btnActualizar.setBackground(new java.awt.Color(0, 102, 0));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar Estado");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        jPanel2.setBackground(new java.awt.Color(43, 43, 43));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total Registros:");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 0));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("8");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(5, 5, 5)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(43, 43, 43));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Sucursal:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Vehículo:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Mécanico Asignado:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Tipo de Servicio:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Fecha de Ingreso:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Fecha Estimada:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Costo Estimado:");

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 102, 0));
        jLabel11.setText("REGISTRO DE MANTENIMIENTO");

        jSeparator1.setBackground(new java.awt.Color(204, 102, 0));
        jSeparator1.setForeground(new java.awt.Color(204, 102, 0));

        jSeparator2.setBackground(new java.awt.Color(204, 102, 0));
        jSeparator2.setForeground(new java.awt.Color(204, 102, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnGenerar.setBackground(new java.awt.Color(0, 102, 0));
        btnGenerar.setFont(new java.awt.Font("Bauhaus 93", 1, 14)); // NOI18N
        btnGenerar.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerar.setText("Generar Cita");
        btnGenerar.addActionListener(this::btnGenerarActionPerformed);

        jSeparator3.setBackground(new java.awt.Color(204, 102, 0));
        jSeparator3.setForeground(new java.awt.Color(204, 102, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(154, 154, 154)
                                    .addComponent(cbxMecanico, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(202, 202, 202)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGap(154, 154, 154)
                                            .addComponent(cbxSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(37, 37, 37)
                                            .addComponent(cbxServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(75, 75, 75)
                                        .addComponent(cbxVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(32, 32, 32)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtFestimado))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jSeparator3)
                .addGap(18, 18, 18)
                .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txtFinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txtFestimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel12))
                                    .addComponent(cbxSucursal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(6, 6, 6)))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(cbxMecanico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cbxServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        try {
            String sucursalNombre = (String) cbxSucursal.getSelectedItem();
            String vehiculoStr = (String) cbxVehiculo.getSelectedItem();
            String mecanicoNombre = (String) cbxMecanico.getSelectedItem();
            String tipoServicio = (String) cbxServicio.getSelectedItem();
            
            String fIngresoStr = txtFinicio.getText().trim();
            String fEstimadaStr = txtFestimado.getText().trim();
            String costoStr = txtCosto.getText().trim();

            if (sucursalNombre == null || vehiculoStr == null || mecanicoNombre == null || fIngresoStr.isEmpty() || fEstimadaStr.isEmpty() || costoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Obtener IDs reales buscando por nombre / placa
            int idSucursal = 0, idVehiculo = 0, idMecanico = 0;
            for (Sucursal s : gestorSucursales.getListaSucursales()) {
                if (s.getNombre().equals(sucursalNombre)) { idSucursal = s.getIdSucursal(); break; }
            }
            for (Vehiculo v : gestorVehiculos.getListaVehiculos()) {
                if (v.toString().equals(vehiculoStr)) { idVehiculo = v.getIdVehiculo(); break; }
            }
            for (Empleado e : gestorEmpleados.getListaEmpleados()) {
                if (e.getNombre().equals(mecanicoNombre)) { idMecanico = e.getIdEmpleado(); break; }
            }

            java.util.Date fIngreso = java.sql.Date.valueOf(LocalDate.parse(fIngresoStr));
            java.util.Date fEstimada = java.sql.Date.valueOf(LocalDate.parse(fEstimadaStr));
            double costo = Double.parseDouble(costoStr);

            if (gestor.agregarMantenimiento(idSucursal, idVehiculo, idMecanico, tipoServicio, fIngreso, fEstimada, costo, "Ingresado")) {
                JOptionPane.showMessageDialog(this, "¡Cita de mantenimiento generada y vehículo puesto en taller!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el mantenimiento en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Verifique el formato de fechas (YYYY-MM-DD) y que el costo sea numérico.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        if (idMantenimientoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un mantenimiento de la tabla para finalizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea finalizar este servicio y liberar el vehículo a estado Disponible?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Extraer el id del vehículo de la fila seleccionada (puedes ajustarlo según el orden de columnas de tu modelo)
            int fila = jTable1.getSelectedRow();
            // Lógica para finalizar y liberar vehículo en BD...
            refrescarTabla();
            limpiarFormulario();
        }
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        if (idMantenimientoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de mantenimiento de la tabla para generar su comprobante en PDF.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Usaremos JFileChooser para que elijas dónde guardar el PDF
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Guardar Comprobante de Mantenimiento");
        fileChooser.setSelectedFile(new java.io.File("Mantenimiento_" + idMantenimientoSeleccionado + ".pdf"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivoDestino = fileChooser.getSelectedFile();
            
            try {
                // Creación del documento PDF con iText
                com.itextpdf.text.Document documento = new com.itextpdf.text.Document();
                com.itextpdf.text.pdf.PdfWriter.getInstance(documento, new java.io.FileOutputStream(archivoDestino));
                documento.open();

                // Fuentes y Estilos
                com.itextpdf.text.Font fuenteTitulo = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 18, com.itextpdf.text.BaseColor.DARK_GRAY);
                com.itextpdf.text.Font fuenteSub = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 12, com.itextpdf.text.BaseColor.BLACK);
                com.itextpdf.text.Font fuenteNormal = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA, 10, com.itextpdf.text.BaseColor.BLACK);

                documento.add(new com.itextpdf.text.Paragraph("SISTEMA DE GESTIÓN DE ALQUILER DE VEHÍCULOS", fuenteTitulo));
                documento.add(new com.itextpdf.text.Paragraph("COMPROBANTE OFICIAL DE MANTENIMIENTO N°: " + idMantenimientoSeleccionado, fuenteSub));
                documento.add(new com.itextpdf.text.Paragraph("Fecha de emisión: " + java.time.LocalDate.now(), fuenteNormal));
                documento.add(new com.itextpdf.text.Paragraph("------------------------------------------------------------------------------------------------------------------"));
                documento.add(new com.itextpdf.text.Paragraph(" "));

                // Obtener los datos de la fila seleccionada en la JTable de mantenimientos
                int fila = jTable1.getSelectedRow();
                if (fila >= 0) {
                    String sucursal = modeloTabla.getValueAt(fila, 1).toString();
                    String vehiculo = modeloTabla.getValueAt(fila, 2).toString();
                    String mecanico = modeloTabla.getValueAt(fila, 3).toString();
                    String servicio = modeloTabla.getValueAt(fila, 4).toString();
                    String fIngreso = modeloTabla.getValueAt(fila, 5).toString();
                    String fEstimada = modeloTabla.getValueAt(fila, 6).toString();
                    String costo = modeloTabla.getValueAt(fila, 7).toString();
                    String estado = modeloTabla.getValueAt(fila, 8).toString();

                    documento.add(new com.itextpdf.text.Paragraph("Sucursal: " + sucursal, fuenteNormal));
                    documento.add(new com.itextpdf.text.Paragraph("Vehículo en Taller: " + vehiculo, fuenteNormal));
                    documento.add(new com.itextpdf.text.Paragraph("Mecánico Asignado: " + mecanico, fuenteNormal));
                    documento.add(new com.itextpdf.text.Paragraph("Tipo de Servicio: " + servicio, fuenteNormal));
                    documento.add(new com.itextpdf.text.Paragraph("Fecha de Ingreso: " + fIngreso, fuenteNormal));
                    documento.add(new com.itextpdf.text.Paragraph("Fecha Estimada de Salida: " + fEstimada, fuenteNormal));
                    documento.add(new com.itextpdf.text.Paragraph("Costo Estimado: " + costo, fuenteSub));
                    documento.add(new com.itextpdf.text.Paragraph("Estado del Servicio: " + estado, fuenteNormal));
                }

                documento.add(new com.itextpdf.text.Paragraph(" "));
                documento.add(new com.itextpdf.text.Paragraph("------------------------------------------------------------------------------------------------------------------"));
                documento.add(new com.itextpdf.text.Paragraph("Reporte oficial de control de taller y mantenimiento vehicular.", fuenteNormal));

                documento.close();

                JOptionPane.showMessageDialog(this, "¡PDF generado exitosamente en:\n" + archivoDestino.getAbsolutePath(), "Reporte Creado", JOptionPane.INFORMATION_MESSAGE);
                
                // Abrir el PDF automáticamente al crearlo
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().open(archivoDestino);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (idMantenimientoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un mantenimiento de la tabla para actualizar su estado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] estados = {"Ingresado", "En Proceso", "Programado", "Esperando Piezas", "Finalizado"};
        String nuevoEstado = (String) JOptionPane.showInputDialog(this, "Seleccione el nuevo estado:", "Actualizar Estado", JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);

        if (nuevoEstado != null) {
            if (gestor.actualizarEstadoMantenimiento(idMantenimientoSeleccionado, nuevoEstado)) {
                JOptionPane.showMessageDialog(this, "¡Estado actualizado correctamente!");
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new VentanaMantenimiento().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox<String> cbxMecanico;
    private javax.swing.JComboBox<String> cbxServicio;
    private javax.swing.JComboBox<String> cbxSucursal;
    private javax.swing.JComboBox<String> cbxVehiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtFestimado;
    private javax.swing.JTextField txtFinicio;
    // End of variables declaration//GEN-END:variables
}
