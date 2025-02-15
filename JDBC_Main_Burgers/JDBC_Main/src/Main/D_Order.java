package Main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase que gestiona los pedidos en el sistema.
 */
public class D_Order {
	private Connection conn;

	/**
	 * Constructor de la clase D_Order.
	 * Inicializa la conexión a la base de datos.
	 */
	public D_Order() {
		conn = DatabaseConnection.getConnection();
	}

	/**
	 * Establece un nuevo pedido en la base de datos.
	 * 
	 * @param nombre Nombre del producto a pedir.
	 * @param cantidad Cantidad del producto a pedir.
	 * @return true si el pedido se realizó con éxito, false en caso contrario.
	 */
	public boolean setOrder(String nombre, float cantidad) {
		
		boolean realizado = false;
		// QUERY PARA ENCONTRAR EL EL ID Y EL PRECIO DEL ALIMENTO QUE QUIERE PEDIR
		String query = "SELECT id_alimento, precio_x_kg FROM alimentos WHERE nombre = ?";
		float precio_x_kg = 0;
		int id_alimento = 0;
		int id_proveedor = 0;
		int id_pedido = 0;
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			// SI EXISTE EL ALIMENTO, COGEMOS EL ID DEL ALIMENTO Y SU PRECIO
			if (rs.next()) {
				id_alimento = rs.getInt("id_alimento");
				precio_x_kg = rs.getFloat("precio_x_kg");
				// LE PASAMOS EL ID_ALIMENTO PARA QUE NOS DEVUELVA EL ID DEL PROVEEDOR
				id_proveedor = getIdProveedor(id_alimento);
				// LE PASAMOS EL ID_PROVEEDOR Y LA CANTIDAD PARA QUE NOS DEVUELVA EL ID DEL
				// PEDIDO QUE HEMOS CREADO AL TERMINAR DE LLAMAR AL METODO
				id_pedido = getIdPedido(id_proveedor, cantidad, precio_x_kg);
				// INSERTAMOS EL NUEVO PEDIDO EN DETALLES PEDIDO
				setDetallesPedido(id_alimento, id_pedido, cantidad, precio_x_kg);
				// ACTUALIZAMOS EL ALIMENTO SEGUN EL PEDIDO QUE HEMOS HECHO
				String update = "UPDATE alimentos SET stock = stock + ?, fecha_caducidad = ? WHERE id_alimento = ?";
				try (PreparedStatement ps2 = conn.prepareStatement(update)) {
					LocalDate fechaActual = LocalDate.now().plusMonths(3);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					ps2.setFloat(1, cantidad);
					ps2.setDate(2, Date.valueOf(fechaActual.format(formatter)));
					ps2.setInt(3, id_alimento);
					ps2.executeUpdate();
					
					System.out.println("Product order and added to the stock");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				realizado = true;
				return realizado;
			} else {
				System.out.println("Product not founded");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return realizado;
	}

	/**
	 * Obtiene el ID del pedido basado en el ID del proveedor, la cantidad y el precio.
	 * 
	 * @param id_proveedor ID del proveedor.
	 * @param cantidad Cantidad del producto.
	 * @param precio Precio del producto.
	 * @return ID del pedido.
	 */
	public int getIdPedido(int id_proveedor, float cantidad, float precio) {
		String insert = "INSERT INTO pedidos (id_proveedor, fecha_pedido, cantidad_total, monto_total) VALUES (?,?,?,?)";
		LocalDate fechaActual = LocalDate.now();
		// Definir el formato de la fecha como 'año-mes-día'
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int id_pedido = 0;

		try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
			insertStmt.setInt(1, id_proveedor);
			insertStmt.setDate(2, Date.valueOf(fechaActual.format(formatter)));
			insertStmt.setFloat(3, cantidad);
			insertStmt.setFloat(4, (precio * cantidad));
			insertStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// OBTENEMOS LA PRIMARY KEY RECIEN CREADA
		id_pedido = getLastIdGenerated();
		return id_pedido;
	}

	/**
	 * Obtiene el último ID de pedido generado.
	 * 
	 * @return Último ID de pedido generado.
	 */
	public int getLastIdGenerated() {
		String query = "SELECT id_pedido FROM pedidos ORDER BY id_pedido DESC LIMIT 1";
		int id_pedido = -1;
		try(PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					id_pedido = rs.getInt("id_pedido");
					return id_pedido;
				}
		}catch(SQLException e) {
			System.out.println("An error has ocurred in method getLastIdGenerated");
		}
		return id_pedido;
	}
	
	/**
	 * Establece los detalles del pedido en la base de datos.
	 * 
	 * @param id_alimento ID del alimento.
	 * @param id_pedido ID del pedido.
	 * @param cantidad Cantidad del producto.
	 * @param precio Precio del producto.
	 */
	public void setDetallesPedido(int id_alimento, int id_pedido, float cantidad, float precio) {
		String insert = "INSERT INTO detalles_pedido (id_pedido, id_alimento, cantidad, precio) VALUES (?,?,?,?)";

		try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
			insertStmt.setInt(1, id_pedido);
			insertStmt.setInt(2, id_alimento);
			insertStmt.setFloat(3, cantidad);
			insertStmt.setFloat(4, precio);
			insertStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el ID del proveedor basado en el ID del alimento.
	 * 
	 * @param id_alimento ID del alimento.
	 * @return ID del proveedor.
	 */
	public int getIdProveedor(int id_alimento) {
	    //AP RELACIONA ALIMENTO PROVEEDORES COGEMOS EL ID QUE COINCIDADA EL EL ID DE ALIMENTO EN AMBAS TABLAS
	    String query = "SELECT ap.id_proveedor FROM alimentos_proveedores ap " +
	                   "INNER JOIN alimentos a ON a.id_alimento = ap.id_alimento " +
	                   "WHERE a.id_alimento = ?";
	    int id_proveedor = 0;

	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, id_alimento);  
	        ResultSet rs = ps.executeQuery();
	      
	        if (rs.next()) {
	            id_proveedor = rs.getInt("id_proveedor");
	            return id_proveedor;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return id_proveedor; 
	}
}
