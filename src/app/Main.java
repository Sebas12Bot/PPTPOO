package app;

import dominio.Declaraciones;
import dominio.Ventana;

public class Main {
    public static void main(String[] args) {
        String nombre = Declaraciones.pedirNombre("Cual es tu nombre");
        Ventana.iniciarJuego(nombre);

    }
}
