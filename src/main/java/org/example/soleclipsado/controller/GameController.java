package org.example.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
*Clase para validar las palabras ingresadas
*@version 1.0
 */

public class GameController {

    @FXML
    private TextField txtPalabra;

    @FXML
    private Label lblMensaje;

    private String palabraSecreta;

    @FXML
    private void handleJugar() {
        String palabra = txtPalabra.getText().trim();

        if (palabra.isEmpty()) {
            lblMensaje.setText("Ingresa una palabra");
            return;
        }

        if (palabra.contains(" ")) {
            lblMensaje.setText("No se permiten espacios");
            return;
        }

        if (palabra.length() < 6 || palabra.length() > 12) {
            lblMensaje.setText("La palabra debe tener entre 6 y 12 letras");
            return;
        }

        if (!palabra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
            lblMensaje.setText("Solo se permiten letras");
            return;
        }

        palabraSecreta = palabra;
        lblMensaje.setText("¡Juego iniciado!");
        System.out.println("Palabra: " + palabraSecreta);
    }
}