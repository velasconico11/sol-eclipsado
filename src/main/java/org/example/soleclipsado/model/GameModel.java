package org.example.soleclipsado.model;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private String palabraSecreta;
    private int errores = 0;
    private int ayudasUsadas = 0;
    private List<Character> letrasAdivinadas = new ArrayList<>();

    public void setPalabraSecreta(String palabra) throws IllegalArgumentException {
        palabra = palabra.trim();

        if (palabra.isEmpty()) throw new IllegalArgumentException("Ingresa una palabra");
        if (palabra.contains(" ")) throw new IllegalArgumentException("No se permiten espacios");
        if (palabra.length() < 6 || palabra.length() > 12)
            throw new IllegalArgumentException("La palabra debe tener entre 6 y 12 letras");
        if (!palabra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+"))
            throw new IllegalArgumentException("Solo se permiten letras");

        this.palabraSecreta = palabra.toLowerCase();
        this.errores = 0;
        this.ayudasUsadas = 0;
        this.letrasAdivinadas.clear();
    }

    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    public int getErrores() {
        return errores;
    }

    public int getAyudasUsadas() {
        return ayudasUsadas;
    }

    public void incrementarErrores() {
        errores++;
    }

    public void usarAyuda() {
        ayudasUsadas++;
    }

    public boolean letraCorrecta(char letra) {
        letra = normalizarLetra(letra);
        boolean encontrada = false;
        for (char c : palabraSecreta.toCharArray()) {
            if (normalizarLetra(c) == letra) {
                encontrada = true;
                if (!letrasAdivinadas.contains(c)) letrasAdivinadas.add(c);
            }
        }
        if (!encontrada) incrementarErrores();
        return encontrada;
    }

    public List<Character> getLetrasAdivinadas() {
        return letrasAdivinadas;
    }

    public boolean gano() {
        for (char c : palabraSecreta.toCharArray()) {
            if (!letrasAdivinadas.contains(c)) return false;
        }
        return true;
    }

    private char normalizarLetra(char c) {
        switch (c) {
            case 'á', 'à', 'â' -> c = 'a';
            case 'é', 'è', 'ê' -> c = 'e';
            case 'í', 'ì', 'î' -> c = 'i';
            case 'ó', 'ò', 'ô' -> c = 'o';
            case 'ú', 'ù', 'û' -> c = 'u';
        }
        return Character.toLowerCase(c);
    }
}