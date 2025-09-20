package edu.gt.rodrigocardenas.umg.utils;

import edu.gt.rodrigocardenas.umg.models.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manager para operaciones CRUD de productos con Oracle Database
 * @author Rodrigo Gerardo Cárdenas Monroy
 */
public class ProductManager {
    
    private static final Logger logger = Logger.getLogger(ProductManager.class.getName());
    
    /**
     * Obtiene todos los productos de la base de datos
     * @return Lista de productos
     */
    public static List<Product> obtenerTodosLosProductos() {
        List<Product> productos = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(UIConstants.DB_URL, UIConstants.DB_USER, UIConstants.DB_PASSWORD);
            String sql = "SELECT * FROM UMG.PRODUCTS ORDER BY PRODUCT_ID";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int productId = rs.getInt("PRODUCT_ID");
                String productName = rs.getString("NAME");
                float productPrice = rs.getFloat("PRICE");
                String productState = rs.getString("ACTIVE");

                Product product = new Product(productId, productName, productPrice, productState);
                productos.add(product);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al obtener productos de la base de datos", ex);
            throw new RuntimeException(UIConstants.CONNECTION_ERROR, ex);
        } finally {
            cerrarRecursos(rs, st, conn);
        }
        
        return productos;
    }
    
    /**
     * Inserta un nuevo producto en la base de datos
     * @param producto Producto a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public static boolean insertarProducto(Product producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DriverManager.getConnection(UIConstants.DB_URL, UIConstants.DB_USER, UIConstants.DB_PASSWORD);

            String sql = "INSERT INTO UMG.PRODUCTS (product_id, name, price, active) VALUES (?, ?, ?, ?)";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, producto.getProduct_id());
            ps.setString(2, producto.getName());
            ps.setFloat(3, producto.getPrice());
            ps.setString(4, producto.getActive());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al insertar producto en la base de datos", ex);
            if (ex.getMessage().contains("unique constraint")) {
                throw new RuntimeException(UIConstants.DUPLICATE_ID, ex);
            }
            throw new RuntimeException(UIConstants.CONNECTION_ERROR, ex);
        } finally {
            cerrarRecursos(null, ps, conn);
        }
    }
    
    /**
     * Actualiza un producto existente en la base de datos
     * @param producto Producto a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public static boolean actualizarProducto(Product producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DriverManager.getConnection(UIConstants.DB_URL, UIConstants.DB_USER, UIConstants.DB_PASSWORD);

            String sql = "UPDATE UMG.PRODUCTS SET name = ?, price = ?, active = ? WHERE product_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, producto.getName());
            ps.setFloat(2, producto.getPrice());
            ps.setString(3, producto.getActive());
            ps.setInt(4, producto.getProduct_id());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al actualizar producto en la base de datos", ex);
            throw new RuntimeException(UIConstants.CONNECTION_ERROR, ex);
        } finally {
            cerrarRecursos(null, ps, conn);
        }
    }
    
    /**
     * Elimina un producto de la base de datos por ID
     * @param productId ID del producto a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public static boolean eliminarProducto(int productId) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DriverManager.getConnection(UIConstants.DB_URL, UIConstants.DB_USER, UIConstants.DB_PASSWORD);

            String sql = "DELETE FROM UMG.PRODUCTS WHERE product_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al eliminar producto de la base de datos", ex);
            throw new RuntimeException(UIConstants.CONNECTION_ERROR, ex);
        } finally {
            cerrarRecursos(null, ps, conn);
        }
    }
    
    /**
     * Busca productos por ID o nombre
     * @param busqueda Término de búsqueda
     * @return Lista de productos que coinciden con la búsqueda
     */
    public static List<Product> buscarProductos(String busqueda) {
        List<Product> productos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(UIConstants.DB_URL, UIConstants.DB_USER, UIConstants.DB_PASSWORD);
            
            String sql = "SELECT * FROM UMG.PRODUCTS WHERE " +
                        "LOWER(NAME) LIKE LOWER(?) OR " +
                        "CAST(PRODUCT_ID AS VARCHAR2(10)) LIKE ? " +
                        "ORDER BY PRODUCT_ID";
            
            ps = conn.prepareStatement(sql);
            String searchPattern = "%" + busqueda + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("PRODUCT_ID");
                String productName = rs.getString("NAME");
                float productPrice = rs.getFloat("PRICE");
                String productState = rs.getString("ACTIVE");

                Product product = new Product(productId, productName, productPrice, productState);
                productos.add(product);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al buscar productos en la base de datos", ex);
            throw new RuntimeException(UIConstants.CONNECTION_ERROR, ex);
        } finally {
            cerrarRecursos(rs, ps, conn);
        }
        
        return productos;
    }
    
    /**
     * Verifica si un producto existe por ID
     * @param productId ID del producto a verificar
     * @return true si existe, false en caso contrario
     */
    public static boolean existeProducto(int productId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(UIConstants.DB_URL, UIConstants.DB_USER, UIConstants.DB_PASSWORD);
            
            String sql = "SELECT COUNT(*) FROM UMG.PRODUCTS WHERE PRODUCT_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al verificar existencia del producto", ex);
            throw new RuntimeException(UIConstants.CONNECTION_ERROR, ex);
        } finally {
            cerrarRecursos(rs, ps, conn);
        }
        
        return false;
    }
    
    /**
     * Obtiene el siguiente ID disponible para un nuevo producto
     * @return Siguiente ID disponible
     */
    public static int obtenerSiguienteId() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(UIConstants.DB_URL, UIConstants.DB_USER, UIConstants.DB_PASSWORD);
            
            String sql = "SELECT MAX(PRODUCT_ID) FROM UMG.PRODUCTS";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            if (rs.next()) {
                int maxId = rs.getInt(1);
                return maxId + 1;
            }
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al obtener siguiente ID", ex);
            throw new RuntimeException(UIConstants.CONNECTION_ERROR, ex);
        } finally {
            cerrarRecursos(rs, st, conn);
        }
        
        return 1; // Si no hay productos, empezar con ID 1
    }
    
    /**
     * Cierra los recursos de base de datos
     * @param rs ResultSet a cerrar
     * @param st Statement a cerrar
     * @param conn Connection a cerrar
     */
    private static void cerrarRecursos(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException ex) {
            logger.log(Level.WARNING, "Error al cerrar ResultSet", ex);
        }
        
        try {
            if (st != null) st.close();
        } catch (SQLException ex) {
            logger.log(Level.WARNING, "Error al cerrar Statement", ex);
        }
        
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            logger.log(Level.WARNING, "Error al cerrar Connection", ex);
        }
    }
}
