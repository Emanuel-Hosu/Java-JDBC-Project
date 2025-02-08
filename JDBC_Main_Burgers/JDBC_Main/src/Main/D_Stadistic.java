package Main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class D_Stadistic {
	private Connection conn;

	public D_Stadistic() {
		conn = DatabaseConnection.getConnection();
	}

	public void expensive() {
		String query = "SELECT * FROM alimentos ORDER BY precio_x_kg DESC LIMIT 1";
		try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				float price = rs.getFloat("precio_x_kg");
				float stock = rs.getFloat("stock");
				Date fecha_caducidad = rs.getDate("fecha_caducidad");
				// System.out.println(" | " + index + ". | " + nombre + " | " + categoria + " |
				// " + price + " | " + stock + " | " + fecha_caducidad + " | ");
				System.out.println("Name: " + nombre + "\n - Category: " + categoria + "\n - Price per kilo: " + price
						+ " €\n - Stock: " + stock + " KG\n - Expiration Date: " + fecha_caducidad + "\n");
			}
		} catch (SQLException e) {
			System.out.println("No results on expensive method");
		}
	}

	public void cheaper() {
		String query = "SELECT * FROM alimentos ORDER BY precio_x_kg ASC LIMIT 1";
		try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String categoria = rs.getString("categoria");
				float price = rs.getFloat("precio_x_kg");
				float stock = rs.getFloat("stock");
				Date fecha_caducidad = rs.getDate("fecha_caducidad");
				System.out.println("Name: " + nombre + "\n - Category: " + categoria + "\n - Price per kilo: " + price
						+ " €\n - Stock: " + stock + " KG\n - Expiration Date: " + fecha_caducidad + "\n");
			}
		} catch (SQLException e) {
			System.out.println("No results on cheaper method");
		}
	}

	public void bestSellings() {
		String query = "SELECT * from ventas ORDER BY monto_total DESC LIMIT 3";
		try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			int index = 1;
			while (rs.next()) {
				int id = rs.getInt("id_venta");
				Date fecha_venta = rs.getDate("fecha_venta");
				float total = rs.getFloat("monto_total");
				System.out.println("Top: " + index + "\n - Id: " + id + "\n - Last Date: " + fecha_venta
						+ "\n - Total price: " + total);
				index++;
			}
		} catch (SQLException e) {
			System.out.println("Couldnt show the top sellings");
		}
	}

	public void totalPrice() {
		String query = "SELECT SUM(precio_x_kg * stock) AS total_inventario FROM Alimentos";
		try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				float total = rs.getFloat("total_inventario");
				System.out.println("Total Price of the inventory: " + total + "$");
			}
		} catch (SQLException e) {
			System.out.println("Couldnt show total price of the inventory");
		}
	}

	public void sellDetails() {
		String query = "SELECT * FROM detalles_venta";
		try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id_detalle = rs.getInt("id_detalle");
				int id_venta = rs.getInt("id_venta");
				int id_alimento = rs.getInt("id_alimento");
				float cantidad = rs.getFloat("cantidad");
				float subtotal = rs.getFloat("subtotal");
				System.out.println(
						"Log Sells:\n - Id Log: " + id_detalle + "\n - Id Sell: " + id_venta + "\n - Id Product: "
								+ id_alimento + "\n - Quantity: " + cantidad + "\n - Subtotal: " + subtotal);
			}
		} catch (SQLException e) {
			System.out.println("Couldnt show Sell Details");
		}
	}
}
