package org.carlos.lexico;

import org.carlos.sintatico.Terminal;

import java.util.Arrays;

public class Tokens {
    private Terminal token;
    private int linha;
    private int coluna;
    private String identificador;

    //verifica se existe o identificador
    public Tokens(String token, String identificador, int linha, int coluna){
        this.identificador = identificador;
        this.token = Terminal.toEnum(token);
        this.linha = linha;
        this.coluna = coluna;
   }
    public static boolean palavraChave(String lexema) {
        return Arrays.asList("main", "if", "else", "endif", "while", "endwhile", "endmain", "number", "output", "input").contains(lexema);
    }

    public static boolean id(String s) {
        return s.matches("([A-Za-z_])([A-Za-z_0-9])*");
    }

    public static boolean numero(String s) {
        return s.matches("[0-9]*");
    }

    public static boolean simbolos(Character ch) {
        return Arrays.asList('(', ')', ';', '+', '*', '-', '/', '<', '>', '=').contains(ch);
    }

    public Terminal getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = Terminal.toEnum(token);
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public String toString() {
        return token + ", " + identificador + ", " + linha + ", " + coluna ;
    }
}
