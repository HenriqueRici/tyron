package org.carlos.principal;

import org.carlos.codigoFinal.CodigoFinal;
import org.carlos.intermediario.Intermediario;
import org.carlos.semantico.Semantico;
import org.carlos.sintatico.Sintatico;
import org.carlos.lexico.Lexico;
import org.carlos.lexico.Tokens;
import org.carlos.utilitarios.Arquivo;
import org.carlos.utilitarios.FlagService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
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

        System.out.println("Iniciando analise semantica!");
        Semantico semantico = new Semantico(listaTokens);
        semantico.analisador();
        System.out.println("Analise semantica concluida com sucesso!\n");

        Intermediario intermediario = new Intermediario(listaTokens, new ArrayList<>(semantico.getListaDeclarados().keySet()));
        var codigoInter = intermediario.gerarCodigo();

        CodigoFinal codigoFinal = new CodigoFinal(codigoInter);
        var finalCodigo = codigoFinal.geraCodigoFinal();
        codigoFinal.geraArquivo(finalCodigo, FlagService.caminho(args));


    }
}
