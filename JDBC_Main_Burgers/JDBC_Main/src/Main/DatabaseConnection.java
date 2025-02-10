package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión a la base de datos.
 * Implementa el patrón Singleton para asegurar una única instancia de conexión.
 */
public class DatabaseConnection {
	
	// Datos de conexion a la base de datos
	private static final String URL = "jdbc:mysql://localhost:3306/mibasededatos";
	private static final String USER = "root";
	private static final String PASSWORD = "Cm071111";
	
	/**
	 * Obtiene una conexión a la base de datos.
	 * 
	 * @return Connection objeto de conexión a la base de datos.
	 * @throws SQLException si ocurre un error al intentar conectar a la base de datos.
	 */
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("An error has ocurred while trying to connect to the data base");
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * Prueba la conexión a la base de datos.
	 * Imprime un mensaje indicando si la conexión fue exitosa o fallida.
	 */
	public static void testConnection() {
	    try (Connection conn = getConnection()) {
	        if (conn != null) {
	            System.out.println("Connected successfully to the database!");
	        } else {
	            System.out.println("Failed to establish a connection.");
	        }
	    } catch (SQLException e) {
	        System.out.println("An error occurred while trying to connect to the database.");
	        e.printStackTrace();
	    }
	}

}
