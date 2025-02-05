package Main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InventoryManager {
	private Connection conn;

	public InventoryManager() {
		// TODO Auto-generated constructor stub
		conn = DatabaseConnection.getConnection();
	}

	public void getNonExpired() { // MAYBE STRING?¿
		String query = "SELECT * FROM Alimentos WHERE fecha_caducidad > CURDATE()";
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query)){
			
			int index = 1;
			System.out.println("| No. | Name | Category | KG Price | KG Stock | Expiration date |");
			System.out.println("-----------------------------------------------------------------");
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				float price = rs.getFloat("precio_x_kg");
				float stock = rs.getFloat("stock");
				Date fecha_caducidad = rs.getDate("fecha_caducidad");
				
				
				//System.out.println(" | " + index + ". | " + nombre + " | " + categoria + " | " + price + " | " + stock + " | " + fecha_caducidad + " | ");
				System.out.println("No. " + index + "\n - Name: " + nombre + "\n - Category: " + categoria + "\n - Price per kilo: " + price + " €\n - Stock: " + stock + " KG\n - Expiration Date: " + fecha_caducidad + "\n");
				index ++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getExpired() {
		String query = "SELECT * FROM Alimentos WHERE fecha_caducidad < CURDATE();";
		
		try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query)){
			
			int index = 1;
			System.out.println("| No. | Name | Category | KG Price | KG Stock | Expiration date |");
			System.out.println("-----------------------------------------------------------------");
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				float price = rs.getFloat("precio_x_kg");
				float stock = rs.getFloat("stock");
				Date fecha_caducidad = rs.getDate("fecha_caducidad");
				
				System.out.println("No. " + index + "\n - Name: " + nombre + "\n - Category: " + categoria + "\n - Price per kilo: " + price + " €\n - Stock: " + stock + " KG\n - Expiration Date: " + fecha_caducidad + "\n");
				index ++;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	
	}

	public void getAll() {
		String query = "SELECT * FROM Alimentos";
		
		try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query)){

			int index = 1;
			System.out.println("| No. | Name | Category | KG Price | KG Stock | Expiration date |");
			System.out.println("-----------------------------------------------------------------");
			
			while (rs.next()) {		
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				float price = rs.getFloat("precio_x_kg");
				float stock = rs.getFloat("stock");
				Date fecha_caducidad = rs.getDate("fecha_caducidad");
				
				System.out.println("No. " + index + "\n - Name: " + nombre + "\n - Category: " + categoria + "\n - Price per kilo: " + price + " €\n - Stock: " + stock + " KG\n - Expiration Date: " + fecha_caducidad + "\n");
				index ++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
