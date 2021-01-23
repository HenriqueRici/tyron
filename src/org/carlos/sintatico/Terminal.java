package org.carlos.sintatico;

public enum Terminal implements PalavrasTyron{
    FINAL(0, "FINAL"),
    IDENTIFIER(1, "ID"),
    INTEGER_LITERAL(2, "NUMERAL"),
    STRING_LITERAL(3, "STRING"),
    MAIN(4, "MAIN"),
    IF(5, "IF"),
    ENDIF(6, "ENDIF"),
    ELSE(7, "ELSE"),
    WHILE(8, "WHILE"),
    ENDWHILE(9, "ENDWHILE"),
    ENDMAIN(10, "ENDMAIN"),
    OUTPUT(11, "OUTPUT"),
    INPUT(12, "INPUT"),
    NUMBER(13, "NUMBER"),

    ABRE_PAR(14, "("),
    FECHA_PAR(15, ")"),
    PONTO_VIRGULA(16, ";"),
    IGUAL(17, "="),
    SOMA(18, "+"),
    SUBTRACAO(19, "-"),
    MULTIPLICACAO(20, "*"),
    DIVISAO(21, "/"),
    MENOR(22, "<"),
    MAIOR(23, ">")
    ;

    private int index;
    private String nome;

    Terminal(int index, String nome){
        this.index = index;
        this.nome = nome;
    }

    public static Terminal toEnum(String string){
        for (Terminal t : Terminal.values()){
            if (string.equals(t.getNome())){
                return t;
            }
        }
        throw new IllegalArgumentException(string + " nao foi reconhecido!");
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getIndice() {
        return index;
    }
}
