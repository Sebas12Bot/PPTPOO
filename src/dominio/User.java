package dominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private Connection connection;
    private int id;
    private String username;
    private int victorias;
    private int derrotas;
    private int empates;

    public User() {
        connection = DataBase.getConnection();
    }

    public User(int id, String username, int victorias, int derrotas, int empates) {
        this.id = id;
        this.username = username;
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.empates = empates;
        connection = DataBase.getConnection();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setvictorias(int victorias) {
        this.victorias = victorias;
    }

    public void setderrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public void setempates(int empates) {
        this.empates = empates;
    }

    public void crearUsuario(String username) {
        String sqlInsertUsuario = "INSERT INTO usuarios (username) VALUES (?)";
        String sqlInsertPuntuacion = "INSERT INTO puntuaciones (id_usuario, victories, defeats, draws) VALUES (?, 0, 0, 0)";

        try {
            PreparedStatement statementUsuario = connection.prepareStatement(sqlInsertUsuario);
            statementUsuario.setString(1, username);
            statementUsuario.executeUpdate();
            String sqlObtenerID = "SELECT LAST_INSERT_ID() AS id";
            PreparedStatement statementID = connection.prepareStatement(sqlObtenerID);
            ResultSet resultSet = statementID.executeQuery();

            int userId = 0;
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }

            PreparedStatement statementPuntuacion = connection.prepareStatement(sqlInsertPuntuacion);
            statementPuntuacion.setInt(1, userId);
            statementPuntuacion.executeUpdate();

            System.out.println("Usuario creado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User obtenerUsuarioPorUsername(String username) {
        String sql = "SELECT u.id, u.username, p.victorias, p.derrotas, p.empates FROM usuarios u " +
                "LEFT JOIN puntuaciones p ON u.id = p.id_usuario WHERE u.username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String foundUsername = resultSet.getString("username");
                int userVictorias = resultSet.getInt("victorias");
                int userDerrotas = resultSet.getInt("derrotas");
                int userEmpates = resultSet.getInt("empates");
                return new User(userId, foundUsername, userVictorias, userDerrotas, userEmpates);
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}