# 🍎 Fruit Store Sun and Ground

**Fruit Store Sun and Ground** es un sistema de gestión integral desarrollado en Java para optimizar la administración de tiendas de productos frescos. Este proyecto permite gestionar inventarios, ventas, proveedores y estadísticas de manera eficiente.

## ⚙️ Requisitos Previos

Antes de comenzar, asegúrate de tener instaladas las siguientes herramientas:

- JDK 8 o superior
- MySQL
- Maven (opcional, para gestión de dependencias)

## 🚀 Instalación y Ejecución

Sigue estos pasos para instalar y ejecutar el proyecto:

1. **Clona el repositorio:**
    ```sh
    git clone https://github.com/tu-usuario/FruitStoreSunAndGround.git
    cd FruitStoreSunAndGround
    ```

2. **Configura la base de datos:**
    - Crea una base de datos en MySQL.
    - Actualiza las credenciales de la base de datos en `DatabaseConnection.java`.

3. **Compila y ejecuta el proyecto:**
    ```sh
    javac -d bin src/Main/*.java
    java -cp bin Main.Engine
    ```

## 🏗 Arquitectura

El proyecto sigue una arquitectura modular con las siguientes clases principales:

- **Engine**: Controlador principal que coordina todas las operaciones.
- **DatabaseConnection**: Gestiona la conexión a la base de datos (Singleton).
- **D_InventoryManager**: Gestión de inventario.
- **D_UpdateProduct**: Actualización de productos.
- **D_SupplierManagment**: Administración de proveedores.
- **D_AddProduct**: Adición de nuevos productos.
- **D_Order**: Gestión de pedidos.
- **D_Sell**: Procesamiento de ventas.
- **D_Create**: Creación y eliminación de tablas.
- **D_Stadistic**: Generación de estadísticas.

## 📌 Casos de Uso

### Gestión de Inventario

```java
D_InventoryManager invMan = new D_InventoryManager();
invMan.getAll(); // Muestra todos los productos en el inventario
```

### Actualización de Productos

```java
D_UpdateProduct updtPdct = new D_UpdateProduct();
int idProduct = updtPdct.setUpdateProduct("Manzana");
if (idProduct > 0) {
    updtPdct.getUpdateProduct(idProduct, 1.99f, 100f, Date.valueOf("2024-12-31"));
}
```

### Gestión de Proveedores

```java
D_SupplierManagment suppMan = new D_SupplierManagment();
suppMan.setAddSupplier("Proveedor1", "Contacto1", "Dirección1", "123456789");
```

### Procesamiento de Ventas

```java
D_Sell sell = new D_Sell();
sell.getCategory("fruta");
sell.setCart(1, 5.0f, 9.95f);
sell.purchaseBuy();
```

### Generación de Estadísticas

```java
D_Stadistic stats = new D_Stadistic();
stats.expensive(); // Muestra el producto más caro
```

## 💡 Ejemplo de Ejecución

```sh
java -cp bin Main.Engine
```

Al ejecutar el comando anterior, se iniciará el sistema y se mostrará el menú principal en la consola.

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

¡Gracias por usar **Fruit Store Sun and Ground**! Si tienes alguna pregunta o sugerencia, no dudes en abrir un issue o contactarnos.
