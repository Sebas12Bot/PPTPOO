package dominio;

import javax.swing.*;
import java.util.Arrays;

public class ventana {

    public static Icon icono;
    private static final Object[] OPCIONES = Arrays.asList("Ver estadisticas", "Jugar", "Ver Instrucciones").toArray();
    private static final String TITULO = "POO - Piedra, Papel o Tijera";
    private static final int VERESTADISTICAS = 0;
    private static final int JUGAR = 1;
    private static final int VERINSTRUCCIONES = 2;

    public static int ganadas = 0;
    public static int empatadas = 0;
    public static int perdidas = 0;

    public static void iniciarJuego(String nombre) {
        while (true) {
            int opcionElegida = mostrarMenuPrincipal(nombre);

            switch (opcionElegida) {
                case VERINSTRUCCIONES -> verInformacion();
                case JUGAR -> jugarPartida(nombre);
                case VERESTADISTICAS -> verEstadisticas(nombre);
            }
        }
    }

    public static int mostrarMenuPrincipal(String nombre) {
        int opcion = JOptionPane.showOptionDialog(null,
                nombre + " Bienvenid@!\n" +
                        "¿Qué quieres hacer?", TITULO, 0,
                JOptionPane.INFORMATION_MESSAGE, icono, OPCIONES, null);

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
                + nombre + perdidas + " Partidas perdidas\n"
                + empatadas + "  Partidas empatadas");
    }

    public static void jugarPartida(String nombre) {
        String eleccionJg1 = declaraciones.pedirEleccion(nombre);
        String eleccionJg2 = declaraciones.obtenerEleccionAleatoria();
        resumenMano(eleccionJg1, eleccionJg2, nombre);
    }

    public static void resumenMano(String eleccionJg1, String eleccionJg2, String nombre) {
        String resultado = declaraciones.verificarGanador(eleccionJg1, eleccionJg2);
        mostrarMensaje(nombre + " Elegiste: " + eleccionJg1 + "\n" +
                "NPC: " + eleccionJg2 + "\n" + " " + resultado);
    }

    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.INFORMATION_MESSAGE, icono);
    }

    public static void mostrarMensajeCancelacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.CANCEL_OPTION, icono);
    }
}
