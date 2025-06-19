package Conexion;

import java.sql.*;

public class conexion {
    private static final String URL = "jdbc:mysql://161.132.48.53:3306/catering_upt?useSSL=false&serverTimezone=UTC";
    private static final String USER = "progra3";
    private static final String PASSWORD = "progra2025";

  public static Connection getConnection() throws SQLException {
    Connection con = null;
    try {
        // Carga el driver de MySQL explícitamente
        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection(URL, USER, PASSWORD);
        if (con != null) {
            System.out.println("Conexión exitosa a la base de datos.");
        }
    } catch (ClassNotFoundException e) {
        System.out.println("Error al cargar el driver JDBC: " + e.getMessage());
        e.printStackTrace();
    } catch (SQLException e) {
        System.out.println("Error de conexión: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
    return con;
}

}
