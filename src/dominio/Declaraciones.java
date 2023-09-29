package dominio;

import java.util.Random;
import javax.swing.*;
import java.util.Arrays;

public class Declaraciones {

    public static String solicitarEleccion(String nombre) {
        return (String) JOptionPane.showInputDialog(null, nombre + " !A jugar!\n" +
                        "Tome su desicion: ", "POO - Piedra, Papel o Tijera", JOptionPane.QUESTION_MESSAGE,
                null, Arrays.asList("Piedra", "Papel", "Tijera").toArray(), 0);
    }

    public static String generarEleccion() {
        String[] opciones = {"Piedra", "Papel", "Tijera"};
        Random random = new Random();
        int indiceAleatorio = random.nextInt(opciones.length);
        return opciones[indiceAleatorio];
    }
    public static void changeEstadistica() {
        String uuid = Ventana.uuid;
        int victorias = Ventana.victorias;
        int derrotas = Ventana.derrotas;
        int empates = Ventana.empatadas;

        User usuario = new User();
        usuario.actualizarPuntuacion(uuid, victorias, derrotas, empates);
    }

    public static String verificarGanador(String desicionPlayer1, String desicionPlayer2) {
        System.out.println(Ventana.uuid);
        if (desicionPlayer1.equalsIgnoreCase(desicionPlayer2)) {
            Ventana.empatadas += 1;
            changeEstadistica();
            return "Empate";
        } else if ((desicionPlayer1.equals("Piedra") && desicionPlayer2.equals("Tijera")) ||
                (desicionPlayer1.equals("Tijera") && desicionPlayer2.equals("Papel")) ||
                (desicionPlayer1.equals("Papel") && desicionPlayer2.equals("Piedra"))) {
            Ventana.victorias += 1;
            changeEstadistica();
            return "Ganaste";
        } else {
            Ventana.derrotas += 1;
            changeEstadistica();
            return "Perdiste";
        }
    }

    public static String pedirNombre(String mensaje) {
        return (String) JOptionPane.showInputDialog(null, mensaje, "POO - Piedra, Papel o Tijera", JOptionPane.QUESTION_MESSAGE, null, null, "");
    }

}
