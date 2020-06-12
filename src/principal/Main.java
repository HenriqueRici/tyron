package principal;

import sintatico.Sintatico;
import utilitarios.ExcecaoLexico;
import lexico.Lexico;
import lexico.Tokens;
import utilitarios.Arquivo;
import utilitarios.Flag;
import utilitarios.FlagService;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FlagService flagService = new FlagService();
        flagService.separador(args);
        Arquivo arquivo = new Arquivo();
        List<String> listaArq = arquivo.lerArquivo(FlagService.caminho(args));
        Lexico lexico = new Lexico();
        List<char[]> listaDesfragmentada = lexico.desfragmentador(listaArq);

        System.out.println("Iniciando analise lexica!");
        List<Tokens> listaTokens = lexico.analisador(listaDesfragmentada);
        System.out.println("Analise lexica concluida com sucesso!\n");



        System.out.println("Iniciando analise sintatica!");
        Sintatico sintatico = new Sintatico(listaTokens);
        sintatico.analisador();
        System.out.println("Analise sintatica concluida com sucesso!\n");



    }
}
