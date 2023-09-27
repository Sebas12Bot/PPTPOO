package dominio;

import javax.swing.*;
import java.util.Arrays;

public class ventana {

    private static final Object[] OPCIONES = Arrays.asList("Ver estadisticas", "Jugar", "Ver Instrucciones").toArray();
    private static final String TITULO = "POO - Piedra, Papel o Tijera";

    public static int ganadas = 0;
    public static int empatadas = 0;
    public static int perdidas = 0;

    public static void iniciarJuego(String nombre) {
        user user = new user();
        user usuarioEncontrado = user.obtenerUsuarioPorUsername(nombre);

        if (usuarioEncontrado != null) {
            System.out.println("Información del usuario:");
            System.out.println("ID: " + usuarioEncontrado.getId());
            System.out.println("Nombre de Usuario: " + usuarioEncontrado.getUsername());
            System.out.println("Victorias: " + usuarioEncontrado.getVictorias()); //
            System.out.println("Derrotas: " + usuarioEncontrado.getDerrotas());
            System.out.println("Empates: " + usuarioEncontrado.getEmpates());
        } else {
            System.out.println("El usuario no fue encontrado.");
            user.crearUsuario(nombre);
        }

        while (true) {
            int opcionElegida = mostrarMenuPrincipal(nombre);

            switch (opcionElegida) {
                case 2:
                    verInformacion();
                    break;
                case 1:
                    jugarPartida(nombre);
                    break;
                case 0:
                    verEstadisticas(nombre);
                    break;
            }
        }
    }

    public static int mostrarMenuPrincipal(String nombre) {
        int opcion = JOptionPane.showOptionDialog(null,
                nombre + " Bienvenid@!\n" +
                        "¿Qué quieres hacer?", TITULO, 0,
                JOptionPane.INFORMATION_MESSAGE, null, OPCIONES, null);

        if (opcion == JOptionPane.CLOSED_OPTION) {
            mostrarMensajeCancelacion("Hasta luego " + nombre);
            System.exit(opcion);
        }
        return opcion;
    }

    public static void verInformacion() {
        mostrarMensaje("El papel gana a la piedra envolviéndola; la piedra gana a la tijera golpeándola; y la tijera gana al papel cortándolo.");
    }

    public static void verEstadisticas(String nombre) {
        mostrarMensaje(nombre + " " + ganadas + " Partidas ganadas\n"
                + nombre + " " + perdidas + " Partidas perdidas\n"
                + empatadas + "  Partidas empatadas");
    }

    public static void jugarPartida(String nombre) {
        String eleccionJg1 = declaraciones.pedirEleccion(nombre);
        String eleccionJg2 = declaraciones.obtenerEleccionAleatoria();
        String resultado = declaraciones.verificarGanador(eleccionJg1, eleccionJg2);
        mostrarResultado(nombre, eleccionJg1, eleccionJg2, resultado);
    }

    public static void mostrarResultado(String nombre, String eleccionJg1, String eleccionJg2, String resultado) {
        mostrarMensaje(nombre + " Elegiste: " + eleccionJg1 + "\n" +
                "NPC: " + eleccionJg2 + "\n" + " " + resultado);
    }

    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarMensajeCancelacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.CANCEL_OPTION);
    }

}
