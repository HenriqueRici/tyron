package org.carlos.sintatico;

import org.carlos.lexico.Tokens;
import org.carlos.utilitarios.ExcecaoSintatico;
import org.carlos.utilitarios.Flag;
import org.carlos.utilitarios.Tabela;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Sintatico {
    private LinkedList<Tokens> tokensList;
    private Stack<PalavrasTyron> pilha;

    public Sintatico(List<Tokens> tokensList) {
        this.tokensList = new LinkedList<>(tokensList);
        this.pilha = new Stack<>();
        pilha.push(Terminal.FINAL);
        pilha.push(NaoTerminal.PRINCIPAL);
    }

    public void analisador() {
        while (!pilha.empty()) {
            int linha = tokensList.getFirst().getLinha();
            int coluna = tokensList.getFirst().getColuna();
            Terminal token = tokensList.getFirst().getToken();
            print("TOKEN " + token.getNome() + " foi selecionado na lista de tokens");
            if (pilha.peek() instanceof Terminal) {
                if (pilha.peek().getIndice() == token.getIndice()) {
                    print("TOKEN " + token.getNome() + " foi removido da lista de tokens");
                    tokensList.removeFirst();
                    print("TOKEN " + token.getNome() + " foi removido da pilha");
                    pilha.pop();
                } else {
                    throw new ExcecaoSintatico("ERRO SINTATICO, " + "era esperado " + pilha.peek() + ", mas foi encontrado " +
                            tokensList.getFirst().getToken() + ", " + linha + ", " + coluna);
                }
            } else {
                PalavrasTyron naoTerminal = pilha.peek();
                int indice = Tabela.analizador().get(naoTerminal.getIndice()).get(token.getIndice());
                if (indice < 0) {
                    throw new ExcecaoSintatico("ERRO SINTATICO, " + "era esperado " + pilha.peek() + ", mas foi encontrado " +
                            tokensList.getFirst().getToken() + ", " + linha + ", " + coluna);
                }
                List<PalavrasTyron> listaComandos = Tabela.pilhaTabela().get(indice);
                print((pilha.peek() instanceof NaoTerminal ? "NAO TERMINAL " : "TERMINAL ") + pilha.peek() + " foi removido da pilha");
                pilha.pop();
                inverter(listaComandos).forEach(comandos -> {
                    print((pilha.peek() instanceof NaoTerminal ? "NAO TERMINAL " : "TERMINAL ") + pilha.peek() + " foi inserido da pilha");
                    pilha.push(comandos);
                });
            }
        }
    }

    public void print(String msg) {
        if (Flag.SINTATICO.isAtivo()) {
            System.out.println(msg);
        }
    }

    public List<PalavrasTyron> inverter(List<PalavrasTyron> list) {
        var invertido = new LinkedList<PalavrasTyron>();
        new LinkedList<>(list).descendingIterator().forEachRemaining(invertido::add);
        return invertido;
    }
}
