/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;
import com.mycompany.sistemagestionalquilervehiculos.Cliente;
import com.mycompany.sistemagestionalquilervehiculos.Vehiculo;
import com.mycompany.sistemagestionalquilervehiculos.Empleado;
import com.mycompany.sistemagestionalquilervehiculos.ContratoAlquiler;
import logica.GestorClientes;
import logica.GestorVehiculos;
import logica.GestorEmpleados;
import logica.GestorContratos;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import conexion.ConexionBD;
/**
 *
 * @author jimmy
 */
public class VentanaContratos extends javax.swing.JFrame {
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaContratos.class.getName());
    
    private GestorContratos gestor = new GestorContratos();
    private GestorClientes gestorClientes = new GestorClientes();
    private GestorVehiculos gestorVehiculos = new GestorVehiculos();
    private GestorEmpleados gestorEmpleados = new GestorEmpleados();
    private DefaultTableModel modeloTabla;
    private int idContratoSeleccionado = -1;
    

    /**
     * Creates new form VentanaContratos
     */
    public VentanaContratos() {
        initComponents();
        setTitle("Módulo de Gestión de Contratos de Alquiler");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        try {
            java.net.URL imgURL = getClass().getResource("/icons/gestor.png");
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
            java.net.URL urlReg = getClass().getResource("/icons/btnRegistrar.png");
            if (urlReg != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(urlReg);
                btnContrato.setIcon(new javax.swing.ImageIcon(iconoOriginal.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
                btnContrato.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnContrato.setIconTextGap(8);
            }
            java.net.URL urlEli = getClass().getResource("/icons/btnEliminar.png");
            if (urlEli != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(urlEli);
                btnEliminar.setIcon(new javax.swing.ImageIcon(iconoOriginal.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
                btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnEliminar.setIconTextGap(8);
            }
            java.net.URL urlLim = getClass().getResource("/icons/btnLimpiar.png");
            if (urlLim != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(urlLim);
                btnLimpiar.setIcon(new javax.swing.ImageIcon(iconoOriginal.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
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

        // Configuración de la tabla con nombres reales en lugar de IDs
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Cliente", "Vehículo", "Empleado", "F. Inicio", "F. Fin", "Días", "Total (Q)", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(modeloTabla);

        txtFechaI.setText(LocalDate.now().toString());
        
        cbxEstado.removeAllItems();
        cbxEstado.addItem("Activo");
        cbxEstado.addItem("Reservado");
        cbxEstado.addItem("Finalizado");
        cbxEstado.addItem("Vencido");

        // Cargar combos con objetos completos
        cargarCombosClientes();
        cargarCombosVehiculos();
        cargarCombosEmpleados();

        refrescarTabla();

        // Listeners para cálculo automático de días y totales en tiempo real
        txtFechaF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calcularTotales();
            }
        });

        txtTarifa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calcularTotales();
            }
        });
    }
    private void cargarCombosClientes() {
        cbxCliente.removeAllItems();
        for (Cliente c : gestorClientes.getListaClientes()) {
            cbxCliente.addItem(c); 
        }
    }

    private void cargarCombosVehiculos() {
        cbxVehiculo.removeAllItems();
        for (Vehiculo v : gestorVehiculos.getListaVehiculos()) {
            // Lógica de negocio: Solo mostrar vehículos disponibles para evitar sobre-alquiler
            if ("Disponible".equalsIgnoreCase(v.getEstado())) {
                cbxVehiculo.addItem(v); 
            }
        }
    }

    private void cargarCombosEmpleados() {
        cbxEmpleado.removeAllItems();
        for (Empleado e : gestorEmpleados.getListaEmpleados()) {
            cbxEmpleado.addItem(e);
        }
    }

    private void refrescarTabla() {
        gestor.cargarTablaContratos(modeloTabla); 
        
        // 2. Actualiza los cuadros estadísticos de arriba (KPIs)
        actualizarEstadisticasDashboard();
        
        // 3. Recarga los combos de vehículos para que aparezcan disponibles de inmediato
        cargarCombosVehiculos(); 
    }
    private void actualizarEstadisticasDashboard() {
        int[] stats = gestor.obtenerEstadisticasContratos();
        lblContratos.setText(String.valueOf(stats[0]));
        lblIngresos.setText(String.format("Q. %,d.00", stats[1]));
        lblFinalizaciones.setText(String.valueOf(stats[2]));
    }

    private void calcularTotales() {
        try {
            String fInicioStr = txtFechaI.getText().trim();
            String fFinStr = txtFechaF.getText().trim();
            String tarifaStr = txtTarifa.getText().trim();

            if (!fInicioStr.isEmpty() && !fFinStr.isEmpty() && !tarifaStr.isEmpty()) {
                LocalDate fechaInicio = LocalDate.parse(fInicioStr);
                LocalDate fechaFin = LocalDate.parse(fFinStr);

                long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
                if (dias < 0) dias = 0;

                lblDias.setText(String.valueOf(dias));

                double tarifa = Double.parseDouble(tarifaStr);
                double total = dias * tarifa;
                lblTotal.setText(String.format("%.2f", total));
            }
        } catch (Exception e) {
            // Silencioso mientras escribe
        }
    }
    

    private void limpiarFormulario() {
        txtFechaI.setText(LocalDate.now().toString());
        txtFechaF.setText("");
        txtTarifa.setText("");
        lblDias.setText("0");
        lblTotal.setText("0.00");
        idContratoSeleccionado = -1;
        jTable1.clearSelection();
        cargarCombosVehiculos(); 
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
        jLabel1 = new javax.swing.JLabel();
        lblContratos = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblIngresos = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblFinalizaciones = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxCliente = new javax.swing.JComboBox<>();
        cbxVehiculo = new javax.swing.JComboBox<>();
        cbxEmpleado = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtFechaI = new javax.swing.JTextField();
        txtFechaF = new javax.swing.JTextField();
        pDias = new javax.swing.JPanel();
        lblDias = new javax.swing.JLabel();
        txtTarifa = new javax.swing.JTextField();
        pTotal = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnContrato = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnRenovar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(169, 99, 19)));

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Contratos Activos");

        lblContratos.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        lblContratos.setForeground(new java.awt.Color(255, 255, 0));
        lblContratos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(169, 99, 19)));

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ingresos Totales (Q)");

        lblIngresos.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        lblIngresos.setForeground(new java.awt.Color(255, 255, 0));
        lblIngresos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(169, 99, 19)));

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Próximas Finalizaciones");

        lblFinalizaciones.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        lblFinalizaciones.setForeground(new java.awt.Color(255, 255, 0));
        lblFinalizaciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel3)
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFinalizaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFinalizaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cliente:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Vehiculo:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Empleado:");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Datos del Cotrato");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxEmpleado, 0, 219, Short.MAX_VALUE)
                            .addComponent(cbxVehiculo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbxEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0)));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Fecha Inicio:");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fecha Fin:");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Días de Alquiler:");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Tarifa Diaria (Q):");

        pDias.setBackground(new java.awt.Color(51, 51, 51));
        pDias.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblDias.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDias.setForeground(new java.awt.Color(255, 204, 0));

        javax.swing.GroupLayout pDiasLayout = new javax.swing.GroupLayout(pDias);
        pDias.setLayout(pDiasLayout);
        pDiasLayout.setHorizontalGroup(
            pDiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDiasLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblDias, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        pDiasLayout.setVerticalGroup(
            pDiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDiasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pTotal.setBackground(new java.awt.Color(51, 51, 51));
        pTotal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 204, 0));

        javax.swing.GroupLayout pTotalLayout = new javax.swing.GroupLayout(pTotal);
        pTotal.setLayout(pTotalLayout);
        pTotalLayout.setHorizontalGroup(
            pTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTotalLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        pTotalLayout.setVerticalGroup(
            pTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTotalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Estado:");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 102));
        jLabel13.setText("Fechas y Totales");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Total (Q)");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pDias, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFechaI, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTarifa))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFechaF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(txtFechaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addGap(39, 39, 39))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pDias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

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

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));

        btnContrato.setBackground(new java.awt.Color(51, 102, 0));
        btnContrato.setForeground(new java.awt.Color(255, 255, 255));
        btnContrato.setText("Nuevo Contrato");
        btnContrato.addActionListener(this::btnContratoActionPerformed);

        btnFinalizar.setBackground(new java.awt.Color(0, 153, 204));
        btnFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        btnFinalizar.setText("Finalizar Alquiler");
        btnFinalizar.addActionListener(this::btnFinalizarActionPerformed);

        btnEliminar.setBackground(new java.awt.Color(204, 0, 0));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar Contrato");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnReporte.setBackground(new java.awt.Color(153, 153, 153));
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setText("Imprimir Reporte");
        btnReporte.addActionListener(this::btnReporteActionPerformed);

        btnRenovar.setBackground(new java.awt.Color(204, 153, 0));
        btnRenovar.setForeground(new java.awt.Color(255, 255, 255));
        btnRenovar.setText("Renovar Contrato");
        btnRenovar.addActionListener(this::btnRenovarActionPerformed);

        btnLimpiar.setBackground(new java.awt.Color(119, 97, 97));
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(btnContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnRenovar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFinalizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnReporte)
                    .addComponent(btnRenovar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnContrato))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContratoActionPerformed
        try {
            Cliente clienteSeleccionado = (Cliente) cbxCliente.getSelectedItem();
            Vehiculo vehiculoSeleccionado = (Vehiculo) cbxVehiculo.getSelectedItem();
            Empleado empleadoSeleccionado = (Empleado) cbxEmpleado.getSelectedItem();

            if (clienteSeleccionado == null || vehiculoSeleccionado == null || empleadoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente, un vehículo y un empleado válidos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!"Disponible".equalsIgnoreCase(vehiculoSeleccionado.getEstado())) {
                JOptionPane.showMessageDialog(this, "El vehículo seleccionado ya se encuentra Ocupado o Reservado. Elija otro.", "Vehículo no disponible", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fechaInicioStr = txtFechaI.getText().trim();
            String fechaFinStr = txtFechaF.getText().trim();
            String tarifaStr = txtTarifa.getText().trim();

            if (fechaInicioStr.isEmpty() || fechaFinStr.isEmpty() || tarifaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete las fechas y la tarifa diaria obligatoriamente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate fInicio = LocalDate.parse(fechaInicioStr);
            LocalDate fFin = LocalDate.parse(fechaFinStr);

            if (fFin.isBefore(fInicio)) {
                JOptionPane.showMessageDialog(this, "La Fecha Fin no puede ser anterior a la Fecha de Inicio.", "Error de Fechas", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long dias = ChronoUnit.DAYS.between(fInicio, fFin);
            if (dias <= 0) dias = 1;

            double tarifa = Double.parseDouble(tarifaStr);
            double montoBase = dias * tarifa;

            ContratoAlquiler contrato = new ContratoAlquiler();
            contrato.setCliente(clienteSeleccionado);
            contrato.setVehiculo(vehiculoSeleccionado);
            contrato.setEmpleado(empleadoSeleccionado);
            contrato.setFechaInicio(java.sql.Date.valueOf(fInicio));
            contrato.setFechaFin(java.sql.Date.valueOf(fFin));
            contrato.setDiasAlquiler((int) dias);
            contrato.setMontoBase(montoBase);
            contrato.setTotal(montoBase);
            contrato.setEstado(cbxEstado.getSelectedItem().toString());

            if (gestor.agregarContrato(contrato)) {
                JOptionPane.showMessageDialog(this, "¡Contrato registrado exitosamente en la Base de Datos!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el contrato en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor verifique que la tarifa diaria sea un valor numérico válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al procesar el contrato: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnContratoActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        if (idContratoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contrato de la tabla para finalizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea finalizar este contrato de alquiler y liberar el vehículo?", "Confirmar Finalización", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestor.cambiarEstadoContrato(idContratoSeleccionado, "Finalizado")) {
                JOptionPane.showMessageDialog(this, "¡Contrato finalizado y vehículo liberado exitosamente!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al finalizar el contrato.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (idContratoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contrato de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este registro de la base de datos?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestor.eliminarContrato(idContratoSeleccionado)) {
                JOptionPane.showMessageDialog(this, "¡Contrato eliminado correctamente!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el contrato.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        if (idContratoSeleccionado == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un contrato de la tabla para generar su comprobante en PDF.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Usaremos JFileChooser para que elijas dónde guardar el PDF
    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    fileChooser.setDialogTitle("Guardar Comprobante de Contrato");
    fileChooser.setSelectedFile(new java.io.File("Contrato_" + idContratoSeleccionado + ".pdf"));
    
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
            documento.add(new com.itextpdf.text.Paragraph("COMPROBANTE OFICIAL DE CONTRATO N°: " + idContratoSeleccionado, fuenteSub));
            documento.add(new com.itextpdf.text.Paragraph("Fecha de emisión: " + java.time.LocalDate.now(), fuenteNormal));
            documento.add(new com.itextpdf.text.Paragraph("------------------------------------------------------------------------------------------------------------------"));
            documento.add(new com.itextpdf.text.Paragraph(" "));

            // Obtener los datos de la fila seleccionada en la JTable para ponerlos en el PDF
            int fila = jTable1.getSelectedRow();
            if (fila >= 0) {
                String cliente = modeloTabla.getValueAt(fila, 1).toString();
                String vehiculo = modeloTabla.getValueAt(fila, 2).toString();
                String empleado = modeloTabla.getValueAt(fila, 3).toString();
                String fInicio = modeloTabla.getValueAt(fila, 4).toString();
                String fFin = modeloTabla.getValueAt(fila, 5).toString();
                String dias = modeloTabla.getValueAt(fila, 6).toString();
                String total = modeloTabla.getValueAt(fila, 7).toString();
                String estado = modeloTabla.getValueAt(fila, 8).toString();

                documento.add(new com.itextpdf.text.Paragraph("Cliente: " + cliente, fuenteNormal));
                documento.add(new com.itextpdf.text.Paragraph("Vehículo Asignado: " + vehiculo, fuenteNormal));
                documento.add(new com.itextpdf.text.Paragraph("Atendido por Empleado: " + empleado, fuenteNormal));
                documento.add(new com.itextpdf.text.Paragraph("Fecha de Inicio: " + fInicio, fuenteNormal));
                documento.add(new com.itextpdf.text.Paragraph("Fecha de Finalización: " + fFin, fuenteNormal));
                documento.add(new com.itextpdf.text.Paragraph("Días de Alquiler: " + dias, fuenteNormal));
                documento.add(new com.itextpdf.text.Paragraph("Monto Total: Q. " + total, fuenteSub));
                documento.add(new com.itextpdf.text.Paragraph("Estado del Contrato: " + estado, fuenteNormal));
            }

            documento.add(new com.itextpdf.text.Paragraph(" "));
            documento.add(new com.itextpdf.text.Paragraph("------------------------------------------------------------------------------------------------------------------"));
            documento.add(new com.itextpdf.text.Paragraph("¡Gracias por preferir nuestros servicios de alquiler de vehículos!", fuenteNormal));

            documento.close();

            JOptionPane.showMessageDialog(this, "¡PDF generado exitosamente en:\n" + archivoDestino.getAbsolutePath(), "Reporte Creado", JOptionPane.INFORMATION_MESSAGE);
            
            // Opcional: Abrir el PDF automáticamente al crearlo
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(archivoDestino);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnRenovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenovarActionPerformed
        if (idContratoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contrato de la tabla para renovar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 1. Obtener la fecha de inicio actual del contrato seleccionado desde la tabla (columna 4)
            int fila = jTable1.getSelectedRow();
            String fechaInicioStr = modeloTabla.getValueAt(fila, 4).toString();
            LocalDate fInicio = LocalDate.parse(fechaInicioStr);

            // 2. Pedir la nueva fecha fin mediante un cuadro de diálogo flotante
            String nuevaFechaFinStr = JOptionPane.showInputDialog(this, 
                "Fecha de Inicio actual: " + fechaInicioStr + "\nIngrese la NUEVA Fecha Fin (Formato: YYYY-MM-DD):", 
                "Renovar Contrato", JOptionPane.QUESTION_MESSAGE);

            if (nuevaFechaFinStr == null || nuevaFechaFinStr.trim().isEmpty()) {
                return; // Si el usuario cancela o lo deja vacío
            }

            LocalDate fFinNueva = LocalDate.parse(nuevaFechaFinStr.trim());

            if (fFinNueva.isBefore(fInicio) || fFinNueva.isEqual(fInicio)) {
                JOptionPane.showMessageDialog(this, "La nueva fecha de finalización debe ser posterior a la fecha de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Pedir la tarifa diaria para calcular el nuevo total
            String tarifaStr = JOptionPane.showInputDialog(this, 
                "Ingrese la Tarifa Diaria del vehículo para el cálculo:", 
                "Tarifa Diaria", JOptionPane.QUESTION_MESSAGE);

            if (tarifaStr == null || tarifaStr.trim().isEmpty()) {
                return;
            }

            double tarifa = Double.parseDouble(tarifaStr.trim());
            long dias = java.time.temporal.ChronoUnit.DAYS.between(fInicio, fFinNueva);
            if (dias <= 0) dias = 1;

            double nuevoTotal = dias * tarifa;

            // 4. Confirmar y actualizar en la Base de Datos
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Desea extender el contrato hasta el " + fFinNueva + "?\n" +
                "Nuevos días: " + dias + "\n" +
                "Nuevo Total a pagar: Q. " + String.format("%.2f", nuevoTotal), 
                "Confirmar Renovación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                java.sql.Date sqlFechaFin = java.sql.Date.valueOf(fFinNueva);
                if (gestor.renovarContrato(idContratoSeleccionado, sqlFechaFin, (int) dias, nuevoTotal)) {
                    JOptionPane.showMessageDialog(this, "¡Contrato renovado exitosamente!");
                    refrescarTabla();
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el contrato en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use el formato YYYY-MM-DD (Ej: 2026-11-15).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRenovarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            idContratoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        }
    }//GEN-LAST:event_jTable1MouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new VentanaContratos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContrato;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRenovar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox<Cliente> cbxCliente;
    private javax.swing.JComboBox<Empleado> cbxEmpleado;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<Vehiculo> cbxVehiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblContratos;
    private javax.swing.JLabel lblDias;
    private javax.swing.JLabel lblFinalizaciones;
    private javax.swing.JLabel lblIngresos;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pDias;
    private javax.swing.JPanel pTotal;
    private javax.swing.JTextField txtFechaF;
    private javax.swing.JTextField txtFechaI;
    private javax.swing.JTextField txtTarifa;
    // End of variables declaration//GEN-END:variables
}
