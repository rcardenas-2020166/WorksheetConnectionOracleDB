package edu.gt.rodrigocardenas.umg;

import edu.gt.rodrigocardenas.umg.gui.frames.ProductFrame;
import edu.gt.rodrigocardenas.umg.utils.UIConstants;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Clase principal para el sistema de gestión de productos con Oracle Database
 * @author Rodrigo Gerardo Cárdenas Monroy
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            logger.log(Level.WARNING, "No se pudo establecer el Look and Feel del sistema", e);
        }
        
        // Verificar conexión a la base de datos
        if (!verificarConexionDB()) {
            JOptionPane.showMessageDialog(null, 
                "No se puede conectar a la base de datos Oracle.\n" +
                "Por favor verifique que:\n" +
                "1. Oracle XE esté ejecutándose\n" +
                "2. Los datos de conexión sean correctos\n" +
                "3. La tabla UMG.PRODUCTS exista",
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Ejecutar la aplicación GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ProductFrame().setVisible(true);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error al iniciar la aplicación", e);
                    JOptionPane.showMessageDialog(null, 
                        "Error al iniciar la aplicación: " + e.getMessage(),
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    /**
     * Verifica la conexión a la base de datos
     * @return true si la conexión es exitosa, false en caso contrario
     */
    private static boolean verificarConexionDB() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(UIConstants.DB_URL, UIConstants.DB_USER, UIConstants.DB_PASSWORD);
            
            // Verificar que la tabla existe
            String sql = "SELECT COUNT(*) FROM UMG.PRODUCTS";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            boolean tablaExiste = rs.next();
            
            rs.close();
            st.close();
            
            return tablaExiste;
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al verificar conexión a la base de datos", ex);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    logger.log(Level.WARNING, "Error al cerrar conexión", ex);
                }
            }
        }
    }

}
