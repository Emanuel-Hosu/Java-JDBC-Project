package Main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class D_UpdateProduct {
	private Connection conn;
	private D_InventoryManager invMan;
	
	public D_UpdateProduct() {
		conn = DatabaseConnection.getConnection();
		invMan = new D_InventoryManager();
	}

	public int setUpdateProduct(String _product_name) {
		String foundedName = _product_name;
		int id = -1;
		String query = "SELECT id_alimento, nombre FROM Alimentos WHERE nombre = ?"; // PASADO POR PARAMETRO
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, _product_name); // INSERTA EL PARAMETRO EN EL PRIMER ? QUE ENCUENTRA

			try (ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					id = rs.getInt("id_alimento");
				}
				// SI RS DEVUELVE EL NOMBRE EXACTO QUE BUSCA EL USUARIO, SIGNIFICA QUE ESTA EN LA 
				// BASE DE DATOS Y ENTOCES DEVUELVE EL ID DEL PRODUCTO LA PK, ESTA SERA NECESARIA 
				// PARA EL .getUpdateProduct()
				if (foundedName.toLowerCase().equals(_product_name.toLowerCase()) && id > 0) { 
					System.out.println("Product FOUND " + foundedName.toUpperCase());
					return id;
				}else {
					System.out.println("Product dosen't exist in BBDD");
					return -1;
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("An error occurred while trying to find the name in setUpdateProduct() of the D_UpdateProduct.java class");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
	
	public float getPriceByID(int _idAlimento) {
		String query = "SELECT precio_x_kg FROM Alimentos WHERE id_alimento = ?";
		float price = 0;
		
		try (PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, _idAlimento);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					price = rs.getFloat("precio_x_kg");
				}
				
				return price;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return price;
	}
	
	public float getStockByID(int _idAlimento) {
		String query = "SELECT stock FROM Alimentos WHERE id_alimento = ?";
		float stock = 0;
		
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, _idAlimento);
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					stock = rs.getFloat("stock");
				}
				
				return stock;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stock;	
	}
	
	public Date getDateByID(int _idAlimento) {
		String query = "SELECT fecha_caducidad FROM Alimentos WHERE id_alimento = ?";
		Date fecha = null;
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, _idAlimento);
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					fecha = rs.getDate("fecha_caducidad");
				}
				
				return fecha;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fecha;
	}
	
	public String getNameByID(int _idAlimento) {
		String query = "SELECT nombre FROM Alimentos WHERE id_alimento = ?";
		String nombre = "";
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, _idAlimento);
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					nombre = rs.getString("nombre");
				}
				
				return nombre;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombre;
	}
	
	public void getUpdateProduct(int _id_product, float _price, float _stock, Date _fecha) {
		String query = "UPDATE Alimentos SET precio_x_kg = ?, stock = ?, fecha_caducidad = ? WHERE id_alimento = ?;";
		
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setFloat(1, _price);
			ps.setFloat(2, _stock);
			ps.setDate(3, _fecha);
			ps.setInt(4, _id_product);
			
			ps.executeUpdate();
			System.out.println("Succesful, product UPDATED to BBDD");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("An ERROR has ocurred while trying to bring the fata from the BBDD in getUpdateProduct");
			e.printStackTrace();
		}
		
	}
}
