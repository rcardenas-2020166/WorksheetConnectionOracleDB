package edu.gt.rodrigocardenas.umg;

import edu.gt.rodrigocardenas.umg.models.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodrigo Gerardo Cárdenas Monroy
 */
public class Main {

    static String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    static String user = "umg";
    static String pass = "umg123";

    public static void main(String[] args) {
        // Insertar un producto
        Product nuevo = new Product(500, "Monitor 32 pulgadas", 2500.0f, "Y");
        insertData(nuevo);

        // Actualizar un producto
        Product actualizado = new Product(101, "PANTALLA 32 FULL SCREEN", 250.0f, "N");
        updateData(actualizado);

        // Eliminar un producto
        deleteData(105);

        // Listar productos
        List<Product> productos = getSelectData();
        for (Product p : productos) {
            System.out.println("ID     : " + p.product_id);
            System.out.println("NOMBRE : " + p.name);
            System.out.println("PRECIO : " + p.price);
            System.out.println("ESTADO : " + p.active);
            System.out.println("------------------------------");
        }
    }

    // SELECT: devuelve lista de productos
    public static List<Product> getSelectData() {
        List<Product> lista = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM UMG.PRODUCTS ORDER BY PRODUCT_ID";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int productId = rs.getInt("PRODUCT_ID");
                String productName = rs.getString("NAME");
                float productPrice = rs.getFloat("PRICE");
                String productState = rs.getString("ACTIVE");

                Product product = new Product(productId, productName, productPrice, productState);
                lista.add(product);
            }

            rs.close();
            st.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Error en la conexión (SELECT)");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    // INSERT: inserta un producto
    public static void insertData(Product newProduct) {
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);

            String sql = "INSERT INTO UMG.PRODUCTS (product_id, name, price, active) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newProduct.product_id);
            ps.setString(2, newProduct.name);
            ps.setFloat(3, newProduct.price);
            ps.setString(4, newProduct.active);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Producto insertado correctamente.");
            }

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Error en la conexión o INSERT");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // UPDATE: actualiza un producto por ID
    public static void updateData(Product updatedProduct) {
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);

            String sql = "UPDATE UMG.PRODUCTS SET name = ?, price = ?, active = ? WHERE product_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, updatedProduct.name);
            ps.setFloat(2, updatedProduct.price);
            ps.setString(3, updatedProduct.active);
            ps.setInt(4, updatedProduct.product_id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("No se encontró el producto con ID " + updatedProduct.product_id);
            }

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Error en la conexión o UPDATE");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // DELETE: elimina un producto por ID
    public static void deleteData(int productId) {
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);

            String sql = "DELETE FROM UMG.PRODUCTS WHERE product_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se encontró el producto con ID " + productId);
            }

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Error en la conexión o DELETE");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
