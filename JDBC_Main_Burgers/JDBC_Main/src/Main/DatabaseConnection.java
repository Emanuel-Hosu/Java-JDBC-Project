package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	// Datos de conexion a la base de datos
	private static final String URL = "jdbc:mysql://localhost:3306/mibasededatos";
	private static final String USER = "root";
	private static final String PASSWORD = "root1234";
	
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
	
	// TEST DE CONEXION
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
