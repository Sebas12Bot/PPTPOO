package dominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class User {
    private Connection connection;
    private String id;
    private String username;
    private int victorias;
    private int derrotas;
    private int empates;

    UUID uuid = UUID.randomUUID();
    public String id_user = uuid.toString();

    public User() {
        connection = DataBase.getConnection();
    }


    public User(String id, String username, int victorias, int derrotas, int empates) {
        this.id = id;
        this.username = username;
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.empates = empates;
        connection = DataBase.getConnection();
    }

    public String getId() {
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

    public void crearUsuario(String username) {
        try {
            connection.setAutoCommit(false);
            String sqlInsertUsuario = "INSERT INTO usuarios (id, username) VALUES (?, ?)";
            PreparedStatement usuarioStatement = connection.prepareStatement(sqlInsertUsuario);
            usuarioStatement.setString(1, id_user);
            usuarioStatement.setString(2, username);
            usuarioStatement.executeUpdate();
            String sqlInsertPuntuacion = "INSERT INTO puntuaciones (id_usuario, victorias, derrotas, empates) VALUES (?, 0, 0, 0)";
            PreparedStatement puntuacionStatement = connection.prepareStatement(sqlInsertPuntuacion);
            puntuacionStatement.setString(1, id_user);
            puntuacionStatement.executeUpdate();
            connection.commit();
            System.out.println("Usuario creado exitosamente");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
                String userId = resultSet.getString("id");
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

    public void actualizarPuntuacion(String userId, int nuevasVictorias, int nuevasDerrotas, int nuevosEmpates) {
        try {
            String sqlUpdatePuntuacion = "UPDATE puntuaciones SET victorias = ?, derrotas = ?, empates = ? WHERE id_usuario = ?";
            PreparedStatement puntuacionStatement = connection.prepareStatement(sqlUpdatePuntuacion);
            puntuacionStatement.setInt(1, nuevasVictorias);
            puntuacionStatement.setInt(2, nuevasDerrotas);
            puntuacionStatement.setInt(3, nuevosEmpates);
            puntuacionStatement.setString(4, userId);
            int rowsUpdated = puntuacionStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Puntuacion actualizada exitosamente");
                this.victorias = nuevasVictorias;
                this.derrotas = nuevasDerrotas;
                this.empates = nuevosEmpates;
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> obtenerRanking() {
        List<User> ranking = new ArrayList<>();
        String sql = "SELECT u.id, u.username, p.victorias, p.derrotas, p.empates " +
                "FROM usuarios u LEFT JOIN puntuaciones p ON u.id = p.id_usuario " +
                "ORDER BY p.victorias DESC";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userId = resultSet.getString("id");
                String foundUsername = resultSet.getString("username");
                int userVictorias = resultSet.getInt("victorias");
                int userDerrotas = resultSet.getInt("derrotas");
                int userEmpates = resultSet.getInt("empates");
                ranking.add(new User(userId, foundUsername, userVictorias, userDerrotas, userEmpates));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ranking;
    }


}