package lexico;

import java.util.Arrays;

public class Tokens {
    private String token;
    private int linha;
    private int coluna;
    private String identificador;

    //verifica se existe o identificador
    public Tokens(String token, String identificador, int linha, int coluna){
        this.identificador = identificador;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    //printa
    @Override
    public String toString() {
        return token + ", " + identificador + ", " + linha + ", " + coluna ;
    }
}
