package org.example.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import org.example.soleclipsado.model.Juego;

public class GameController2 {

    @FXML private HBox contenedorLetras;
    @FXML private ImageView imgSol;
    @FXML private TextField txtInput;
    @FXML private Button btnAyuda;
    @FXML private Label lblMensaje;
    @FXML private Label lblLetrasUsadas;

    private Juego juego;
    private TextField[] camposLetras;

    public void initGame(String palabra) {
        juego = new Juego(palabra);

        camposLetras = new TextField[juego.getPalabra().length()];
        crearCamposLetras();

        txtInput.setVisible(false);
        txtInput.setManaged(false);
        txtInput.requestFocus();

        lblMensaje.setText("Escribe una letra...");

        txtInput.textProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo.isEmpty()) return;

            char letra = nuevo.toLowerCase().charAt(0);

            if (!String.valueOf(letra).matches("[a-záéíóúñ]")) {
                txtInput.clear();
                return;
            }

            procesarLetra(letra);
            txtInput.clear();
        });

        contenedorLetras.setOnMouseClicked(e -> txtInput.requestFocus());
    }

    private void procesarLetra(char letra) {

        if (juego.letraYaUsada(letra)) {
            lblMensaje.setText("Ya usaste esa letra");
            return;
        }

        juego.agregarLetra(letra);

        boolean correcta = juego.verificarLetra(letra);

        actualizarVista();

        if (correcta) {
            lblMensaje.setText("Letra correcta");
        } else {
            lblMensaje.setText("Letra incorrecta (" + juego.getErrores() + ")");
        }

        actualizarLetrasUsadas();
        actualizarSol();

        if (juego.gano()) {
            lblMensaje.setText("¡GANASTE!");
            desactivarGame();
        }

        if (juego.perdio()) {
            lblMensaje.setText("Perdiste. Era: " + juego.getPalabra());
            desactivarGame();
        }
    }

    private void crearCamposLetras() {
        contenedorLetras.getChildren().clear();

        for (int i = 0; i < camposLetras.length; i++) {
            TextField tf = new TextField();
            tf.setPrefSize(45, 45);
            tf.setEditable(false);
            tf.setStyle("-fx-alignment: center; -fx-font-size: 18px;");

            camposLetras[i] = tf;
            contenedorLetras.getChildren().add(tf);
        }
    }

    private void actualizarVista() {
        boolean[] descubiertas = juego.getDescubiertas();

        for (int i = 0; i < descubiertas.length; i++) {
            if (descubiertas[i]) {
                camposLetras[i].setText(
                        String.valueOf(juego.getPalabra().charAt(i))
                );
            }
        }
    }

    private void actualizarLetrasUsadas() {
        StringBuilder sb = new StringBuilder("Letras usadas: ");

        for (char c : juego.getLetrasUsadas()) {
            sb.append(c).append(" ");
        }

        lblLetrasUsadas.setText(sb.toString());
    }

    private void actualizarSol() {
        int nivel = Math.min(juego.getErrores(), 5);
        String ruta = "/org/example/soleclipsado/sun_" + nivel + ".png";

        try {
            imgSol.setImage(new Image(getClass().getResourceAsStream(ruta)));
        } catch (Exception e) {
            System.out.println("Imagen no encontrada");
        }
    }

    private void desactivarGame() {
        txtInput.setDisable(true);
    }

    @FXML
    private void handleAyuda() {

        if (juego.getAyudas() >= 3) {
            lblMensaje.setText("No quedan ayudas");
            return;
        }

        int index = juego.revelarLetraRandom();

        if (index != -1) {
            actualizarVista();
        }

        juego.usarAyuda();
        actualizarSol();
    }
}