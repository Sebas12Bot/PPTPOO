package dominio;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Ventana {

    private static final Object[] OPCIONES_INICIALES = Arrays.asList("Ver estadisticas", "Jugar", "Ver Instrucciones", "Ver Ranking").toArray();
    private static final String TITULO = "POO - Piedra, Papel o Tijera";

    public static String uuid = "";
    public static int victorias = 0;
    public static int empatadas = 0;
    public static int derrotas = 0;

    public static void Inicializar(String nombre) {
        User user = new User();
        User usuarioEncontrado = user.obtenerUsuarioPorUsername(nombre);
        if (usuarioEncontrado != null) {
            uuid = usuarioEncontrado.getId();
            victorias = usuarioEncontrado.getVictorias();
            derrotas = usuarioEncontrado.getDerrotas();
            empatadas = usuarioEncontrado.getEmpates();
        } else {
            user.crearUsuario(nombre);
            usuarioEncontrado = user.obtenerUsuarioPorUsername(nombre);
            uuid = usuarioEncontrado.getId();
        }

        while (true) {
            int opcionElegida = VisualizarMenu(nombre);

            switch (opcionElegida) {
                case 3:
                    visualizarRanking(user);
                    break;
                case 2:
                    verInformacion();
                    break;
                case 1:
                    jugarPartida(nombre);
                    break;
                case 0:
                    visualizarEstadisticas(nombre, user);
                    break;
            }
        }
    }

    public static int VisualizarMenu(String nombre) {
        int opcion = JOptionPane.showOptionDialog(null,
                nombre + " Bienvenid@!\n" +
                        "¿Qué quieres hacer?", TITULO, 0,
                JOptionPane.INFORMATION_MESSAGE, null, OPCIONES_INICIALES, null);

        if (opcion == JOptionPane.CLOSED_OPTION) {
            mostrarMensajeCancelacion("Hasta luego " + nombre);
            System.exit(opcion);
        }
        return opcion;
    }

    public static void verInformacion() {
        mostrarMensaje("El papel gana a la piedra envolviendola; la piedra gana a la tijera golpeandola; y la tijera gana al papel cortandolo.");
    }

    public static void visualizarEstadisticas(String nombre, User user) {
        mostrarMensaje(nombre + " " + victorias + " Partidas ganadas\n"
                + nombre + " " + derrotas + " Partidas perdidas\n"
                + empatadas + " Partidas empatadas");
    }

    public static void jugarPartida(String nombre) {
        try {
            String desicionPlayer1 = Declaraciones.solicitarEleccion(nombre);
            if (desicionPlayer1 == null) {
                mostrarMensaje("Juego cancelado.");
                return;
            }

            String desicionPlayer2 = Declaraciones.generarEleccion();
            String resultado = Declaraciones.verificarGanador(desicionPlayer1, desicionPlayer2);
            visualizarResultados(nombre, desicionPlayer1, desicionPlayer2, resultado);
        } catch (NullPointerException e) {
            mostrarMensaje("Se produjo un error: " + e.getMessage());
        }
    }

    public static void visualizarResultados(String nombre, String desicionPlayer1, String desicionPlayer2, String resultado) {
        mostrarMensaje(nombre + " Elegiste: " + desicionPlayer1 + "\n" +
                "NPC: " + desicionPlayer2 + "\n" + " " + resultado);
    }

    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarMensajeCancelacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.CANCEL_OPTION);
    }

    public static void visualizarRanking(User user) {
        List<User> ranking = user.obtenerRanking();

        if (ranking.isEmpty()) {
            mostrarMensaje("No hay usuarios registrados aun.");
        } else {
            StringBuilder rankingMensaje = new StringBuilder("Ranking de Jugadores👑:\n");

            for (int i = 0; i < ranking.size(); i++) {
                User usuario = ranking.get(i);
                rankingMensaje.append(i + 1)
                        .append(". ").append(usuario.getUsername())
                        .append(" | Victorias: ").append(usuario.getVictorias())
                        .append(" | Derrotas: ").append(usuario.getDerrotas())
                        .append(" | Empates: ").append(usuario.getEmpates())
                        .append("\n");
            }

            mostrarMensaje(rankingMensaje.toString());
        }
    }
}
