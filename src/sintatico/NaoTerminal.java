package sintatico;

public enum NaoTerminal implements PalavrasTyron {
    PRINCIPAL(0, "principal"),
    LISTA_COMANDO(1, "lista_comando"),
    COMANDO(2, "comando"),
    SAIDA(3, "saida"),
    ENTRADA(4, "entrada"),
    CONDICIONAL(5, "condicional"),
    LOOP(6, "loop"),
    IF(7, "if"),
    ELSE(8, "else"),
    WHILE(9, "while"),
    DECLARACAO(10, "declaracao"),
    ATRIBUICAO(11, "atribuicao"),
    EXPRESSAO(12, "expressao"),
    OPERADOR(13, "operador"),
    STRING(14, "string"),
    QUALQUER_EXPRESSAO(15, "qualquer_expressao");

    private int index;
    private String nome;

    NaoTerminal(int index, String nome){
        this.index = index;
        this.nome = nome;
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
