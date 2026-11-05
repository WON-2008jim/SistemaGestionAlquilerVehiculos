/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.mycompany.sistemagestionalquilervehiculos.Empleado;
import com.mycompany.sistemagestionalquilervehiculos.Sucursal;
import logica.GestorEmpleados;
import logica.GestorSucursales;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author jimmy
 */
public class VentanaEmpleados extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaEmpleados.class.getName());
    private GestorEmpleados gestor = new GestorEmpleados();
    private GestorSucursales gestorSucursales = new GestorSucursales();
    private DefaultTableModel modeloTabla;
    private int idEmpleadoSeleccionado = -1;

    /**
     * Creates new form VentanaEmpleados
     */
    public VentanaEmpleados() {
        initComponents();
        setTitle("Módulo de Gestión de Empleados");
        setLocationRelativeTo(null);
        
        // Configurar el modelo de la tabla con las columnas correctas
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Rol", "Teléfono", "Usuario", "ID Sucursal"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar edición directa en celdas
            }
        };
        jTable1.setModel(modeloTabla);
        
        // Cargar datos iniciales desde la Base de Datos
        cargarComboSucursales();
        refrescarTabla();
        try {
            java.net.URL imgURL = getClass().getResource("/icons/empleado.png");
            if (imgURL != null) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(imgURL);
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
                jLabel8.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                jLabel8.setIconTextGap(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(70, 80, 95), 1, true),
            " Datos del Empleado ", 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", 1, 12),
            new java.awt.Color(255, 255, 153)
        ));
        try {
            // Registrar
            java.net.URL urlReg = getClass().getResource("/icons/btnRegistrar.png");
            if (urlReg != null) {
                java.awt.Image imgReg = new javax.swing.ImageIcon(urlReg).getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
                btnRegistrar.setIcon(new javax.swing.ImageIcon(imgReg));
                btnRegistrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnRegistrar.setIconTextGap(8);
            }

            // Modificar
            java.net.URL urlMod = getClass().getResource("/icons/btnModificar.png");
            if (urlMod != null) {
                java.awt.Image imgMod = new javax.swing.ImageIcon(urlMod).getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
                btnModificar.setIcon(new javax.swing.ImageIcon(imgMod));
                btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnModificar.setIconTextGap(8);
            }

            // Eliminar
            java.net.URL urlEli = getClass().getResource("/icons/btnEliminar.png");
            if (urlEli != null) {
                java.awt.Image imgEli = new javax.swing.ImageIcon(urlEli).getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
                btnEliminar.setIcon(new javax.swing.ImageIcon(imgEli));
                btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnEliminar.setIconTextGap(8);
            }

            // Limpiar
            java.net.URL urlLim = getClass().getResource("/icons/btnLimpiar.png");
            if (urlLim != null) {
                java.awt.Image imgLim = new javax.swing.ImageIcon(urlLim).getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
                btnLimpiar.setIcon(new javax.swing.ImageIcon(imgLim));
                btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                btnLimpiar.setIconTextGap(8);
            }

        } catch (Exception e) {
            System.err.println("Error al cargar iconos en botones de empleados: " + e.getMessage());
        }
        java.awt.Color colorFondoTabla = new java.awt.Color(30, 34, 46); // Fondo general oscuro
        java.awt.Color colorFondoFilas = new java.awt.Color(40, 45, 60);  // Un gris un poco más visible para las filas de datos
        
        jPanel2.setBackground(colorFondoTabla);
        jPanel5.setBackground(colorFondoTabla);
        jScrollPane1.getViewport().setBackground(colorFondoTabla);
        
        jTable1.setBackground(colorFondoFilas);
        jTable1.setForeground(new java.awt.Color(255, 255, 255)); // Texto blanco en filas
        jTable1.setGridColor(new java.awt.Color(60, 70, 85));       // Líneas de la tabla sutiles y oscuras
        jTable1.setSelectionBackground(new java.awt.Color(51, 153, 0)); // Verde esmeralda al seleccionar
        jTable1.setSelectionForeground(new java.awt.Color(255, 255, 255));
        
        // Ajustar también el color del encabezado de la tabla para que no se vea plateado/claro
        jTable1.getTableHeader().setBackground(new java.awt.Color(20, 24, 33));
        jTable1.getTableHeader().setForeground(new java.awt.Color(255, 255, 255));
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }
    private void cargarComboSucursales() {
        cmbSucursal.removeAllItems();
        List<Sucursal> listaSucursales = gestorSucursales.getListaSucursales();
        for (Sucursal s : listaSucursales) {
            cmbSucursal.addItem(s); // Muestra el nombre gracias al toString() de la clase Sucursal
        }
    }

    private void refrescarTabla() {
        gestor.cargarTablaEmpleados(modeloTabla);
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtRol.setText("");
        txtTelefono.setText("");
        txtUser.setText("");
        txtContra.setText("");
        lblID.setText("");
        if (cmbSucursal.getItemCount() > 0) {
            cmbSucursal.setSelectedIndex(0);
        }
        idEmpleadoSeleccionado = -1;
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtRol = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
        txtContra = new javax.swing.JTextField();
        cmbSucursal = new javax.swing.JComboBox<>();
        lblID = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnTatras = new javax.swing.JButton();
        btnTadelante = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtPag = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        btnTinicio = new javax.swing.JButton();
        btnTfinal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modulo de Gestion de Empleados");

        jPanel1.setBackground(new java.awt.Color(30, 34, 46));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel3.setBackground(new java.awt.Color(30, 34, 46));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Empleado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(164, 164, 164))); // NOI18N

        jLabel1.setForeground(new java.awt.Color(164, 164, 164));
        jLabel1.setText("Nombre:");

        jLabel2.setForeground(new java.awt.Color(164, 164, 164));
        jLabel2.setText("Rol:");

        jLabel3.setForeground(new java.awt.Color(164, 164, 164));
        jLabel3.setText("Teléfono:");

        jLabel4.setForeground(new java.awt.Color(164, 164, 164));
        jLabel4.setText("Sucursal Asignada:");

        jLabel5.setForeground(new java.awt.Color(164, 164, 164));
        jLabel5.setText("Usuario:");

        jLabel6.setForeground(new java.awt.Color(164, 164, 164));
        jLabel6.setText("Contraseña:");

        jLabel7.setForeground(new java.awt.Color(164, 164, 164));
        jLabel7.setText("ID Asignado:");

        txtNombre.setBackground(new java.awt.Color(51, 51, 51));

        txtRol.setBackground(new java.awt.Color(51, 51, 51));

        txtTelefono.setBackground(new java.awt.Color(51, 51, 51));

        txtUser.setBackground(new java.awt.Color(51, 51, 51));

        txtContra.setBackground(new java.awt.Color(51, 51, 51));

        cmbSucursal.setBackground(new java.awt.Color(51, 51, 51));

        lblID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(30, 34, 46));

        btnLimpiar.setBackground(new java.awt.Color(102, 102, 102));
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnEliminar.setBackground(new java.awt.Color(255, 0, 0));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnModificar.setBackground(new java.awt.Color(0, 153, 204));
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);

        btnRegistrar.setBackground(new java.awt.Color(51, 102, 0));
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(this::btnRegistrarActionPerformed);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar)
                    .addComponent(btnEliminar)
                    .addComponent(btnModificar)
                    .addComponent(btnRegistrar))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(70, 70, 70)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbSucursal, 0, 252, Short.MAX_VALUE)
                                            .addComponent(txtContra))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtRol, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 0));
        jLabel8.setText("REGISTRO DEL EMPLEADO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel2.setBackground(new java.awt.Color(34, 34, 57));

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

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 0));
        jLabel17.setText("LISTADO DE EMPLEADOS");

        jPanel5.setBackground(new java.awt.Color(34, 34, 57));

        btnTatras.setText("<");

        btnTadelante.setText(">");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Página");

        txtPag.setBackground(new java.awt.Color(51, 51, 51));

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("de 5");

        btnTinicio.setText("<<");

        btnTfinal.setText(">>");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(btnTinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnTatras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(txtPag, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTadelante, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnTfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTatras)
                    .addComponent(btnTadelante)
                    .addComponent(jLabel18)
                    .addComponent(txtPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(btnTinicio)
                    .addComponent(btnTfinal))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String nombre = txtNombre.getText().trim();
        String rol = txtRol.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String usuario = txtUser.getText().trim();
        String clave = txtContra.getText().trim();

        if (nombre.isEmpty() || rol.isEmpty() || telefono.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sucursal sucursalSeleccionada = (Sucursal) cmbSucursal.getSelectedItem();
        if (sucursalSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una sucursal válida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Empleado e = new Empleado();
        e.setNombre(nombre);
        e.setRol(rol);
        e.setTelefono(telefono);
        e.setUsuario(usuario);
        e.setClave(clave);
        e.setIdSucursal(sucursalSeleccionada.getIdSucursal());

        if (gestor.agregarEmpleado(e)) {
            JOptionPane.showMessageDialog(this, "¡Empleado registrado con éxito en la BD!");
            refrescarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el empleado (el usuario podría estar duplicado).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int fila = jTable1.getSelectedRow();
        if (fila >= 0) {
            idEmpleadoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            lblID.setText(String.valueOf(idEmpleadoSeleccionado));
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtRol.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtUser.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtContra.setText(modeloTabla.getValueAt(fila, 5).toString());
            
            // Buscar y seleccionar la sucursal correspondiente en el ComboBox
            int idSucursalFila = Integer.parseInt(modeloTabla.getValueAt(fila, 6).toString());
            for (int i = 0; i < cmbSucursal.getItemCount(); i++) {
                Sucursal s = cmbSucursal.getItemAt(i);
                if (s.getIdSucursal() == idSucursalFila) {
                    cmbSucursal.setSelectedIndex(i);
                    break;
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (idEmpleadoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText().trim();
        String rol = txtRol.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String usuario = txtUser.getText().trim();
        String clave = txtContra.getText().trim();

        if (nombre.isEmpty() || rol.isEmpty() || telefono.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sucursal sucursalSeleccionada = (Sucursal) cmbSucursal.getSelectedItem();

        Empleado e = new Empleado();
        e.setIdEmpleado(idEmpleadoSeleccionado);
        e.setNombre(nombre);
        e.setRol(rol);
        e.setTelefono(telefono);
        e.setUsuario(usuario);
        e.setClave(clave);
        e.setIdSucursal(sucursalSeleccionada.getIdSucursal());

        if (gestor.actualizarEmpleado(e)) {
            JOptionPane.showMessageDialog(this, "¡Empleado modificado correctamente!");
            refrescarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (idEmpleadoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este empleado?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestor.eliminarEmpleado(idEmpleadoSeleccionado)) {
                JOptionPane.showMessageDialog(this, "¡Empleado eliminado de la base de datos!");
                refrescarTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar (puede tener contratos de alquiler asociados).", "Restricción", JOptionPane.ERROR_MESSAGE);
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
        java.awt.EventQueue.invokeLater(() -> new VentanaEmpleados().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnTadelante;
    private javax.swing.JButton btnTatras;
    private javax.swing.JButton btnTfinal;
    private javax.swing.JButton btnTinicio;
    private javax.swing.JComboBox<Sucursal> cmbSucursal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblID;
    private javax.swing.JTextField txtContra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPag;
    private javax.swing.JTextField txtRol;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
