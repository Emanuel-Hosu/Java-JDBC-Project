package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class D_SupplierManagment {
	private Connection conn;
	
	public D_SupplierManagment() {
		conn = DatabaseConnection.getConnection();
	}
	
	// PASARLE EL NOMBRE PROVEEDOR POR PARAMETRO
	public void setAddSupplier(String _supplierName, String _contactName, String _adress, String _phone) {
		String query = "INSERT INTO Proveedores SET nombre = ?, contacto = ?, direccion = ?, telefono = ?";
		
		try (PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1, _supplierName);
			ps.setString(2, _contactName);
			ps.setString(3, _adress);
			ps.setString(4, _phone);
			
			ps.executeUpdate();
			System.out.println("SUCCESFUL, new SUPPLIER " +_supplierName + " added into BBDD.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR, has ocurren meanwhile trying to INSERT INTO Proveedores DATA, setAddSuppliers(=");
			e.printStackTrace();
		}
	}
	
	public boolean getExistSupplierName(String _supplierName) {
		boolean encontrado = false;
		String supplierName = "";
		String query = "SELECT nombre FROM Proveedores WHERE nombre = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1, _supplierName);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					supplierName = rs.getString("nombre");
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR while trying to do a preparedStatement in D_SupplierManagment");
			e.printStackTrace();
		}
		
		// UNA VEZ SE HA EJECUTADO LA QUERY Y SE HA RECOGIDO O NO EL NOMBRE (EN CASO DE QUE NO EXISTA)
		if (supplierName.toLowerCase().equals(_supplierName.toLowerCase())) {
			encontrado = true;
			return encontrado;
		}else {
			return encontrado; // RETORNA FALSE SIEMPRE QUE NO LO ENCUENTRA
		}
	}
	
	public void setDeleteSupplier(String _supplierName) {
		int idSupplier = getSupplierIdByString(_supplierName);
		String query = "DELETE FROM Proveedores WHERE id_proveedor = ?";
		
		try (PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, idSupplier);
			ps.executeUpdate();
			System.out.println("SUCCESFUL " + _supplierName + " DELETED.");
		} catch (SQLException e) {
			System.out.println("ERROR has ocurred menwhike trying to DELETE SUPPLIER on setDeleteSupplier()");
		}
	}
	
	public String getSupplierContactNameById(String _supplierName) {
		int idSupplier = getSupplierIdByString(_supplierName);
		String query = "SELECT contacto FROM Proveedores WHERE id_proveedor = ?";
		String returnedContact = "";
		
		try (PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, idSupplier);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					returnedContact = rs.getString("contacto");
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR has ocurred menwhike trying to DELETE SUPPLIER on getSupplierContactNameById()");
		}
		
		return returnedContact;
	}
	
	
	public String getSupplierAdressById(String _supplierName) {
		int idSupplier = getSupplierIdByString(_supplierName);
		String query = "SELECT direccion FROM Proveedores WHERE id_proveedor = ?";
		String returnedAdress = "";
		
		try (PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, idSupplier);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					returnedAdress = rs.getString("direccion");
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR has ocurred menwhike trying to DELETE SUPPLIER on getSupplierContactNameById()");
		}
		
		return returnedAdress;
	}
	
	public String getSupplierPhoneById(String _supplierName) {
		int idSupplier = getSupplierIdByString(_supplierName);
		String query = "SELECT telefono FROM Proveedores WHERE id_proveedor = ?";
		String returnedPhone = "";
		
		try (PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, idSupplier);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					returnedPhone = rs.getString("telefono");
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR has ocurred menwhike trying to DELETE SUPPLIER on getSupplierContactNameById()");
		}
		
		return returnedPhone;
	}
	
	public int getSupplierIdByString(String _supplierName) {
		String query = "SELECT id_proveedor FROM Proveedores WHERE nombre = ?";
		int id_proveedor = -1;
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1, _supplierName);
			
			try (ResultSet rs = ps.executeQuery()){
				
				while (rs.next()) {
					id_proveedor = rs.getInt("id_proveedor");
					return id_proveedor;
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ERROR has ocurred menwhike trying to DELETE SUPPLIER on getSupplierIdByString()");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR has ocurred menwhike trying to DELETE SUPPLIER on getSupplierIdByString()");
		}
		return id_proveedor;
	}
	
	public void setUpdateSuppliers(String _supplierName, String _contact, String _adress, String _telefono) {
		String query = "UPDATE Proveedores SET contacto = ?, direccion = ?, telefono = ? WHERE id_proveedor = ?;";
		int id_proveedor = getSupplierIdByString(_supplierName);
		
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1, _contact);
			ps.setString(2, _adress);
			ps.setString(3, _telefono);
			ps.setInt(4, id_proveedor);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("ERROR has ocurred menwhike trying to DELETE SUPPLIER on setUpdateSuppliers()");
		}
		
	}
}
