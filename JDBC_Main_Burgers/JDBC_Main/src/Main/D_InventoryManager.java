package Main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que gestiona el inventario en el sistema.
 */
public class D_InventoryManager {
	private Connection conn;

	/**
	 * Constructor de la clase D_InventoryManager.
	 * Inicializa la conexión a la base de datos.
	 */
	public D_InventoryManager() {
		conn = DatabaseConnection.getConnection();
	}

	/**
	 * Obtiene los productos no caducados del inventario.
	 */
	public void getNonExpired() {
		String query = "SELECT * FROM Alimentos WHERE fecha_caducidad > CURDATE()";
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {
			
			int index = 1;
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				float price = rs.getFloat("precio_x_kg");
				float stock = rs.getFloat("stock");
				Date fecha_caducidad = rs.getDate("fecha_caducidad");
				
				System.out.println("No. " + index + "\n - Name: " + nombre + "\n - Category: " + categoria + "\n - Price per kilo: " + price + " €\n - Stock: " + stock + " KG\n - Expiration Date: " + fecha_caducidad + "\n");
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene los productos caducados del inventario.
	 */
	public void getExpired() {
		String query = "SELECT * FROM Alimentos WHERE fecha_caducidad < CURDATE();";
		
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {
			
			int index = 1;
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				float price = rs.getFloat("precio_x_kg");
				float stock = rs.getFloat("stock");
				Date fecha_caducidad = rs.getDate("fecha_caducidad");
				
				System.out.println("No. " + index + "\n - Name: " + nombre + "\n - Category: " + categoria + "\n - Price per kilo: " + price + " €\n - Stock: " + stock + " KG\n - Expiration Date: " + fecha_caducidad + "\n");
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene todos los productos del inventario.
	 */
	public void getAll() {
		String query = "SELECT * FROM Alimentos";
		
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			int index = 1;
			while (rs.next()) {		
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				float price = rs.getFloat("precio_x_kg");
				float stock = rs.getFloat("stock");
				Date fecha_caducidad = rs.getDate("fecha_caducidad");
				
				System.out.println("No. " + index + "\n - Name: " + nombre + "\n - Category: " + categoria + "\n - Price per kilo: " + price + " €\n - Stock: " + stock + " KG\n - Expiration Date: " + fecha_caducidad + "\n");
				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
