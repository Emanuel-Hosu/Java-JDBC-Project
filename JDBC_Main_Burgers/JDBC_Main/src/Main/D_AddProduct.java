package Main;

import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class D_AddProduct {
	private Connection conn;

	public D_AddProduct() {
		conn = DatabaseConnection.getConnection();
	}

	public boolean Add(String nombre, String categoria, int _id) {
		//esta i la devolveremos con -1 si el producto ya esta en la base de datos o 1 si se ha podido añadir
		boolean encontrado = false;
		// Select para ver si existe ya el nombre
		String check = "SELECT nombre FROM alimentos WHERE nombre = ?";
		try (PreparedStatement checkStmt = conn.prepareStatement(check)) {
			checkStmt.setString(1, nombre);
			ResultSet rs = checkStmt.executeQuery();

			// Si existe ya en la base de datos se devuelve -1
			if (rs.next()) {
				System.out.println("Product already in the database");
				encontrado = true;
				return encontrado;
			} else {
				// Insertamos el producto
				String insert = "INSERT INTO alimentos (nombre, categoria, precio_x_kg, stock , fecha_caducidad) VALUES (?,?,?,?,?)";
				try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
					insertStmt.setString(1, nombre);
					insertStmt.setString(2, categoria);
					insertStmt.setFloat(3, 0);
					insertStmt.setFloat(4, 0);
					//para poner en default la fecha hacemos lo siguiente, ya que no puede ser null
					LocalDate fechaCaducidad = LocalDate.of(9999, 12, 31);
					insertStmt.setDate(5, Date.valueOf(fechaCaducidad));
					
					insertStmt.executeUpdate();
					this.alimentos_Proveedores(nombre, _id);
					System.out.println("Product added succesfully");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: couldnt add the product");
		}
		return encontrado;
	}

	public void alimentos_Proveedores(String nombre, int _id) {
		// buscamos el id del alimento que hemos añadido
		String query = "SELECT id_alimento FROM alimentos WHERE nombre = ?";
		int id = 0;
		try (PreparedStatement checkStmt = conn.prepareStatement(query)) {
			checkStmt.setString(1, nombre);
			ResultSet rs = checkStmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id_alimento");
			}
			// insertamos el id del nuevo alimento al proveedor que nos ha dicho el usuario
			String insert = "INSERT INTO alimentos_proveedores (id_alimento, id_proveedor) VALUES (?,?)";
			try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
				//ESTE ID ES PARA ID_ALIMENTO
				insertStmt.setInt(1, id);
				//ESTE _ID ES PARA ID_PROVEEDOR
				insertStmt.setInt(2, _id);
				insertStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getProveedores() {
		String query = "SELECT * FROM Proveedores";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				int id = rs.getInt("id_proveedor");
				String nombre = rs.getString("nombre");
				String contacto = rs.getString("contacto");
				String direccion = rs.getString("direccion");
				System.out.println("Id: " + id + "\n - Name: " + nombre + "\n - Contact: " + contacto + "\n - Address: "
						+ direccion);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
