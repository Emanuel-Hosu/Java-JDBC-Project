# README - Base de Datos para Gestión de Alimentos y Proveedores

## Descripción
Este proyecto consiste en la creación de una base de datos en MySQL para gestionar alimentos, proveedores, pedidos y ventas. Se incluyen las sentencias SQL para la creación de tablas y la inserción de datos de prueba.

## Estructura de la Base de Datos
La base de datos está compuesta por las siguientes tablas:

1. **Alimentos**: Contiene los alimentos con su información básica como nombre, categoría, precio, stock y fecha de caducidad.
2. **Proveedores**: Almacena información de los proveedores, incluyendo su nombre, contacto, dirección y teléfono.
3. **Alimentos_Proveedores**: Tabla intermedia para establecer la relación entre alimentos y proveedores.
4. **Pedidos**: Registra los pedidos realizados a los proveedores con la fecha y los montos.
5. **Detalles_Pedido**: Contiene los detalles de cada pedido, especificando los alimentos comprados.
6. **Ventas**: Registra las ventas de los alimentos.
7. **Detalles_Venta**: Contiene los detalles de cada venta, especificando los alimentos vendidos.

## Creación de la Base de Datos y Tablas
Para crear la base de datos y las tablas, ejecutar el siguiente SQL:

```sql
USE mibasededatos;

CREATE TABLE Alimentos (
    id_alimento INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria ENUM('fruta', 'verdura', 'fruto_seco') NOT NULL,
    precio_x_kg DECIMAL(10,2) NOT NULL,
    stock DECIMAL(10,2) NOT NULL,
    fecha_caducidad DATE NOT NULL
);

CREATE TABLE Proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(9) NOT NULL
);

CREATE TABLE Alimentos_Proveedores (
    id_alimento INT,
    id_proveedor INT,
    PRIMARY KEY (id_alimento, id_proveedor),
    FOREIGN KEY (id_alimento) REFERENCES Alimentos(id_alimento) ON DELETE CASCADE,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor) ON DELETE CASCADE
);

CREATE TABLE Pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_proveedor INT NOT NULL,
    fecha_pedido DATE NOT NULL,
    cantidad_total DECIMAL(10,2) NOT NULL,
    monto_total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor) ON DELETE CASCADE
);

CREATE TABLE Detalles_Pedido (
    id_pedido INT NOT NULL,
    id_alimento INT NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_pedido, id_alimento),
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_alimento) REFERENCES Alimentos(id_alimento)
);

CREATE TABLE Ventas (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta DATE NOT NULL,
    monto_total DECIMAL(10,2) NOT NULL
);

CREATE TABLE Detalles_Venta (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_alimento INT NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES Ventas(id_venta),
    FOREIGN KEY (id_alimento) REFERENCES Alimentos(id_alimento)
);
```

## Inserción de Datos de Prueba

### Insertar Alimentos
```sql
INSERT INTO Alimentos (nombre, categoria, precio_x_kg, stock, fecha_caducidad) VALUES
('Manzana', 'fruta', 2.50, 100.00, '2025-05-01'),
('Banana', 'fruta', 1.80, 120.00, '2025-03-15'),
('Zanahoria', 'verdura', 1.20, 200.00, '2025-02-10'),
('Lechuga', 'verdura', 1.50, 80.00, '2025-03-01');
```

### Insertar Proveedores
```sql
INSERT INTO Proveedores (nombre, contacto, direccion, telefono) VALUES  
('Frutas del Valle', 'Juan Pérez', 'Calle Falsa 123', '123456789'),  
('Hortalizas Frescas', 'Ana López', 'Avenida del Sol 456', '987654321');  
```

### Insertar Relación Alimentos - Proveedores
```sql
INSERT INTO Alimentos_Proveedores (id_alimento, id_proveedor) VALUES
(1, 1),
(2, 2);
```

### Insertar Pedidos
```sql
INSERT INTO Pedidos (id_proveedor, fecha_pedido, cantidad_total, monto_total) VALUES
(1, '2025-01-15', 200.00, 500.00);
```

### Insertar Detalles de Pedidos
```sql
INSERT INTO Detalles_Pedido (id_pedido, id_alimento, cantidad, precio) VALUES
(1, 1, 50.00, 2.50);
```

### Insertar Ventas
```sql
INSERT INTO Ventas (fecha_venta, monto_total) VALUES
('2025-01-16', 800.00);
```

### Insertar Detalles de Ventas
```sql
INSERT INTO Detalles_Venta (id_venta, id_alimento, cantidad, subtotal) VALUES
(1, 1, 30.00, 75.00);
```

## Consideraciones
- Se ha aplicado integridad referencial con claves foráneas para garantizar la consistencia de los datos.
- La eliminación en cascada se ha habilitado en las relaciones para evitar registros huérfanos.
- Se han predefinido categorías de alimentos como `fruta`, `verdura` y `fruto_seco` mediante ENUM.

## Uso
1. Ejecutar las sentencias SQL en un servidor MySQL.
2. Verificar la correcta inserción de datos mediante `SELECT * FROM <tabla>;`.
3. Modificar o expandir la base de datos según sea necesario.

¡Base de datos lista para gestionar la información de alimentos y proveedores!

