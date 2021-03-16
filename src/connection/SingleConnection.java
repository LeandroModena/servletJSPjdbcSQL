package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Responsável por fazer as conexões com o banco de dados 
 * 
  */

public class SingleConnection {
	
	private static String db = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(db, user, password);
				connection.setAutoCommit(false);
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Error connecting to the database");
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
}
