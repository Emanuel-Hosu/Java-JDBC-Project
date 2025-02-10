package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que gestiona la administración de proveedores en la base de datos.
 */
public class D_SupplierManagment {
	private Connection conn;
	
	/**
	 * Constructor de la clase D_SupplierManagment.
	 * Inicializa la conexión a la base de datos.
	 */
	public D_SupplierManagment() {
		conn = DatabaseConnection.getConnection();
	}
	
	/**
	 * Añade un nuevo proveedor a la base de datos.
	 * 
	 * @param _supplierName Nombre del proveedor.
	 * @param _contactName Nombre del contacto del proveedor.
	 * @param _adress Dirección del proveedor.
	 * @param _phone Teléfono del proveedor.
	 */
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
	
	/**
	 * Verifica si un proveedor existe en la base de datos.
	 * 
	 * @param _supplierName Nombre del proveedor a buscar.
	 * @return true si el proveedor existe, false en caso contrario.
	 */
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
	
	/**
	 * Elimina un proveedor de la base de datos.
	 * 
	 * @param _supplierName Nombre del proveedor a eliminar.
	 */
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
	
	/**
	 * Obtiene el nombre del contacto de un proveedor basado en su nombre.
	 * 
	 * @param _supplierName Nombre del proveedor.
	 * @return Nombre del contacto del proveedor.
	 */
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
	
	/**
	 * Obtiene la dirección de un proveedor basado en su nombre.
	 * 
	 * @param _supplierName Nombre del proveedor.
	 * @return Dirección del proveedor.
	 */
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
	
	/**
	 * Obtiene el teléfono de un proveedor basado en su nombre.
	 * 
	 * @param _supplierName Nombre del proveedor.
	 * @return Teléfono del proveedor.
	 */
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
	
	/**
	 * Obtiene el ID de un proveedor basado en su nombre.
	 * 
	 * @param _supplierName Nombre del proveedor.
	 * @return ID del proveedor.
	 */
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
	
	/**
	 * Actualiza los detalles de un proveedor en la base de datos.
	 * 
	 * @param _supplierName Nombre del proveedor.
	 * @param _contact Nombre del contacto del proveedor.
	 * @param _adress Dirección del proveedor.
	 * @param _telefono Teléfono del proveedor.
	 */
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
