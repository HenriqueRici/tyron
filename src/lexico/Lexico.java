package lexico;

import utilitarios.ExcecaoLexico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Lexico {
    //transforma a lista recebida em um lista de char
    public List<char[]> desfragmentador(List<String> lista) {
        List<char[]> matriz = new ArrayList<>();
        for (String s : lista) {
            matriz.add(s.toCharArray());
        }
        return matriz;
    }

    public List<Tokens> analisador(List<char[]> list){
        List<Tokens> listTokens = new ArrayList<>();
        int linha;
        for (linha = 0; linha < list.size(); linha++) {
            StringBuffer buffer = new StringBuffer();
            char[] conteudoLinha = list.get(linha);
            if (!Stream.of(conteudoLinha).allMatch(chars -> chars.equals(' '))) {
                for (int coluna = 0; coluna < conteudoLinha.length; coluna++) {
                    if (conteudoLinha[coluna] == ' ') {
                        continue;
                    }
                    buffer.append(conteudoLinha[coluna]);
                    Character proximo = coluna < conteudoLinha.length - 1 ? conteudoLinha[coluna + 1] : ' ';
                    if (conteudoLinha[coluna] == '\"') {
                        int cont = coluna;
                        buffer = new StringBuffer();
                        boolean flag = true;
                        do {
                            buffer.append(conteudoLinha[cont++]);
                            if (cont == conteudoLinha.length) {
                                flag = false;
                                throw new ExcecaoLexico("ERRO LEXICO, " + buffer.toString() + ", " + (linha + 1) + ", " + (cont + 1 - buffer.length()));
                            }
                        } while (conteudoLinha[cont] != '\"');

                        if (flag) {
                            buffer.append("\"");
                            listTokens.add(new Tokens("STRING", buffer.toString(), linha + 1, cont + 2 - buffer.length()));
                            coluna += buffer.length() - 1;
                        }

                        buffer = new StringBuffer();
                    } else if (Tokens.simbolos(conteudoLinha[coluna])) {
                        Character ch = conteudoLinha[coluna];
                        listTokens.add(new Tokens(ch.toString(), ch.toString(), linha + 1, coluna + 1));
                        buffer = new StringBuffer();
                    } else if (!Character.isLetterOrDigit(proximo)) {
                        if (Tokens.palavraChave(buffer.toString())) {
                            listTokens.add(new Tokens(buffer.toString().toUpperCase(), buffer.toString(), linha + 1, coluna + 2 - buffer.length()));

                        } else if (Tokens.numero(buffer.toString())) {
                            listTokens.add(new Tokens("NUMERAL", buffer.toString(), linha + 1, coluna + 2 - buffer.length()));

                        } else if (Tokens.id(buffer.toString())) {
                            listTokens.add(new Tokens("ID", buffer.toString(), linha + 1, coluna + 2 - buffer.length()));
                        } else {
                            throw new ExcecaoLexico("ERRO LEXICO, " + buffer.toString() + ", " + (linha + 1) + ", " + (coluna + 2 - buffer.length()));
                        }
                        buffer = new StringBuffer();
                    }
                }
            }
        }
        listTokens.add(new Tokens("FINAL", "$", linha + 1, 0));
        return listTokens;
    }
}
