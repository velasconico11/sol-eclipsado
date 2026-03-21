package org.example.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.soleclipsado.model.GameModel;

public class GameController {

    @FXML
    private TextField txtPalabra;
    @FXML
    private Label lblMensaje;

    private GameModel gameModel = new GameModel();

    @FXML
    private void handleJugar() {
        try {
            // Validar y guardar palabra en el modelo
            gameModel.setPalabraSecreta(txtPalabra.getText());
            lblMensaje.setText("¡Juego iniciado!");

            // Cargar segunda vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/soleclipsado/Game.fxml"));
            Parent root = loader.load();

            GameController2 controller2 = loader.getController();
            controller2.initGame(gameModel);

            Stage stage = (Stage) txtPalabra.getScene().getWindow();
            stage.getScene().setRoot(root); // mantiene el tamaño de la ventana

        } catch (IllegalArgumentException e) {
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            lblMensaje.setText("Error al iniciar el juego");
        }
    }
}