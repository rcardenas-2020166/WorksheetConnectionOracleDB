package edu.gt.rodrigocardenas.umg.gui.frames;

import edu.gt.rodrigocardenas.umg.models.Product;
import edu.gt.rodrigocardenas.umg.utils.ProductManager;
import edu.gt.rodrigocardenas.umg.utils.ProductTableModel;
import edu.gt.rodrigocardenas.umg.utils.UIConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * Frame principal para la gestión de productos con Oracle Database
 * @author Rodrigo Gerardo Cárdenas Monroy
 */
public class ProductFrame extends JFrame {

    // Componentes principales
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel formPanel;
    private JPanel tablePanel;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    
    // Campos del formulario
    private JTextField idField;
    private JTextField nombreField;
    private JTextField precioField;
    private JComboBox<String> estadoComboBox;
    
    // Campo de búsqueda
    private JTextField searchField;
    
    // Labels de error
    private JLabel idErrorLabel;
    private JLabel nombreErrorLabel;
    private JLabel precioErrorLabel;
    private JLabel estadoErrorLabel;
    
    // Botones
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    private JButton refrescarButton;
    
    // Tabla
    private JTable productosTable;
    private ProductTableModel tableModel;
    private JScrollPane tableScrollPane;
    
    // Estado de la aplicación
    private boolean modoEdicion = false;
    private Product productoSeleccionado = null;

    /**
     * Constructor principal
     */
    public ProductFrame() {
        initComponents();
        setupModernInterface();
    }

    /**
     * Inicializa los componentes básicos
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(UIConstants.APP_TITLE);
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(UIConstants.MIN_FRAME_WIDTH, UIConstants.MIN_FRAME_HEIGHT));
    }

    /**
     * Configura la interfaz moderna
     */
    private void setupModernInterface() {
        // Crear componentes modernos
        createModernComponents();
        layoutModernComponents();
        setupEventHandlers();
        setupTable();
        
        // Cargar datos iniciales
        refrescarTabla();
        
        // Hacer el frame visible
        setVisible(true);
    }
    
    /**
     * Crea todos los componentes de la interfaz
     */
    private void createModernComponents() {
        // Panel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 
                                          UIConstants.PADDING, UIConstants.PADDING));
        
        // Panel del encabezado
        headerPanel = createHeaderPanel();
        
        // Panel de contenido
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
        contentPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        
        // Panel del formulario
        formPanel = createFormPanel();
        
        // Panel de búsqueda
        searchPanel = createSearchPanel();
        
        // Panel de la tabla
        tablePanel = createTablePanel();
        
        // Panel de botones
        buttonPanel = createButtonPanel();
    }
    
    /**
     * Crea el panel del encabezado
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(UIConstants.PRIMARY_COLOR);
        panel.setBorder(new EmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 
                                      UIConstants.PADDING, UIConstants.PADDING));
        
        // Cargar y mostrar el logo de la UMG
        try {
            ImageIcon logoIcon = new ImageIcon("assets/logo_umg.png");
            Image logoImage = logoIcon.getImage();
            // Redimensionar la imagen para que sea apropiada para el header
            Image resizedLogo = logoImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedLogo);
            
            JLabel logoLabel = new JLabel(resizedIcon);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(logoLabel);
        } catch (Exception e) {
            System.err.println("Error al cargar el logo: " + e.getMessage());
        }
        
        JLabel titleLabel = new JLabel("Sistema de Gestión de Productos con Oracle");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel);
        return panel;
    }
    
    /**
     * Crea el panel del formulario
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR),
            new EmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 
                          UIConstants.PADDING, UIConstants.PADDING)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título del formulario
        JLabel formTitle = new JLabel("Gestión de Productos");
        formTitle.setFont(UIConstants.SUBTITLE_FONT);
        formTitle.setForeground(UIConstants.PRIMARY_COLOR);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(formTitle, gbc);
        
        // Campo ID
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        JLabel idLabel = new JLabel(UIConstants.ID_LABEL);
        idLabel.setFont(UIConstants.BODY_FONT);
        panel.add(idLabel, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        idField = createStyledTextField(UIConstants.ID_PLACEHOLDER);
        // Configurar para solo números
        idField.setDocument(new NumericDocumentFilter());
        panel.add(idField, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        idErrorLabel = createErrorLabel();
        panel.add(idErrorLabel, gbc);
        
        // Campo Nombre
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        JLabel nombreLabel = new JLabel(UIConstants.NAME_LABEL);
        nombreLabel.setFont(UIConstants.BODY_FONT);
        panel.add(nombreLabel, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        nombreField = createStyledTextField(UIConstants.NAME_PLACEHOLDER);
        panel.add(nombreField, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        nombreErrorLabel = createErrorLabel();
        panel.add(nombreErrorLabel, gbc);
        
        // Campo Precio
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        JLabel precioLabel = new JLabel(UIConstants.PRICE_LABEL);
        precioLabel.setFont(UIConstants.BODY_FONT);
        panel.add(precioLabel, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        precioField = createStyledTextField(UIConstants.PRICE_PLACEHOLDER);
        // Configurar para solo números decimales
        precioField.setDocument(new DecimalDocumentFilter());
        panel.add(precioField, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        precioErrorLabel = createErrorLabel();
        panel.add(precioErrorLabel, gbc);
        
        // Campo Estado
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0;
        JLabel estadoLabel = new JLabel(UIConstants.STATUS_LABEL);
        estadoLabel.setFont(UIConstants.BODY_FONT);
        panel.add(estadoLabel, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        estadoComboBox = createStyledComboBox();
        estadoComboBox.addItem("Activo");
        estadoComboBox.addItem("Inactivo");
        estadoComboBox.setSelectedItem("Activo");
        panel.add(estadoComboBox, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        estadoErrorLabel = createErrorLabel();
        panel.add(estadoErrorLabel, gbc);
        
        return panel;
    }
    
    /**
     * Crea el panel de búsqueda
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR),
            new EmptyBorder(UIConstants.SMALL_PADDING, UIConstants.PADDING, 
                          UIConstants.SMALL_PADDING, UIConstants.PADDING)
        ));
        
        // Panel superior para el label
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelPanel.setBackground(Color.WHITE);
        JLabel searchLabel = new JLabel(UIConstants.SEARCH_LABEL);
        searchLabel.setFont(UIConstants.BODY_FONT);
        labelPanel.add(searchLabel);
        
        // Panel para input y botón alineados horizontalmente
        JPanel inputButtonPanel = new JPanel(new BorderLayout(UIConstants.PADDING, 0));
        inputButtonPanel.setBackground(Color.WHITE);
        
        searchField = createStyledTextField(UIConstants.SEARCH_PLACEHOLDER);
        searchField.setPreferredSize(new Dimension(300, UIConstants.FIELD_HEIGHT));
        searchField.setMinimumSize(new Dimension(200, 40));
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIConstants.FIELD_HEIGHT));
        inputButtonPanel.add(searchField, BorderLayout.CENTER);
        
        buscarButton = createStyledButton(UIConstants.SEARCH_BUTTON_TEXT, UIConstants.PRIMARY_COLOR);
        inputButtonPanel.add(buscarButton, BorderLayout.EAST);
        
        panel.add(labelPanel, BorderLayout.NORTH);
        panel.add(inputButtonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel de la tabla
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR),
            new EmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 
                          UIConstants.PADDING, UIConstants.PADDING)
        ));
        
        JLabel tableTitle = new JLabel(UIConstants.PRODUCTS_TABLE_LABEL);
        tableTitle.setFont(UIConstants.HEADER_FONT);
        tableTitle.setForeground(UIConstants.PRIMARY_COLOR);
        panel.add(tableTitle, BorderLayout.NORTH);
        
        // Crear tabla
        tableModel = new ProductTableModel();
        productosTable = new JTable(tableModel);
        productosTable.setFont(UIConstants.BODY_FONT);
        productosTable.setRowHeight(35);
        productosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Configurar renderer para la columna de acciones
        productosTable.getColumnModel().getColumn(4).setCellRenderer(new ActionButtonRenderer());
        
        // Renderer personalizado para todos los headers de la tabla
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                         boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setHorizontalAlignment(JLabel.CENTER);
                // Tamaño moderadamente más grande para headers (excepto acciones)
                if (column == 4) { // Columna de acciones
                    label.setFont(UIConstants.BODY_FONT.deriveFont(Font.BOLD, UIConstants.BODY_FONT.getSize() - 2));
                } else { // Resto de columnas con fuente ligeramente más grande
                    label.setFont(UIConstants.BODY_FONT.deriveFont(Font.BOLD, UIConstants.BODY_FONT.getSize() + 1));
                }
                // Usar el color original de los headers (gris por defecto)
                label.setForeground(Color.BLACK);
                label.setBackground(UIConstants.BACKGROUND_COLOR);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return label;
            }
        };
        
        // Aplicar el renderer personalizado a todas las columnas
        for (int i = 0; i < productosTable.getColumnCount(); i++) {
            productosTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        // Agregar MouseListener directo para detectar clics en la columna de acciones
        productosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = productosTable.rowAtPoint(e.getPoint());
                int col = productosTable.columnAtPoint(e.getPoint());
                
                // Si se hace clic en la columna de acciones (columna 4)
                if (col == 4 && row >= 0) {
                    eliminarProducto(row);
                } else if (col >= 0 && col < 4 && row >= 0) {
                    // Seleccionar producto para edición
                    seleccionarProductoParaEdicion(row);
                }
            }
        });
        
        tableScrollPane = new JScrollPane(productosTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 300));
        tableScrollPane.setMinimumSize(new Dimension(400, 200));
        tableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel de botones
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, UIConstants.PADDING, UIConstants.PADDING));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR),
            new EmptyBorder(UIConstants.PADDING, UIConstants.PADDING, 
                          UIConstants.PADDING, UIConstants.PADDING)
        ));
        
        agregarButton = createStyledButton(UIConstants.ADD_BUTTON_TEXT, UIConstants.SUCCESS_COLOR);
        actualizarButton = createStyledButton(UIConstants.UPDATE_BUTTON_TEXT, UIConstants.WARNING_COLOR);
        limpiarButton = createStyledButton(UIConstants.CLEAR_BUTTON_TEXT, UIConstants.SECONDARY_COLOR);
        refrescarButton = createStyledButton(UIConstants.REFRESH_BUTTON_TEXT, UIConstants.PRIMARY_COLOR);
        
        panel.add(agregarButton);
        panel.add(actualizarButton);
        panel.add(limpiarButton);
        panel.add(refrescarButton);
        
        // Inicialmente deshabilitar el botón actualizar
        actualizarButton.setEnabled(false);
        
        return panel;
    }
    
    /**
     * Organiza los componentes en el layout
     */
    private void layoutModernComponents() {
        // Agregar componentes al panel principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Panel de contenido con layout horizontal responsive
        contentPanel.setLayout(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
        
        // Panel izquierdo para formulario y botones
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
        leftPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Panel derecho para búsqueda y tabla
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
        rightPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        rightPanel.add(searchPanel, BorderLayout.NORTH);
        rightPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Agregar paneles al contenido
        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Agregar el panel principal al frame
        setContentPane(mainPanel);
        
        // Configurar responsive design
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayoutForSize(getWidth(), getHeight());
            }
        });
    }
    
    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        // Botón agregar producto
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });
        
        // Botón actualizar producto
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
        
        // Botón limpiar formulario
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        
        // Botón buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductos();
            }
        });
        
        // Botón refrescar
        refrescarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescarTabla();
            }
        });
        
        // Campo de búsqueda con búsqueda en tiempo real
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductos();
            }
        });
    }
    
    /**
     * Configura la tabla
     */
    private void setupTable() {
        // Configurar sorter para la tabla
        TableRowSorter<ProductTableModel> sorter = new TableRowSorter<>(tableModel);
        productosTable.setRowSorter(sorter);
        
        // Configurar renderer para centrar el contenido
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < productosTable.getColumnCount(); i++) {
            productosTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Configurar ancho de columnas inicial
        productosTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        productosTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
        productosTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Precio
        productosTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Estado
        productosTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Acciones
        
        // Hacer que la columna de acciones sea fija
        productosTable.getColumnModel().getColumn(4).setMinWidth(100);
        productosTable.getColumnModel().getColumn(4).setMaxWidth(100);
        productosTable.getColumnModel().getColumn(4).setResizable(false);
        
        // Configurar tabla
        productosTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        productosTable.setFillsViewportHeight(true);
        productosTable.setDefaultEditor(Object.class, null);
        
        // Personalizar colores de selección
        productosTable.setSelectionBackground(new Color(240, 248, 255));
        productosTable.setSelectionForeground(Color.BLACK);
        
        // Configurar scroll pane
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
    
    /**
     * Agrega un nuevo producto
     */
    private void agregarProducto() {
        try {
            // Validar campos
            if (!validarCampos()) {
                return;
            }
            
            int productId = Integer.parseInt(idField.getText());
            
            // Verificar que no exista un producto con el mismo ID
            if (ProductManager.existeProducto(productId)) {
                mostrarMensaje("Ya existe un producto con el ID " + productId, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear producto
            Product producto = new Product(
                productId,
                nombreField.getText(),
                Float.parseFloat(precioField.getText()),
                "Activo".equals(estadoComboBox.getSelectedItem()) ? "Y" : "N"
            );
            
            // Agregar a la base de datos
            if (ProductManager.insertarProducto(producto)) {
                limpiarFormulario();
                refrescarTabla();
                mostrarMensaje(UIConstants.SUCCESS_MESSAGE, "Producto agregado exitosamente", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarMensaje(UIConstants.ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            mostrarMensaje(UIConstants.CONNECTION_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Actualiza un producto existente
     */
    private void actualizarProducto() {
        try {
            if (productoSeleccionado == null) {
                mostrarMensaje("Por favor seleccione un producto de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar campos (excepto ID ya que está deshabilitado)
            if (!validarCamposParaEdicion()) {
                return;
            }
            
            // Actualizar producto manteniendo el ID original
            productoSeleccionado.setName(nombreField.getText());
            productoSeleccionado.setPrice(Float.parseFloat(precioField.getText()));
            productoSeleccionado.setActive("Activo".equals(estadoComboBox.getSelectedItem()) ? "Y" : "N");
            
            // Actualizar en la base de datos
            if (ProductManager.actualizarProducto(productoSeleccionado)) {
                limpiarFormulario();
                refrescarTabla();
                mostrarMensaje(UIConstants.SUCCESS_MESSAGE, "Producto actualizado exitosamente", JOptionPane.INFORMATION_MESSAGE);
                modoEdicion = false;
                actualizarButton.setEnabled(false);
                agregarButton.setEnabled(true);
            } else {
                mostrarMensaje(UIConstants.ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            mostrarMensaje(UIConstants.CONNECTION_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Valida todos los campos del formulario
     */
    private boolean validarCampos() {
        boolean valido = true;
        
        // Limpiar errores previos
        limpiarErrores();
        
        // Validar ID
        if (idField.getText().trim().isEmpty()) {
            idErrorLabel.setText("Campo requerido");
            idErrorLabel.setVisible(true);
            valido = false;
        } else {
            try {
                int id = Integer.parseInt(idField.getText());
                if (id <= 0) {
                    idErrorLabel.setText("ID debe ser positivo");
                    idErrorLabel.setVisible(true);
                    valido = false;
                }
            } catch (NumberFormatException e) {
                idErrorLabel.setText("Solo números");
                idErrorLabel.setVisible(true);
                valido = false;
            }
        }
        
        // Validar nombre
        if (nombreField.getText().trim().isEmpty()) {
            nombreErrorLabel.setText("Campo requerido");
            nombreErrorLabel.setVisible(true);
            valido = false;
        }
        
        // Validar precio
        if (precioField.getText().trim().isEmpty()) {
            precioErrorLabel.setText("Campo requerido");
            precioErrorLabel.setVisible(true);
            valido = false;
        } else {
            try {
                float precio = Float.parseFloat(precioField.getText());
                if (precio < 0) {
                    precioErrorLabel.setText("Precio debe ser positivo");
                    precioErrorLabel.setVisible(true);
                    valido = false;
                }
            } catch (NumberFormatException e) {
                precioErrorLabel.setText("Formato inválido");
                precioErrorLabel.setVisible(true);
                valido = false;
            }
        }
        
        return valido;
    }
    
    /**
     * Valida campos del formulario para edición (excluye ID)
     */
    private boolean validarCamposParaEdicion() {
        boolean valido = true;
        
        // Limpiar errores previos
        limpiarErrores();
        
        // Validar nombre
        if (nombreField.getText().trim().isEmpty()) {
            nombreErrorLabel.setText("Campo requerido");
            nombreErrorLabel.setVisible(true);
            valido = false;
        }
        
        // Validar precio
        if (precioField.getText().trim().isEmpty()) {
            precioErrorLabel.setText("Campo requerido");
            precioErrorLabel.setVisible(true);
            valido = false;
        } else {
            try {
                float precio = Float.parseFloat(precioField.getText());
                if (precio < 0) {
                    precioErrorLabel.setText("Precio debe ser positivo");
                    precioErrorLabel.setVisible(true);
                    valido = false;
                }
            } catch (NumberFormatException e) {
                precioErrorLabel.setText("Formato inválido");
                precioErrorLabel.setVisible(true);
                valido = false;
            }
        }
        
        return valido;
    }
    
    /**
     * Limpia el formulario
     */
    private void limpiarFormulario() {
        idField.setText("");
        nombreField.setText("");
        precioField.setText("");
        estadoComboBox.setSelectedItem("Activo");
        limpiarErrores();
        
        // Restaurar modo normal
        modoEdicion = false;
        productoSeleccionado = null;
        actualizarButton.setEnabled(false);
        agregarButton.setEnabled(true);
        
        // Habilitar siempre el campo ID
        idField.setEnabled(true);
        
        // Establecer foco en el campo ID para facilitar la entrada de datos
        idField.requestFocus();
    }
    
    /**
     * Limpia todos los mensajes de error
     */
    private void limpiarErrores() {
        idErrorLabel.setVisible(false);
        nombreErrorLabel.setVisible(false);
        precioErrorLabel.setVisible(false);
        estadoErrorLabel.setVisible(false);
    }
    
    /**
     * Busca productos
     */
    private void buscarProductos() {
        String busqueda = searchField.getText().trim();
        List<Product> resultados;
        
        if (busqueda.isEmpty()) {
            resultados = ProductManager.obtenerTodosLosProductos();
        } else {
            resultados = ProductManager.buscarProductos(busqueda);
        }
        
        tableModel.setProductos(resultados);
    }
    
    /**
     * Refresca la tabla
     */
    private void refrescarTabla() {
        tableModel.refrescarTabla();
        searchField.setText("");
    }
    
    /**
     * Selecciona un producto para edición
     */
    private void seleccionarProductoParaEdicion(int row) {
        int modelRow = productosTable.convertRowIndexToModel(row);
        productoSeleccionado = tableModel.getProducto(modelRow);
        
        if (productoSeleccionado != null) {
            idField.setText(String.valueOf(productoSeleccionado.getProduct_id()));
            nombreField.setText(productoSeleccionado.getName());
            precioField.setText(String.valueOf(productoSeleccionado.getPrice()));
            estadoComboBox.setSelectedItem(productoSeleccionado.getEstadoFormateado());
            
            // Cambiar a modo edición
            modoEdicion = true;
            actualizarButton.setEnabled(true);
            agregarButton.setEnabled(false);
            
            // Deshabilitar el campo ID al seleccionar un producto para editar
            idField.setEnabled(false);
        }
    }
    
    /**
     * Elimina un producto
     */
    private void eliminarProducto(int row) {
        int modelRow = productosTable.convertRowIndexToModel(row);
        Product producto = tableModel.getProducto(modelRow);
        
        if (producto != null) {
            int confirmacion = JOptionPane.showOptionDialog(
                this,
                UIConstants.DELETE_CONFIRMATION + "\nProducto: " + producto.getName(),
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Sí", "No"},
                "No"
            );
            
            if (confirmacion == 0) { // 0 = Sí, 1 = No
                try {
                    if (ProductManager.eliminarProducto(producto.getProduct_id())) {
                        refrescarTabla();
                        mostrarMensaje("Producto eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        mostrarMensaje("Error al eliminar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    mostrarMensaje(UIConstants.CONNECTION_ERROR, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Muestra un mensaje al usuario
     */
    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
    
    /**
     * Ajusta el layout según el tamaño de la ventana
     */
    private void adjustLayoutForSize(int width, int height) {
        // Cambiar a layout vertical para pantallas muy pequeñas
        if (width < 700) {
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            
            contentPanel.removeAll();
            JPanel topPanel = new JPanel(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
            topPanel.setBackground(UIConstants.BACKGROUND_COLOR);
            topPanel.add(formPanel, BorderLayout.CENTER);
            topPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            JPanel bottomPanel = new JPanel(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
            bottomPanel.setBackground(UIConstants.BACKGROUND_COLOR);
            bottomPanel.add(searchPanel, BorderLayout.NORTH);
            bottomPanel.add(tablePanel, BorderLayout.CENTER);
            
            contentPanel.add(topPanel);
            contentPanel.add(Box.createVerticalStrut(UIConstants.PADDING));
            contentPanel.add(bottomPanel);
        } else {
            // Layout horizontal para pantallas grandes
            contentPanel.setLayout(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
            
            contentPanel.removeAll();
            JPanel leftPanel = new JPanel(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
            leftPanel.setBackground(UIConstants.BACKGROUND_COLOR);
            leftPanel.add(formPanel, BorderLayout.CENTER);
            leftPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            JPanel rightPanel = new JPanel(new BorderLayout(UIConstants.PADDING, UIConstants.PADDING));
            rightPanel.setBackground(UIConstants.BACKGROUND_COLOR);
            rightPanel.add(searchPanel, BorderLayout.NORTH);
            rightPanel.add(tablePanel, BorderLayout.CENTER);
            
            contentPanel.add(leftPanel, BorderLayout.WEST);
            contentPanel.add(rightPanel, BorderLayout.CENTER);
        }
        
        // Ajustar ancho de columnas de la tabla de forma proporcional
        int totalTableWidth = width - 350;
        if (totalTableWidth > 0) {
            int idWidth = (int)(totalTableWidth * 0.15);      // 15% del ancho
            int nombreWidth = (int)(totalTableWidth * 0.35);  // 35% del ancho
            int precioWidth = (int)(totalTableWidth * 0.20);  // 20% del ancho
            int estadoWidth = (int)(totalTableWidth * 0.15);  // 15% del ancho
            int accionesWidth = (int)(totalTableWidth * 0.15); // 15% del ancho
            
            // Establecer anchos mínimos
            idWidth = Math.max(idWidth, 60);
            nombreWidth = Math.max(nombreWidth, 120);
            precioWidth = Math.max(precioWidth, 80);
            estadoWidth = Math.max(estadoWidth, 80);
            accionesWidth = Math.max(accionesWidth, 60);
            
            productosTable.getColumnModel().getColumn(0).setPreferredWidth(idWidth);
            productosTable.getColumnModel().getColumn(1).setPreferredWidth(nombreWidth);
            productosTable.getColumnModel().getColumn(2).setPreferredWidth(precioWidth);
            productosTable.getColumnModel().getColumn(3).setPreferredWidth(estadoWidth);
            productosTable.getColumnModel().getColumn(4).setPreferredWidth(accionesWidth);
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    /**
     * Crea un campo de texto estilizado
     */
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(UIConstants.FIELD_WIDTH, UIConstants.FIELD_HEIGHT));
        field.setMinimumSize(new Dimension(200, 40));
        field.setFont(UIConstants.BODY_FONT);
        
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        
        if (placeholder != null && !placeholder.isEmpty()) {
            field.setToolTipText(placeholder);
        }
        
        return field;
    }
    
    /**
     * Crea un combo box estilizado
     */
    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(UIConstants.FIELD_WIDTH, UIConstants.FIELD_HEIGHT));
        comboBox.setMinimumSize(new Dimension(200, 40));
        comboBox.setFont(UIConstants.BODY_FONT);
        
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        
        return comboBox;
    }
    
    /**
     * Crea un botón estilizado
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(UIConstants.BUTTON_WIDTH, UIConstants.BUTTON_HEIGHT));
        button.setMinimumSize(new Dimension(100, 35));
        button.setFont(UIConstants.BODY_FONT);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        
        // Efectos hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    /**
     * Crea una etiqueta de error
     */
    private JLabel createErrorLabel() {
        JLabel label = new JLabel("");
        label.setFont(UIConstants.SMALL_FONT);
        label.setForeground(UIConstants.DANGER_COLOR);
        label.setVisible(false);
        return label;
    }
    
    /**
     * Renderer personalizado para la columna de acciones
     */
    private class ActionButtonRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                     boolean hasFocus, int row, int column) {
            JButton button = new JButton(UIConstants.DELETE_BUTTON_TEXT);
            
            // Color rojo permanente para el botón eliminar
            Color rojoEliminar = new Color(220, 53, 69); // Rojo más intenso
            button.setBackground(rojoEliminar);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(rojoEliminar.darker(), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));
            
            button.setPreferredSize(new Dimension(80, 30));
            button.setFont(UIConstants.BODY_FONT);
            
            button.setSelected(false);
            button.setRolloverEnabled(false);
            button.setEnabled(true);
            button.setVisible(true);
            
            // Asegurar que el color rojo se mantenga siempre
            button.setBackground(rojoEliminar);
            button.setForeground(Color.WHITE);
            
            // Efecto hover personalizado para mantener el rojo
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(rojoEliminar.darker());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(rojoEliminar);
                }
            });
            
            return button;
        }
    }
    
    /**
     * DocumentFilter para solo números
     */
    private class NumericDocumentFilter extends javax.swing.text.PlainDocument {
        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (str == null) return;
            
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return;
                }
            }
            super.insertString(offset, str, attr);
        }
    }
    
    /**
     * DocumentFilter para números decimales
     */
    private class DecimalDocumentFilter extends javax.swing.text.PlainDocument {
        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (str == null) return;
            
            String currentText = getText(0, getLength());
            String newText = currentText.substring(0, offset) + str + currentText.substring(offset);
            
            try {
                Float.parseFloat(newText);
                super.insertString(offset, str, attr);
            } catch (NumberFormatException e) {
                // Ignorar caracteres no válidos
            }
        }
    }

    /**
     * Método main para ejecutar la aplicación
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductFrame().setVisible(true);
            }
        });
    }
}
