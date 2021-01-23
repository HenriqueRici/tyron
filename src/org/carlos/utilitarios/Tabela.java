package org.carlos.utilitarios;

import org.carlos.sintatico.NaoTerminal;
import org.carlos.sintatico.PalavrasTyron;
import org.carlos.sintatico.Terminal;

import java.util.List;

import static java.util.Arrays.asList;

public class Tabela {

    public static List<List<Integer>> analizador() {

        return asList(
                asList(1, -1, -1, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, 2, -1, -1, -1, 2, 3, 3, 2, 3, 3, 2, 2, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, 9, -1, -1, -1, 6, -1, -1, 7, -1, -1, 4, 5, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, -1, 15, 14, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, 19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, 20, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 24, -1, -1, -1, 22, 23, -1, -1, -1, -1),
                asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 31, 31, -1, 25, 26, 27, 28, 29, 30),
                asList(-1, -1, -1, 32, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
                asList(-1, 33, 33, 34, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, -1, -1, 33, 33, -1, -1, -1, -1)
        );
    }

    public static List<List<PalavrasTyron>> pilhaTabela(){
        return asList(
//        0 -, < org.carlos.principal > ::=main<lista_comandos> endmain
            asList(Terminal.MAIN, NaoTerminal.LISTA_COMANDO, Terminal.ENDMAIN),
//        1 -,<org.carlos.principal > ::=î
                List.of(),
//        2 -,<lista_comandos > ::= <comando > <lista_comandos >
                asList(NaoTerminal.COMANDO, NaoTerminal.LISTA_COMANDO),
//        3 -,<lista_comandos > ::=î
                List.of(),
//        4 -,<comando > ::= <saida >
                asList(NaoTerminal.SAIDA),
//        5 -,<comando > ::= <entrada >
                asList(NaoTerminal.ENTRADA),
//        6 -,<comando > ::= <condicional >
                asList(NaoTerminal.CONDICIONAL),
//        7 -,<comando > ::= <loop >
                asList(NaoTerminal.LOOP),
//        8 -,<comando > ::= <declaracao >
                asList(NaoTerminal.DECLARACAO),
//        9 -,<comando > ::= <atribuicao >
                asList(NaoTerminal.ATRIBUICAO),
//        10 -, "<saida> ::= output " "(" " <qualquer_expressao> " ")" " " ";" ""
                asList(Terminal.OUTPUT, Terminal.ABRE_PAR, NaoTerminal.QUALQUER_EXPRESSAO, Terminal.FECHA_PAR, Terminal.PONTO_VIRGULA),
//        11 -, "<entrada> ::= input " "(" " identifier " ")" " " ";" ""
                asList(Terminal.INPUT, Terminal.ABRE_PAR, Terminal.IDENTIFIER, Terminal.FECHA_PAR, Terminal.PONTO_VIRGULA),
//        12 -,<condicional > ::= <if> <else>endif
                asList(NaoTerminal.IF, NaoTerminal.ELSE, Terminal.ENDIF),
//        13 -, "<if> ::= if " "(" " <expressao> " ")" " <lista_comandos>"
                asList(Terminal.IF, Terminal.ABRE_PAR, NaoTerminal.EXPRESSAO, Terminal.FECHA_PAR, NaoTerminal.LISTA_COMANDO),
//        14 -,<else> ::= else <lista_comandos >
                asList(Terminal.ELSE, NaoTerminal.LISTA_COMANDO),
//        15 -,<else> ::=î
                List.of(),
//        16 -,<loop > ::= <while>endwhile
                asList(NaoTerminal.WHILE ,Terminal.ENDWHILE),
//        17 -, "<while> ::= while " "(" " <expressao> " ")" " <lista_comandos>"
                asList(Terminal.WHILE, Terminal.ABRE_PAR, NaoTerminal. EXPRESSAO, Terminal.FECHA_PAR, NaoTerminal.LISTA_COMANDO),
//        18 -, "<declaracao> ::= number identifier " ";" ""
                asList(Terminal.NUMBER, Terminal.IDENTIFIER, Terminal.PONTO_VIRGULA),
//        19 -, "<atribuicao> ::= identifier " "=" " <expressao> " ";" ""
                asList(Terminal.IDENTIFIER, Terminal.IGUAL, NaoTerminal.EXPRESSAO, Terminal.PONTO_VIRGULA),
//        20 -,<expressao > ::=identifier<operador>
                asList(Terminal.IDENTIFIER, NaoTerminal.OPERADOR),
//        21 -,<expressao > ::=integer_literal<operador>
                asList(Terminal.INTEGER_LITERAL, NaoTerminal.OPERADOR),
//        22 -, "<expressao> ::= " "+" " <expressao>"
                asList(Terminal.SOMA, NaoTerminal.EXPRESSAO),
//        23 -, "<expressao> ::= " "-" " <expressao>"
                asList(Terminal.SUBTRACAO, NaoTerminal.EXPRESSAO),
//        24 -, "<expressao> ::= " "(" " <expressao> " ")" " <operador>"
                asList(Terminal.ABRE_PAR, NaoTerminal.EXPRESSAO, Terminal.FECHA_PAR, NaoTerminal.OPERADOR),
//        25 -, "<operador> ::= " "+" " <expressao>"
                asList(Terminal.SOMA, NaoTerminal.EXPRESSAO),
//        26 -, "<operador> ::= " "-" " <expressao>"
                asList(Terminal.SUBTRACAO, NaoTerminal.EXPRESSAO),
//        27 -, "<operador> ::= " "*" " <expressao>"
                asList(Terminal.MULTIPLICACAO, NaoTerminal.EXPRESSAO),
//        28 -, "<operador> ::= " "/" " <expressao>"
                asList(Terminal.DIVISAO, NaoTerminal.EXPRESSAO),
//        29 -, "<operador> ::= " "<" " <expressao>"
                asList(Terminal.MENOR, NaoTerminal.EXPRESSAO),
//        30 -, "<operador> ::= " ">" " <expressao>"
                asList(Terminal.MAIOR, NaoTerminal.EXPRESSAO),
//        31 -,<operador > ::=î
                List.of(),
//        32 -,<string > ::=string_literal
                asList(Terminal.STRING_LITERAL),
//        33 -,<qualquer_expressao > ::= <expressao >
                asList(NaoTerminal.EXPRESSAO),
//        34 -,<qualquer_expressao > ::= <string >
                asList(NaoTerminal.STRING)
        );
    }
}
