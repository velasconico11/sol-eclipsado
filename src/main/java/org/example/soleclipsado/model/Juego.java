package org.example.soleclipsado.model;

import java.util.HashSet;
import java.util.Set;

public class Juego {

    private String palabra;
    private int errores;
    private int ayudas;

    private Set<Character> letrasUsadas;
    private boolean[] descubiertas;

    public Juego(String palabra) {
        this.palabra = palabra.toLowerCase();
        this.errores = 0;
        this.ayudas = 0;
        this.letrasUsadas = new HashSet<>();
        this.descubiertas = new boolean[palabra.length()];
    }

    public String getPalabra() {
        return palabra;
    }

    public int getErrores() {
        return errores;
    }

    public int getAyudas() {
        return ayudas;
    }

    public Set<Character> getLetrasUsadas() {
        return letrasUsadas;
    }

    public boolean letraYaUsada(char letra) {
        return letrasUsadas.contains(letra);
    }

    public void agregarLetra(char letra) {
        letrasUsadas.add(letra);
    }

    public boolean verificarLetra(char letra) {
        boolean encontrada = false;

        for (int i = 0; i < palabra.length(); i++) {
            if (normalizar(palabra.charAt(i)) == normalizar(letra) && !descubiertas[i]) {
                descubiertas[i] = true;
                encontrada = true;
                break; // solo revela una letra
            }
        }

        if (!encontrada) {
            errores++;
        }

        return encontrada;
    }

    public boolean[] getDescubiertas() {
        return descubiertas;
    }

    public boolean gano() {
        for (boolean b : descubiertas) {
            if (!b) return false;
        }
        return true;
    }

    public boolean perdio() {
        return errores >= 5;
    }

    public int revelarLetraRandom() {
        int vacios = 0;

        for (boolean b : descubiertas) {
            if (!b) vacios++;
        }

        if (vacios == 0) return -1;

        int objetivo = (int) (Math.random() * vacios);

        int contador = 0;

        for (int i = 0; i < palabra.length(); i++) {
            if (!descubiertas[i]) {
                if (contador == objetivo) {
                    descubiertas[i] = true;
                    return i;
                }
                contador++;
            }
        }

        return -1;
    }

    public void usarAyuda() {
        ayudas++;
    }

    private char normalizar(char c) {
        if (c == 'á' || c == 'à' || c == 'â') return 'a';
        if (c == 'é' || c == 'è' || c == 'ê') return 'e';
        if (c == 'í' || c == 'ì' || c == 'î') return 'i';
        if (c == 'ó' || c == 'ò' || c == 'ô') return 'o';
        if (c == 'ú' || c == 'ù' || c == 'û') return 'u';
        return Character.toLowerCase(c);
    }
}