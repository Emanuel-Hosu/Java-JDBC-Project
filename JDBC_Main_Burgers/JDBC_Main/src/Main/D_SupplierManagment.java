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
		if (supplierName.toLowerCase().equals(_supplierName)) {
			encontrado = true;
			return encontrado;
		}else {
			return encontrado; // RETORNA FALSE SIEMPRE QUE NO LO ENCUENTRA
		}
	}
}
