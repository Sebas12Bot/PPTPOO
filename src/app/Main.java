package app;

import dominio.ventana;
import dominio.dataBase;
import dominio.user;

public class Main {
    public static void main(String[] args) {
        String nombre = dominio.declaraciones.pedirNombre("Cual es tu nombre");
        ventana.iniciarJuego(nombre);
    }
}
