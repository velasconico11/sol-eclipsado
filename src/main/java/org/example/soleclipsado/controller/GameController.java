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

        //Validar textfield vacio
        if (palabra.isEmpty()){
            lblMensaje.setText("Ingresa una palabra");
            return;
        }

        // Evitar espacios
        if (palabra.contains(" ")){
            lblMensaje.setText("No se permiten espacios");
            return;
        }

        // Validar Longitud
        if (palabra.length() < 6 || palabra.length() > 12) {
            lblMensaje.setText("La palabra debe tener entre 6 y 12 letras");
            return;
        }

        // Validar las letras (ñ y acentos)
        if (!palabra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")){
            lblMensaje.setText("Solo se permiten Letras");
            return;
        }

        palabraSecreta = palabra;

        lblMensaje.setText("Palabra guardada, iniciando juego... ");

        System.out.println("Palabra secreta: " + palabraSecreta);



    }

}
