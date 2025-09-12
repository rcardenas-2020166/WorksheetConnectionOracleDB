# 🗄️ Sistema de Gestión de Productos con Oracle Database - UMG

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Oracle](https://img.shields.io/badge/Database-Oracle-red?style=for-the-badge&logo=oracle)
![JDBC](https://img.shields.io/badge/Driver-JDBC-blue?style=for-the-badge&logo=java)
![Docker](https://img.shields.io/badge/Container-Docker-blue?style=for-the-badge&logo=docker)
![NetBeans](https://img.shields.io/badge/IDE-NetBeans-green?style=for-the-badge&logo=apachenetbeanside)
![Status](https://img.shields.io/badge/Status-Completado-success?style=for-the-badge)

## 📋 Descripción del Proyecto

Sistema de gestión de productos desarrollado en Java que implementa operaciones CRUD completas conectándose a Oracle Database Express Edition mediante JDBC. El proyecto demuestra el uso de conexiones de base de datos, consultas SQL parametrizadas y manejo de transacciones, siguiendo las mejores prácticas de programación orientada a objetos.

## 👨‍💻 Información del Desarrollador

**Desarrollado por:** Rodrigo Gerardo Cárdenas Monroy  
**Carné:** 9490-23-25128  
**Clase:** Programación II  
**Proyecto:** Sistema de Gestión de Productos con Oracle DB  
**Universidad:** Mariano Gálvez de Guatemala  
**GitHub:** [@rcardenas-2020166](https://github.com/rcardenas-2020166)

## ✨ Características Principales

### 🎯 Funcionalidades Core
- **Inserción de Productos**: Registro de nuevos productos con validación de datos
- **Consulta de Productos**: Listado completo de productos ordenados por ID
- **Actualización de Productos**: Modificación de información existente por ID
- **Eliminación de Productos**: Borrado seguro de productos por ID
- **Conexión Robusta**: Manejo de conexiones JDBC con Oracle Database

### 🔄 Operaciones CRUD Implementadas
- **CREATE**: `insertData(Product newProduct)` - Insertar nuevos productos
- **READ**: `getSelectData()` - Obtener lista completa de productos
- **UPDATE**: `updateData(Product updatedProduct)` - Actualizar productos existentes
- **DELETE**: `deleteData(int productId)` - Eliminar productos por ID

### 🛡️ Características de Seguridad
- **Consultas Parametrizadas**: Uso de PreparedStatement para prevenir SQL Injection
- **Manejo de Excepciones**: Gestión robusta de errores SQL y de conexión
- **Cierre de Recursos**: Liberación automática de conexiones, statements y result sets
- **Validación de Resultados**: Verificación de filas afectadas en operaciones

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 17+ | Lenguaje de programación principal |
| **Oracle JDBC Driver** | ojdbc17.jar | Driver para conexión a Oracle Database |
| **Oracle Database** | XE 21c | Base de datos relacional |
| **Docker** | Latest | Contenedorización de Oracle XE |
| **JDBC API** | Built-in | API para acceso a bases de datos |
| **NetBeans** | 12+ | Entorno de desarrollo integrado |

## 🏗️ Arquitectura del Proyecto

```
ConnectionDB/
├── src/
│   └── edu/gt/rodrigocardenas/umg/
│       ├── models/
│       │   └── Product.java              # Modelo de datos del producto
│       └── Main.java                     # Clase principal con operaciones CRUD
├── lib/
│   └── ojdbc17.jar                       # Driver JDBC de Oracle
├── nbproject/                            # Configuración NetBeans
├── build/                                # Archivos compilados
└── README.md                             # Este archivo
```

## 🐳 Configuración de Oracle con Docker

### Prerrequisitos
- **Java JDK 17 o superior**
- **Docker Desktop** instalado y ejecutándose
- **NetBeans IDE 12+ (recomendado)**

### Configuración de Oracle XE en Docker

1. **Descargar e iniciar Oracle XE**
   ```bash
   # Descargar imagen de Oracle XE
   docker pull gvenzl/oracle-xe:21-slim
   
   # Ejecutar contenedor Oracle XE
   docker run -d --name oracle-xe \
     -e ORACLE_PASSWORD=umg123 \
     -e ORACLE_DATABASE=UMG \
     -p 1521:1521 \
     gvenzl/oracle-xe:21-slim
   ```

2. **Verificar conexión**
   ```bash
   # Verificar que el contenedor esté ejecutándose
   docker ps
   
   # Ver logs del contenedor
   docker logs oracle-xe
   ```

3. **Crear tabla de productos**
   ```sql
   -- Conectar a Oracle XE
   sqlplus umg/umg123@localhost:1521/XEPDB1
   
   -- Crear tabla PRODUCTS
   CREATE TABLE UMG.PRODUCTS (
       PRODUCT_ID NUMBER PRIMARY KEY,
       NAME VARCHAR2(100) NOT NULL,
       PRICE NUMBER(10,2) NOT NULL,
       ACTIVE CHAR(1) DEFAULT 'Y'
   );
   
   -- Insertar datos de ejemplo
   INSERT INTO UMG.PRODUCTS VALUES (101, 'Laptop Dell', 1500.00, 'Y');
   INSERT INTO UMG.PRODUCTS VALUES (102, 'Mouse Logitech', 25.50, 'Y');
   INSERT INTO UMG.PRODUCTS VALUES (103, 'Teclado Mecánico', 89.99, 'N');
   COMMIT;
   ```

## 🚀 Instalación y Ejecución

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/rcardenas-2020166/ConnectionDB.git
   cd ConnectionDB
   ```

2. **Configurar dependencias en NetBeans**
   - Abrir NetBeans IDE
   - File → Open Project
   - Seleccionar la carpeta `ConnectionDB`
   - Verificar que ojdbc17.jar esté en las librerías del proyecto

3. **Configurar conexión a base de datos**
   ```java
   // En Main.java, verificar configuración de conexión
   static String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
   static String user = "umg";
   static String pass = "umg123";
   ```

4. **Compilar y ejecutar**
   ```bash
   # Desde línea de comandos
   javac -cp "lib/ojdbc17.jar" -d build src/edu/gt/rodrigocardenas/umg/**/*.java
   java -cp "build:lib/ojdbc17.jar" edu.gt.rodrigocardenas.umg.Main
   ```

   O usar el botón **Run** en NetBeans

## 📱 Guía de Uso

### 1. Operaciones CRUD Disponibles

#### **Crear Producto**
```java
Product nuevo = new Product(500, "Monitor 32 pulgadas", 2500.0f, "Y");
insertData(nuevo);
```

#### **Leer Productos**
```java
List<Product> productos = getSelectData();
for (Product p : productos) {
    System.out.println("ID: " + p.product_id + " - " + p.name);
}
```

#### **Actualizar Producto**
```java
Product actualizado = new Product(101, "PANTALLA 32 FULL SCREEN", 250.0f, "N");
updateData(actualizado);
```

#### **Eliminar Producto**
```java
deleteData(105); // Elimina producto con ID 105
```

### 2. Estructura de la Tabla PRODUCTS

| Campo | Tipo | Descripción |
|-------|------|-------------|
| **PRODUCT_ID** | NUMBER | Identificador único (Primary Key) |
| **NAME** | VARCHAR2(100) | Nombre del producto |
| **PRICE** | NUMBER(10,2) | Precio del producto |
| **ACTIVE** | CHAR(1) | Estado del producto (Y/N) |

## 🎨 Características de Diseño

### Paleta de Colores para Logs
- **Verde Éxito**: `\u001B[32m` - Operaciones exitosas
- **Rojo Error**: `\u001B[31m` - Mensajes de error
- **Azul Info**: `\u001B[34m` - Información general
- **Amarillo Advertencia**: `\u001B[33m` - Advertencias

### Formato de Salida
```
ID     : 101
NOMBRE : Laptop Dell
PRECIO : 1500.0
ESTADO : Y
------------------------------
```

## 🔧 Funcionalidades Técnicas

### Configuración de Conexión JDBC
```java
// URL de conexión Oracle XE
static String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";

// Credenciales de acceso
static String user = "umg";
static String pass = "umg123";
```

### Consultas SQL Implementadas
```sql
-- SELECT: Obtener todos los productos
SELECT * FROM UMG.PRODUCTS ORDER BY PRODUCT_ID

-- INSERT: Insertar nuevo producto
INSERT INTO UMG.PRODUCTS (product_id, name, price, active) VALUES (?, ?, ?, ?)

-- UPDATE: Actualizar producto existente
UPDATE UMG.PRODUCTS SET name = ?, price = ?, active = ? WHERE product_id = ?

-- DELETE: Eliminar producto por ID
DELETE FROM UMG.PRODUCTS WHERE product_id = ?
```

### Manejo de Recursos
```java
// Patrón de cierre de recursos
try {
    Connection conn = DriverManager.getConnection(url, user, pass);
    // ... operaciones ...
} finally {
    if (rs != null) rs.close();
    if (st != null) st.close();
    if (conn != null) conn.close();
}
```

## 🧪 Testing y Validación

El proyecto incluye validaciones automáticas:
- Verificación de conexión a base de datos
- Validación de filas afectadas en operaciones
- Manejo de excepciones SQL
- Verificación de integridad de datos

### Casos de Prueba
- ✅ Inserción de productos nuevos
- ✅ Consulta de productos existentes
- ✅ Actualización de productos por ID
- ✅ Eliminación de productos por ID
- ✅ Manejo de errores de conexión
- ✅ Validación de datos nulos

## 📊 Estadísticas del Proyecto

- **Líneas de código**: ~160
- **Clases principales**: 2
- **Métodos CRUD**: 4
- **Consultas SQL**: 4
- **Tiempo de desarrollo**: 1 semana
- **Patrones implementados**: DAO (Data Access Object)

## 🔮 Funcionalidades Futuras

- **Interfaz Gráfica**: GUI con Swing para operaciones CRUD
- **Transacciones**: Implementación de transacciones ACID
- **Pool de Conexiones**: Optimización de conexiones a BD
- **Logging**: Sistema de logs avanzado
- **Validaciones**: Validaciones de negocio más robustas
- **Búsquedas**: Filtros y búsquedas avanzadas
- **Reportes**: Generación de reportes de productos

## 🐳 Docker Compose (Opcional)

```yaml
version: '3.8'
services:
  oracle-xe:
    image: gvenzl/oracle-xe:21-slim
    container_name: oracle-xe
    environment:
      ORACLE_PASSWORD: umg123
      ORACLE_DATABASE: UMG
    ports:
      - "1521:1521"
    volumes:
      - oracle-data:/opt/oracle/oradata
    restart: unless-stopped

volumes:
  oracle-data:
```

## 📝 Licencia

Este proyecto es desarrollado con fines educativos para la Universidad Mariano Gálvez de Guatemala.

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Para cambios importantes:
1. Fork el proyecto
2. Cree una rama para su feature (`git checkout -b feature/AmazingFeature`)
3. Commit sus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abra un Pull Request

## 📞 Contacto

**Desarrollador:** Rodrigo Gerardo Cárdenas Monroy  
**Correo:** rodrigo.cardenas@umg.edu.gt  
**GitHub:** [@rcardenas-2020166](https://github.com/rcardenas-2020166)

---

⭐ **¿Te gustó el proyecto? ¡Dale una estrella en GitHub!**

---

## 🔗 Enlaces Útiles

- [Oracle JDBC Documentation](https://docs.oracle.com/en/database/oracle/oracle-database/21/jjdbc/)
- [Docker Oracle XE](https://hub.docker.com/r/gvenzl/oracle-xe)
- [Java JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [Oracle Database Express Edition](https://www.oracle.com/database/technologies/xe-downloads.html)
