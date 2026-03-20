package org.example.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameController2 {

    @FXML private HBox contenedorLetras;
    @FXML private ImageView imgSol;
    @FXML private Label lblTitulo;

    private String palabraSecreta;
    private TextField[] camposLetras;
    private int errores = 0;

    // Recibe palabra de la primera pantalla
    public void initGame(String palabra) {
        palabraSecreta = palabra.toLowerCase();
        camposLetras = new TextField[palabraSecreta.length()];
        crearCamposLetras();
        actualizarSol();
    }

    // Crea un TextField por cada letra de la palabra secreta
    private void crearCamposLetras() {
        contenedorLetras.getChildren().clear();
        for (int i = 0; i < palabraSecreta.length(); i++) {
            TextField tf = new TextField();
            tf.setPrefSize(45, 45);
            tf.setText("");
            tf.textProperty().addListener((obs, old, nuevo) -> {
                if (nuevo.length() > 1) tf.setText(nuevo.substring(0, 1));
            });

            // Cuando escribo, valido la letra
            tf.textProperty().addListener((obs, viejo, nuevo) -> {
                if (!nuevo.matches("[a-zA-Záéíóúñ]")) {
                    tf.clear();
                    return;
                }
                verificarLetra(tf, nuevo.toLowerCase().charAt(0));
            });

            camposLetras[i] = tf;
            contenedorLetras.getChildren().add(tf);
        }
    }

    // Busca si la letra que puse está en la palabra
    private void verificarLetra(TextField tf, char letra) {
        boolean correcta = false;
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (normalizarLetra(palabraSecreta.charAt(i)) == normalizarLetra(letra)) {
                camposLetras[i].setText(String.valueOf(letra));
                tf.clear();
                correcta = true;
                break;
            }
        }
        if (!correcta) {
            errores++;
            actualizarSol();
            System.out.println("Error #"+errores);
        }
        checkWin();
    }

    // Para que 'a' = 'á' y 'e' = 'é' como pide el profe
    private char normalizarLetra(char c) {
        if (c=='á' || c=='à' || c=='â') return 'a';
        if (c=='é' || c=='è' || c=='ê') return 'e';
        if (c=='í' || c=='ì' || c=='î') return 'i';
        if (c=='ó' || c=='ò' || c=='ô') return 'o';
        if (c=='ú' || c=='ù' || c=='û') return 'u';
        return Character.toLowerCase(c);
    }

    // Cambia la imagen del sol según errores (20% por error)
    private void actualizarSol() {
        int nivel = Math.min(errores, 5);
        String ruta = "/sun_" + nivel + ".png";
        try {
            imgSol.setImage(new Image(getClass().getResourceAsStream(ruta)));
        } catch (Exception e) {
            System.out.println("Imagen no encontrada: " + ruta);
        }
    }

    private void checkWin() {
        for (TextField tf : camposLetras) {
            if (tf.getText().isEmpty()) return;
        }
        System.out.println("¡GANASTE!");
    }
}