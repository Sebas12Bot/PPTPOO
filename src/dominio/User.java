package dominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


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

    public void setId(String id) {
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
        try {
            // Abre una transacción en la base de datos
            connection.setAutoCommit(false);

            // Inserta un nuevo usuario en la tabla 'usuarios'
            String sqlInsertUsuario = "INSERT INTO usuarios (id, username) VALUES (?, ?)";
            PreparedStatement usuarioStatement = connection.prepareStatement(sqlInsertUsuario);
            usuarioStatement.setString(1, id_user);
            usuarioStatement.setString(2, username);
            usuarioStatement.executeUpdate();
            // Inserta una nueva puntuación en la tabla 'puntuaciones'
            String sqlInsertPuntuacion = "INSERT INTO puntuaciones (id_usuario, victorias, derrotas, empates) VALUES (?, 0, 0, 0)";
            PreparedStatement puntuacionStatement = connection.prepareStatement(sqlInsertPuntuacion);
            puntuacionStatement.setString(1, id_user);
            puntuacionStatement.executeUpdate();

            // Confirma la transacción
            connection.commit();
            System.out.println("Usuario creado exitosamente");
        } catch (SQLException e) {
            try {
                // En caso de error, revierte la transacción
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
                String userId = resultSet.getString("id"); // Corrección aquí
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
            // Actualiza las puntuaciones del usuario en la tabla 'puntuaciones'
            String sqlUpdatePuntuacion = "UPDATE puntuaciones SET victorias = ?, derrotas = ?, empates = ? WHERE id_usuario = ?";
            PreparedStatement puntuacionStatement = connection.prepareStatement(sqlUpdatePuntuacion);
            puntuacionStatement.setInt(1, nuevasVictorias);
            puntuacionStatement.setInt(2, nuevasDerrotas);
            puntuacionStatement.setInt(3, nuevosEmpates);
            puntuacionStatement.setString(4, userId);
            int rowsUpdated = puntuacionStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Puntuación actualizada exitosamente");
                // Actualiza las propiedades locales del objeto User
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
}