package semantico;


import lexico.Tokens;
import sintatico.Terminal;
import utilitarios.ExcecaoSemantico;
import utilitarios.Flag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// git add .
// git commit -m "Implementação do semantico"
// git push

public class Semantico {
    private List<Tokens> listaTokens;
    private Map<String, Boolean> listaDeclarados;

    public Semantico(List<Tokens> listaTokens) {
        this.listaTokens = new ArrayList<>(listaTokens);
        this.listaDeclarados = new HashMap<>();
    }

    public void analisador() {
        int i = 0;
        while (i < listaTokens.size()) {
            Tokens tokenAtual = listaTokens.get(i);
            if (tokenAtual.getToken().equals(Terminal.NUMBER)) {
                Tokens tokenProximo = listaTokens.get(i + 1);
                if (listaDeclarados.containsKey(tokenProximo.getIdentificador())) {
                    erro("a variavel \"" + tokenProximo.getIdentificador() + "\" ja foi declarada! ", tokenProximo.getLinha(), tokenProximo.getColuna());
                }
                listaDeclarados.put(tokenProximo.getIdentificador(), false);
                print("A variavel \"" + tokenProximo.getIdentificador() + "\" foi declarada");
            } else if (tokenAtual.getToken().equals(Terminal.IF) || tokenAtual.getToken().equals(Terminal.WHILE)) {
                while (!listaTokens.get(i++).getToken().equals(Terminal.FECHA_PAR)) {
                    if (listaTokens.get(i).getToken().equals(Terminal.IDENTIFIER)) {
                        if (!listaDeclarados.containsKey(listaTokens.get(i).getIdentificador())) {
                            erro("a variavel \"" + listaTokens.get(i).getIdentificador() + "\" nao foi declarada! ",
                                    listaTokens.get(i).getLinha(), listaTokens.get(i).getColuna());
                        }
                    }
                }
            } else if (tokenAtual.getToken().equals(Terminal.IDENTIFIER)) {
                if (!listaDeclarados.containsKey(tokenAtual.getIdentificador())) {
                    erro("a variavel \"" + tokenAtual.getIdentificador() + "\" nao foi declarada! ",
                            listaTokens.get(i).getLinha(), listaTokens.get(i).getColuna());
                }
                StringBuffer buffer = new StringBuffer();
                if (listaTokens.get(++i).getToken().equals(Terminal.IGUAL)) {
                    i++;
                    while (!listaTokens.get(i).getToken().equals(Terminal.PONTO_VIRGULA)) {
                        String valor = listaTokens.get(i).getIdentificador();
                        if (Tokens.id(valor)) {
                            if (!listaDeclarados.containsKey(tokenAtual.getIdentificador())) {
                                erro("a variavel \"" + tokenAtual.getIdentificador() + "\" nao foi declarada! ",
                                        listaTokens.get(i).getLinha(), listaTokens.get(i).getColuna());
                            }
                            if (listaDeclarados.get(valor)) {
                                buffer.append(valor);
                            } else {
                                buffer.append("0");
                            }
                        } else {
                            buffer.append(valor);
                        }
                        i++;
                    }
                    if (buffer.toString().equals("0")) {
                        listaDeclarados.put(tokenAtual.getIdentificador(), false);
                    } else if (buffer.toString().contains("/0")) {
                        erro("encontrado divisao por 0! ", tokenAtual.getLinha(), tokenAtual.getColuna());
                    } else {
                        listaDeclarados.put(tokenAtual.getIdentificador(), true);
                    }
                    print("A variavel \"" + tokenAtual.getIdentificador() + "\" recebeu valor: " + buffer.toString());
                }
            }
            i++;
        }
    }

    private void erro(String erro, int linha, int coluna) {
        throw new ExcecaoSemantico("ERRO SEMANTICO, " + erro + linha + ", " + coluna);
    }

    public void print(String msg) {
        if (Flag.SEMANTICO.isAtivo()) {
            System.out.println(msg);
        }
    }
}
