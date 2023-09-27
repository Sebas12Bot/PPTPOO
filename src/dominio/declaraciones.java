package dominio;

import java.util.Random;
import javax.swing.*;
import java.util.Arrays;

public class declaraciones {

    public static String pedirEleccion(String nombre) {
        return (String) JOptionPane.showInputDialog(null, nombre + " !A jugar!\n" +
                        "Tome su desicion: ", "POO - Piedra, Papel o Tijera", JOptionPane.QUESTION_MESSAGE,
                null, Arrays.asList("Piedra", "Papel", "Tijera").toArray(), 0);
    }

    public static String obtenerEleccionAleatoria() {
        String[] opciones = {"Piedra", "Papel", "Tijera"};
        Random random = new Random();
        int indiceAleatorio = random.nextInt(opciones.length);
        return opciones[indiceAleatorio];
    }

    public static String verificarGanador(String eleccionJg1, String eleccionJg2) {
        if (eleccionJg1.equalsIgnoreCase(eleccionJg2)) {
            ventana.empatadas += 1;
            return "Empate";
        } else if ((eleccionJg1.equals("Piedra") && eleccionJg2.equals("Tijera")) ||
                (eleccionJg1.equals("Tijera") && eleccionJg2.equals("Papel")) ||
                (eleccionJg1.equals("Papel") && eleccionJg2.equals("Piedra"))) {
            ventana.ganadas += 1;
            return "Ganaste";
        } else {
            ventana.perdidas += 1;
            return "Perdiste";
        }
    }

    public static String pedirNombre(String mensaje) {
        return (String) JOptionPane.showInputDialog(null, mensaje, "POO - Piedra, Papel o Tijera", JOptionPane.QUESTION_MESSAGE, null, null, "");
    }
}
