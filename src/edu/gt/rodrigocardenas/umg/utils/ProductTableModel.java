package edu.gt.rodrigocardenas.umg.utils;

import edu.gt.rodrigocardenas.umg.models.Product;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de tabla personalizado para mostrar productos
 * @author Rodrigo Gerardo Cárdenas Monroy
 */
public class ProductTableModel extends AbstractTableModel {
    
    private List<Product> productos;
    private final String[] columnNames = {"ID", "Nombre", "Precio", "Estado", "Acciones"};
    private final Class<?>[] columnClasses = {Integer.class, String.class, Float.class, String.class, String.class};
    
    /**
     * Constructor
     */
    public ProductTableModel() {
        this.productos = new ArrayList<>();
    }
    
    /**
     * Establece la lista de productos
     * @param productos Lista de productos a mostrar
     */
    public void setProductos(List<Product> productos) {
        this.productos = productos != null ? productos : new ArrayList<>();
        fireTableDataChanged();
    }
    
    /**
     * Refresca la tabla con datos actualizados
     */
    public void refrescarTabla() {
        // Obtener datos frescos de la base de datos
        List<Product> nuevosProductos = ProductManager.obtenerTodosLosProductos();
        setProductos(nuevosProductos);
    }
    
    @Override
    public int getRowCount() {
        return productos.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= productos.size()) {
            return null;
        }
        
        Product producto = productos.get(rowIndex);
        
        switch (columnIndex) {
            case 0: // ID
                return producto.getProduct_id();
            case 1: // Nombre
                return producto.getName();
            case 2: // Precio
                return producto.getPrice();
            case 3: // Estado
                return producto.getEstadoFormateado();
            case 4: // Acciones
                return "Eliminar";
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Solo la columna de acciones es editable
        return columnIndex == 4;
    }
    
    /**
     * Obtiene el producto en la fila especificada
     * @param rowIndex Índice de la fila
     * @return Producto en esa fila o null si no existe
     */
    public Product getProducto(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < productos.size()) {
            return productos.get(rowIndex);
        }
        return null;
    }
    
    /**
     * Obtiene todos los productos del modelo
     * @return Lista de productos
     */
    public List<Product> getProductos() {
        return new ArrayList<>(productos);
    }
}
