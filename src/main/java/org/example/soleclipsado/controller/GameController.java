package org.example.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;  // Carga Game.fxml, lo lee y crea la pantalla
import javafx.scene.Parent; // La base completa del Game.fxml (Todo lo que esta dentro del Vbox)
import javafx.scene.Scene; // Ventana donde entra la base
import javafx.stage.Stage; // Ventana donde se cambia de escena


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
    private Stage stage; //Faltaba declarar la variable

    @FXML
    private void handleJugar() {
        String palabra = txtPalabra.getText().trim();

        // Validar vacio
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

        // Se pasa a la segunda escena (pantalla)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game.fxml"));
            Parent root = loader.load();

            GameController2 gameController2 = loader.getController();
            gameController2.initGame(palabraSecreta);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al iniciar el juego");


        }
    }
}