package dominio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    public static Connection getConnection() {
        String jdbcURL = "jdbc:postgresql://containers-us-west-87.railway.app:6608/railway";
        String username = "postgres";
        String password = "9c5Wbzl5pvPdaca9fgpd";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
            } else {
                System.out.println("Error al conectar a la base de datos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}