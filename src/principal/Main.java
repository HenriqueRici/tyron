package principal;

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
        Arquivo arquivo = new Arquivo();
        List<String> listaArq = arquivo.lerArquivo(FlagService.caminho(args));
        Lexico lexico = new Lexico();
        List<char[]> listaDesfragmentada = lexico.desfragmentador(listaArq);
        List<Tokens> listaTokens = lexico.analisador(listaDesfragmentada);
        FlagService flagService = new FlagService();
        flagService.separador(args);

        System.out.println("Iniciando analise lexica!");
        if (Flag.LEXICO.isAtivo()) {
            try{
                System.out.println("Token, Lexema, Linha, Coluna");
                listaTokens.forEach(System.out::println);
            }catch (ExcecaoLexico ex){
                ex.printStackTrace();
            }


        }
        System.out.println("Analise lexica concluida com sucesso!");
    }
}
