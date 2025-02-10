package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que gestiona la creación y eliminación de tablas en la base de datos.
 */
public class D_Create {

	private Connection conn;
	// ESTE ARRAY TIENE EL ORDEN EN EL QUE SE TIENEN QUE ELIMINAR LAS TABLAS
	String[] tablasDelete = { "Alimentos_Proveedores", "Detalles_Pedido", "Detalles_Venta", "Pedidos", "Ventas",
			"Proveedores", "Alimentos" };
	// ESTE ARRAY TIENE EL ORDEN EN EL QUE SE TIENEN QUE CREAR LAS TABLAS
	String[] tablasCreate = { "Alimentos", "Proveedores", "Alimentos_Proveedores", "Pedidos", "Detalles_Pedido",
			"Ventas", "Detalles_Venta" };

	/**
	 * Constructor de la clase D_Create.
	 * Inicializa la conexión a la base de datos.
	 */
	public D_Create() {
		conn = DatabaseConnection.getConnection();
	}

	/**
	 * Ejecuta la creación o eliminación de tablas según la opción proporcionada.
	 * 
	 * @param opcion 1 para crear tablas, cualquier otro valor para eliminar tablas.
	 */
	public void system32(int opcion) {
		if (opcion == 1) {
			this.createTable();
		} else {
			this.delete();
		}
	}

	/**
	 * Elimina las tablas de la base de datos en el orden especificado.
	 */
	public void delete() {

		for (String tabla : this.tablasDelete) {
			// COMPRUEBA SI LA TABLA EXISTE
			if (tableExists(tabla)) {
				// Sentencia para eliminar la tabla
				String query = "DROP TABLE IF EXISTS " + tabla;
				try (PreparedStatement ps = conn.prepareStatement(query)) {
					ps.executeUpdate(query);
					System.out.println("Table " + tabla + " has been errased.");
				} catch (SQLException e) {
					System.out.println("Error trying to delete " + tabla);
				}
			} else {
				System.out.println("Table " + tabla + " doesnt exist or has already been deleted.");
			}
		}

	}

	/**
	 * Verifica si una tabla existe en la base de datos.
	 * 
	 * @param tabla Nombre de la tabla a verificar.
	 * @return true si la tabla existe, false en caso contrario.
	 */
	public boolean tableExists(String tabla) {
		String query = "SHOW TABLES LIKE ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, tabla);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			// Manejo de excepciones
		}
		return false;
	}

	/**
	 * Crea las tablas en la base de datos en el orden especificado.
	 */
	public void createTable() {

		for (String tabla : this.tablasCreate) {
			// VERIFICA SI LA TABLA EXISTE
			if (!tableExists(tabla)) {
				// CADA VEZ QUE VERIFIQUE SI DICHA TABLA EXISTE, VA IR CREANDO UNA A UNA CADA
				// TABLA, DANDOLE VALOR A QUERY DE CREAR LA TABLA QUE TOQUE
				String query = "";
				switch (tabla) {
				case "Alimentos":
					query = "CREATE TABLE Alimentos (" + "id_alimento INT PRIMARY KEY AUTO_INCREMENT, "
							+ "nombre VARCHAR(100) NOT NULL, "
							+ "categoria ENUM('fruta', 'verdura', 'fruto_seco') NOT NULL, "
							+ "precio_x_kg DECIMAL(10,2) NOT NULL, " + "stock DECIMAL(10,2) NOT NULL, "
							+ "fecha_caducidad DATE NOT NULL" + ");";
					break;
				case "Proveedores":
					query = "CREATE TABLE Proveedores (" + "id_proveedor INT AUTO_INCREMENT PRIMARY KEY, "
							+ "nombre VARCHAR(100) NOT NULL, " + "contacto VARCHAR(100) NOT NULL, "
							+ "direccion VARCHAR(200) NOT NULL" + "telefono VARCHAR(9) NOT NULL" + ");";
					break;
				case "Alimentos_Proveedores":
					query = "CREATE TABLE Alimentos_Proveedores (" + "id_alimento INT, " + "id_proveedor INT, "
							+ "PRIMARY KEY (id_alimento, id_proveedor), "
							+ "FOREIGN KEY (id_alimento) REFERENCES Alimentos(id_alimento) ON DELETE CASCADE, "
							+ "FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor) ON DELETE CASCADE"
							+ ");";
					break;
				case "Pedidos":
					query = "CREATE TABLE Pedidos (" + "id_pedido INT AUTO_INCREMENT PRIMARY KEY, "
							+ "id_proveedor INT NOT NULL, " + "fecha_pedido DATE NOT NULL, "
							+ "cantidad_total DECIMAL(10,2) NOT NULL, " + "monto_total DECIMAL(10,2) NOT NULL, "
							+ "FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor) ON DELETE CASCADE"
							+ ");";
					break;
				case "Detalles_Pedido":
					query = "CREATE TABLE Detalles_Pedido (" + "id_pedido INT NOT NULL, " + "id_alimento INT NOT NULL, "
							+ "cantidad DECIMAL(10,2) NOT NULL, " + "precio DECIMAL(10,2) NOT NULL, "
							+ "PRIMARY KEY (id_pedido, id_alimento), "
							+ "FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido) ON DELETE CASCADE, "
							+ "FOREIGN KEY (id_alimento) REFERENCES Alimentos(id_alimento)" + ");";
					break;
				case "Ventas":
					query = "CREATE TABLE Ventas (" + "id_venta INT AUTO_INCREMENT PRIMARY KEY, "
							+ "fecha_venta DATE NOT NULL, " + "monto_total DECIMAL(10,2) NOT NULL" + ");";
					break;
				case "Detalles_Venta":
					query = "CREATE TABLE Detalles_Venta (" + "id_detalle INT AUTO_INCREMENT PRIMARY KEY, "
							+ "id_venta INT NOT NULL, " + "id_alimento INT NOT NULL, "
							+ "cantidad DECIMAL(10,2) NOT NULL, " + "subtotal DECIMAL(10,2) NOT NULL, "
							+ "FOREIGN KEY (id_venta) REFERENCES Ventas(id_venta), "
							+ "FOREIGN KEY (id_alimento) REFERENCES Alimentos(id_alimento)" + ");";
					break;
				}

				try (PreparedStatement ps = conn.prepareStatement(query)) {
					ps.executeUpdate();
					System.out.println("Table " + tabla + " created.");
				} catch (SQLException e) {
					System.out.println("Error while trying to create " + tabla);
				}
			} else {
				System.out.println("Table " + tabla + " already exists.");
			}
		}
	}

}
