package org.carlos.utilitarios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    public List<String> lerArquivo(String caminhoArquivo) {
        List<String> linhas = new ArrayList<>();
        try {
            BufferedReader arquivo = new BufferedReader(new FileReader(caminhoArquivo));
            while(arquivo.ready()) {
                linhas.add(arquivo.readLine());
            }
            arquivo.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return linhas;
    }

}
