package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que gestiona las ventas en el sistema.
 */
public class D_Sell {
	private HashMap<Integer, Float> carrito;
	private ArrayList<Float> stock_pair_carrito;
	private Connection conn;
	
	/**
	 * Constructor de la clase D_Sell.
	 * Inicializa la conexión a la base de datos y las estructuras de datos para el carrito.
	 */
	public D_Sell() {
		conn = DatabaseConnection.getConnection();
		carrito = new HashMap<Integer, Float>();
		stock_pair_carrito = new ArrayList<Float>();
	}
	
	/**
	 * Obtiene los productos de una categoría específica que no han caducado.
	 * 
	 * @param _category Categoría de los productos a buscar.
	 */
	public void getCategory(String _category) {
		String query = "SELECT nombre, precio_x_kg FROM Alimentos WHERE categoria = ? AND fecha_caducidad > CURDATE()";
		
		try (PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1, _category);
			
			try (ResultSet rs = ps.executeQuery()){
				int index = 1;
				while (rs.next()) {
					String nombre = rs.getString("nombre");
					String price = rs.getString("precio_x_kg");
					
					System.out.println("\nNro. " + index);
					System.out.println("Name: " + nombre + "\nPrice: " + price + " €");
					index ++;
				}
			}catch(SQLException e) {
				System.out.println("ERROR, has ocurred menwhile in RESULT SET. Trying to get category in getCategory()");
			}
			
		} catch (SQLException e) {
			System.out.println("ERROR, has ocurred menwhile trying to get category in getCategory()");
		}
	}
	
	/**
	 * Verifica si un producto existe en la base de datos.
	 * 
	 * @param _productName Nombre del producto a buscar.
	 * @return true si el producto existe, false en caso contrario.
	 */
	public boolean getExistProductName(String _productName) {
		boolean encontrado = false;
		String productName = "";
		String query = "SELECT nombre FROM Alimentos WHERE nombre = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1, _productName);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					productName = rs.getString("nombre");
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR while trying to do a preparedStatement in getExistProductName class D_Sell");
			e.printStackTrace();
		}
		
		// UNA VEZ SE HA EJECUTADO LA QUERY Y SE HA RECOGIDO O NO EL NOMBRE (EN CASO DE QUE NO EXISTA)
		if (productName.toLowerCase().equals(_productName.toLowerCase())) {
			encontrado = true;
			return encontrado;
		}else {
			return encontrado; // RETORNA FALSE SIEMPRE QUE NO LO ENCUENTRA
		}
	}
	
	/**
	 * Obtiene el ID de un producto basado en su nombre.
	 * 
	 * @param _name Nombre del producto.
	 * @return ID del producto.
	 */
	public int getIdProductByName(String _name) {
		String query = "SELECT id_alimento FROM Alimentos WHERE nombre = ?";
		int id_producto = -1;
				
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setString(1, _name);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					id_producto = rs.getInt("id_alimento");
					return id_producto;
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR while trying to do a preparedStatement in getIdProductByName class D_Sell");
			e.printStackTrace();
		}
		return id_producto;
	}
	
	/**
	 * Obtiene el precio de un producto basado en su ID.
	 * 
	 * @param _idProducto ID del producto.
	 * @return Precio del producto.
	 */
	public float getPriceById(int _idProducto) {
		String query = "SELECT precio_x_kg FROM Alimentos WHERE id_alimento = ?";
		float product_price = -1;
				
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, _idProducto);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					product_price = rs.getInt("precio_x_kg");
					return product_price;
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR while trying to do a preparedStatement in getPriceById class D_Sell");
			e.printStackTrace();
		}
		return product_price;
	}
	
	/**
	 * Obtiene el stock de un producto basado en su ID.
	 * 
	 * @param _idProducto ID del producto.
	 * @return Stock del producto.
	 */
	public float getStockById(int _idProducto) {
		String query = "SELECT stock FROM Alimentos WHERE id_alimento = ?";
		float stock_product = -1;
				
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, _idProducto);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					stock_product = rs.getInt("stock");
					return stock_product;
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR while trying to do a preparedStatement in getPriceById class D_Sell");
			e.printStackTrace();
		}
		return stock_product;
	}
	
	/**
	 * Obtiene el nombre de un producto basado en su ID.
	 * 
	 * @param _idProduct ID del producto.
	 * @return Nombre del producto.
	 */
	public String getNameProductById(int _idProduct) {
		String query = "SELECT nombre FROM Alimentos WHERE id_alimento = ?";
		String nombreAlimento = "";
		
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setInt(1, _idProduct);
			
			try (ResultSet rs = ps.executeQuery()){
				while (rs.next()) {
					nombreAlimento = rs.getString("nombre");
					return nombreAlimento;
				}
			}
		} catch (SQLException e) {
			System.out.println("ERROR while trying to do a preparedStatement in getPriceById class D_Sell");
			e.printStackTrace();
		}
		return nombreAlimento;
	}
	
	/**
	 * Añade un producto al carrito y actualiza el stock en la base de datos.
	 * 
	 * @param _idProducto ID del producto.
	 * @param _stock Cantidad de producto a añadir.
	 * @param _subtotal Subtotal del producto.
	 */
	public void setCart(Integer _idProducto, float _stock, float _subtotal) {
		String nombreAlimento = getNameProductById(_idProducto);
		String query = "UPDATE Alimentos SET stock = stock - ? WHERE id_alimento = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setFloat(1, _stock);
			ps.setInt(2, _idProducto);
			
			carrito.put(_idProducto, _subtotal);
			stock_pair_carrito.add(_stock); // ARRAY LIST PARA QUE VAYA A LA PAR QUE EL HASHMAP
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("ERROR while trying to do a preparedStatement in getPriceById class D_Sell");
			e.printStackTrace();
		}
	}
	
	/**
	 * Procesa la compra y registra la venta en la base de datos.
	 */
	public void purchaseBuy() { // UNA VEZ EL USUARIO LLAMA A ESTE METODO SIGNFICA QUE HAY QUE INSERTAR LOS DATOS EN VENTA
		// CONSEGUIMOS EL MONTO TOTAL DE TODO LO QUE HAY EN EL CARRITO JEJE GOD
		float montoTotal = 0; // 
        for (float precio : carrito.values()) {
        	montoTotal += precio;
        }
        
        String query = "INSERT INTO Ventas (fecha_venta, monto_total) VALUES (?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(query)){
			ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			ps.setFloat(2, montoTotal);
			
			ps.executeUpdate();
			System.out.println("- - - - FRUIT STORE SUN AND GROUND - - - -\n");
			System.out.println("PURCHASED sale, TOTAL: " + montoTotal + " €");
			System.out.println("\n - - - - - - - - THANK YOU - - - - - - - - \n");
		} catch (SQLException e) {
			System.out.println("ERROR while trying to do a preparedStatement in purchaseBuy() class D_Sell");
			e.printStackTrace();
		}
        
        setSellDetails();
	}
	
	/**
	 * Registra los detalles de la venta en la base de datos.
	 */
	public void setSellDetails() {
	    String query = "INSERT INTO Detalles_Venta (id_venta, id_alimento, cantidad, subtotal) VALUES (?, ?, ?, ?)";
	    
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        int idVenta = getLastSaleId();
	        
	        int index = 0; 
	        for (HashMap.Entry<Integer, Float> entry : carrito.entrySet()) {
	            Integer idProducto = entry.getKey(); 
	            Float subtotal = entry.getValue();  
	            Float cantidad = stock_pair_carrito.get(index);
	            
	            ps.setInt(1, idVenta);     
	            ps.setInt(2, idProducto);  
	            ps.setFloat(3, cantidad); 
	            ps.setFloat(4, subtotal);  
	            
	            ps.executeUpdate();
	            index++;
	        }
	      
	    } catch (SQLException e) {
	        System.out.println("An error has occurred in setSellDetails of the D_Sell class");
	    }
	}

	/**
	 * Obtiene el ID de la última venta registrada.
	 * 
	 * @return ID de la última venta.
	 */
	public int getLastSaleId() {
	    String query = "SELECT MAX(id_venta) AS last_id FROM Ventas";
	    int lastId = -1;
	    
	    try (PreparedStatement ps = conn.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        if (rs.next()) {
	            lastId = rs.getInt("last_id");
	        }
	    } catch (SQLException e) {
	        System.out.println("An error has occurred in getLastSaleId of the D_Sell class");
	    }
	    
	    return lastId;
	}

}
