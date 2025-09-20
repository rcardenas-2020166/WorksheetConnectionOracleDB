package edu.gt.rodrigocardenas.umg.models;

/**
 * Modelo de Producto para la aplicación de gestión de productos con Oracle
 * @author Rodrigo Gerardo Cárdenas Monroy
 */
public class Product {
    private int product_id;
    private String name;
    private Float price;
    private String active;
    
    /**
     * Constructor por defecto
     */
    public Product() {
        this.product_id = 0;
        this.name = "";
        this.price = 0.0f;
        this.active = "Y";
    }
    
    /**
     * Constructor con parámetros
     * @param product_id ID del producto
     * @param name Nombre del producto
     * @param price Precio del producto
     * @param active Estado del producto (Y/N)
     */
    public Product(int product_id, String name, Float price, String active) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("El precio debe ser un valor positivo");
        }
        if (active == null || (!active.equals("Y") && !active.equals("N"))) {
            throw new IllegalArgumentException("El estado debe ser 'Y' o 'N'");
        }
        
        this.product_id = product_id;
        this.name = name.trim();
        this.price = price;
        this.active = active;
    }
    
    // Getters y Setters
    public int getProduct_id() {
        return product_id;
    }
    
    public void setProduct_id(int product_id) {
        if (product_id <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser un número positivo");
        }
        this.product_id = product_id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        this.name = name.trim();
    }
    
    public Float getPrice() {
        return price;
    }
    
    public void setPrice(Float price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("El precio debe ser un valor positivo");
        }
        this.price = price;
    }
    
    public String getActive() {
        return active;
    }
    
    public void setActive(String active) {
        if (active == null || (!active.equals("Y") && !active.equals("N"))) {
            throw new IllegalArgumentException("El estado debe ser 'Y' o 'N'");
        }
        this.active = active;
    }
    
    /**
     * Obtiene el estado formateado del producto
     * @return "Activo" si es Y, "Inactivo" si es N
     */
    public String getEstadoFormateado() {
        return "Y".equals(active) ? "Activo" : "Inactivo";
    }
    
    /**
     * Verifica si el producto está activo
     * @return true si el producto está activo, false en caso contrario
     */
    public boolean isActivo() {
        return "Y".equals(active);
    }
    
    /**
     * Verifica si el precio es válido
     * @return true si el precio es mayor a 0, false en caso contrario
     */
    public boolean isPrecioValido() {
        return price != null && price > 0;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", active='" + active + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return product_id == product.product_id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(product_id);
    }
}
