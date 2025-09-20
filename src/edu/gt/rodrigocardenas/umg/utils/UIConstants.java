package edu.gt.rodrigocardenas.umg.utils;

import java.awt.Color;
import java.awt.Font;

/**
 * Constantes de interfaz para mantener consistencia en el diseño
 * @author Rodrigo Gerardo Cárdenas Monroy
 */
public class UIConstants {
    
    // Colores principales
    public static final Color PRIMARY_COLOR = new Color(30, 136, 229);      // Azul principal
    public static final Color SUCCESS_COLOR = new Color(40, 180, 99);       // Verde éxito
    public static final Color WARNING_COLOR = new Color(255, 152, 0);       // Naranja advertencia
    public static final Color DANGER_COLOR = new Color(244, 67, 54);        // Rojo peligro
    public static final Color SECONDARY_COLOR = new Color(108, 117, 125);   // Gris secundario
    public static final Color BACKGROUND_COLOR = new Color(248, 249, 250);  // Fondo suave
    public static final Color BORDER_COLOR = new Color(222, 226, 230);      // Color de borde
    
    // Fuentes
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    // Tamaños
    public static final int BUTTON_HEIGHT = 40;
    public static final int BUTTON_WIDTH = 120;
    public static final int FIELD_HEIGHT = 45;
    public static final int FIELD_WIDTH = 250;
    public static final int PADDING = 15;
    public static final int SMALL_PADDING = 10;
    
    // Bordes
    public static final int BORDER_RADIUS = 8;
    public static final int BORDER_THICKNESS = 1;
    
    // Dimensiones del frame
    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 700;
    public static final int MIN_FRAME_WIDTH = 800;
    public static final int MIN_FRAME_HEIGHT = 600;
    
    // Mensajes
    public static final String APP_TITLE = "Sistema de Gestión de Productos - UMG Oracle DB";
    public static final String SUCCESS_MESSAGE = "Operación realizada exitosamente";
    public static final String ERROR_MESSAGE = "Ha ocurrido un error";
    public static final String VALIDATION_ERROR = "Por favor, complete todos los campos correctamente";
    public static final String DELETE_CONFIRMATION = "¿Está seguro de que desea eliminar este producto?";
    public static final String DUPLICATE_ID = "Ya existe un producto con este ID";
    public static final String CONNECTION_ERROR = "Error de conexión a la base de datos";
    
    // Textos de botones
    public static final String ADD_BUTTON_TEXT = "Agregar";
    public static final String CLEAR_BUTTON_TEXT = "Limpiar";
    public static final String SEARCH_BUTTON_TEXT = "Buscar";
    public static final String DELETE_BUTTON_TEXT = "Eliminar";
    public static final String REFRESH_BUTTON_TEXT = "Refrescar";
    public static final String UPDATE_BUTTON_TEXT = "Actualizar";
    
    // Textos de etiquetas
    public static final String ID_LABEL = "ID del Producto:";
    public static final String NAME_LABEL = "Nombre del Producto:";
    public static final String PRICE_LABEL = "Precio:";
    public static final String STATUS_LABEL = "Estado:";
    public static final String SEARCH_LABEL = "Buscar Producto:";
    public static final String PRODUCTS_TABLE_LABEL = "Lista de Productos";
    
    // Placeholders
    public static final String ID_PLACEHOLDER = "Ingrese el ID del producto";
    public static final String NAME_PLACEHOLDER = "Ingrese el nombre del producto";
    public static final String PRICE_PLACEHOLDER = "Ingrese el precio";
    public static final String SEARCH_PLACEHOLDER = "Buscar por ID o nombre...";
    
    // Estados
    public static final String ACTIVE_STATUS = "Activo";
    public static final String INACTIVE_STATUS = "Inactivo";
    
    // Configuración de base de datos
    public static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    public static final String DB_USER = "umg";
    public static final String DB_PASSWORD = "umg123";
}
