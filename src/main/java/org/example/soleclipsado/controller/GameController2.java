package org.example.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.soleclipsado.model.GameModel;

public class GameController2 {

    @FXML private HBox contenedorLetras;
    @FXML private ImageView imgSol;
    @FXML private TextField txtInput;
    @FXML private Label lblMensaje;
    @FXML private Button btnAyuda;

    private GameModel gameModel;
    private TextField[] camposLetras;

    public void initGame(GameModel model) {
        this.gameModel = model;
        lblMensaje.setText("INICIO");

        String palabra = gameModel.getPalabraSecreta();
        camposLetras = new TextField[palabra.length()];

        crearCamposLetras();
        actualizarSol();

        txtInput.setVisible(true);
        txtInput.requestFocus();

        txtInput.textProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo.isEmpty()) return;

            char letra = nuevo.toLowerCase().charAt(0);
            if (!String.valueOf(letra).matches("[a-záéíóúñ]")) {
                txtInput.clear();
                return;
            }

            if (gameModel.letraCorrecta(letra)) {
                actualizarCampos();
                lblMensaje.setText("Letra correcta");
            } else {
                actualizarSol();
                lblMensaje.setText("Letra incorrecta (" + gameModel.getErrores() + ")");
            }

            txtInput.clear();

            if (gameModel.gano()) {
                lblMensaje.setText("¡GANASTE!");
                desactivarGame();
            } else if (gameModel.getErrores() >= 5) {
                lblMensaje.setText("¡PERDISTE!");
                desactivarGame();
            }
        });

        contenedorLetras.setOnMouseClicked(e -> txtInput.requestFocus());
    }

    private void crearCamposLetras() {
        contenedorLetras.getChildren().clear();
        String palabra = gameModel.getPalabraSecreta();
        for (int i = 0; i < palabra.length(); i++) {
            TextField tf = new TextField();
            tf.setPrefSize(45, 45);
            tf.setEditable(false);
            camposLetras[i] = tf;
            contenedorLetras.getChildren().add(tf);
        }
    }

    private void actualizarCampos() {
        String palabra = gameModel.getPalabraSecreta();
        for (int i = 0; i < palabra.length(); i++) {
            if (gameModel.getLetrasAdivinadas().contains(palabra.charAt(i))) {
                camposLetras[i].setText(String.valueOf(palabra.charAt(i)));
            }
        }
    }

    private void desactivarGame() {
        for (TextField tf : camposLetras) tf.setEditable(false);
    }

    private void actualizarSol() {
        int nivel = Math.min(gameModel.getErrores(), 5);
        String ruta = "/org/example/soleclipsado/sun_" + nivel + ".png";
        try { imgSol.setImage(new Image(getClass().getResourceAsStream(ruta))); }
        catch (Exception e) { System.out.println("Imagen no encontrada: " + ruta); }
    }

    @FXML
    private void handleAyuda() {
        if (gameModel.getAyudasUsadas() >= 3) {
            lblMensaje.setText("No quedan más ayudas");
            return;
        }

        revelarLetraRandom();
        gameModel.usarAyuda();
        lblMensaje.setText("Ayuda usada (" + gameModel.getAyudasUsadas() + "/3)");

        if (gameModel.getAyudasUsadas() >= 3) btnAyuda.setDisable(true);
    }

    private void revelarLetraRandom() {
        int vacios = 0;
        for (TextField tf : camposLetras) if (tf.getText().isEmpty()) vacios++;
        if (vacios == 0) return;

        int objetivo = (int)(Math.random() * vacios);
        int contador = 0;
        for (int i = 0; i < camposLetras.length; i++) {
            if (camposLetras[i].getText().isEmpty()) {
                if (contador == objetivo) {
                    camposLetras[i].setText(String.valueOf(gameModel.getPalabraSecreta().charAt(i)));
                    gameModel.getLetrasAdivinadas().add(gameModel.getPalabraSecreta().charAt(i));
                    return;
                }
                contador++;
            }
        }
    }
}