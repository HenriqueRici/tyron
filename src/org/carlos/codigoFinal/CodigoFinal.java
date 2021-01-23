package org.carlos.codigoFinal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodigoFinal {
    private static final StringBuffer bufferData = new StringBuffer();
    private static final StringBuffer bufferText = new StringBuffer();
    //private static final StringBuffer bufferBSS = new StringBuffer();

    private final LinkedList<String> intermediario;
    private static final List<String> declarados = new ArrayList<>();
    private static int contaMSG;
    private static String elemento;

    public CodigoFinal(LinkedList<String> intermediario) {
        this.intermediario = intermediario;
        bufferData.append("section .data\n");
        bufferData.append("\tinput: db \"%d\",0\n");
        bufferData.append("\toutput: db \"%d\",10,0\n");
        //bufferBSS.append("\nsection .bss\n");
        bufferText.append("\nsection .text\n\tglobal _main\n\textern _printf\n\textern _scanf\n");
        bufferText.append("_main:\n");
    }

    public StringBuffer geraCodigoFinal() {
        for (int i = 0; i < intermediario.size(); i++) {
            if (intermediario.get(i).equals("NUMBER")) {
                geraVariavel(i);
            } else if (intermediario.get(i).equals("OUTPUT")) {
                geraOutput(i);
            } else if (intermediario.get(i).equals("INPUT")) {
                geraInput(i);
            } else if (intermediario.get(i).equals("=")) {
                geraAtribuicao(i);
            } else if (intermediario.get(i).equals("IF")) {
                geraIF(i);
            } else if (intermediario.get(i).contains(".L") && intermediario.get(i).contains(":")) {
                geraLabel(i);
            } else if (intermediario.get(i).equals("GOTO ")) {
                geraGOTO(i);
            }

        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(bufferData).append(bufferText).append("ret");
        return buffer;
    }

    private void geraGOTO(int i) {
        var label = intermediario.get(i + 1);
        var verificaIF = intermediario.get(i - 3);
        if (!verificaIF.equals("IF")) {
            bufferText.append("\tjmp " + label + "\n\n");
        }


    }

    private void geraLabel(int i) {
        bufferText.append(intermediario.get(i) + "\n");
    }

    private void geraIF(int i) {
        var condicao = intermediario.get(i + 2).split(" ");
        String opA = condicao[0];
        String operador = condicao[1];
        String opB = condicao[2];
        String label = intermediario.get(i + 4);

        if (opA.matches("[0-9]*")) {
            bufferText.append("\tmov eax, ").append(opA).append("\n");
        } else {
            bufferText.append("\tmov eax, dword[").append(opA).append("]\n");
        }

        if (opB.matches("[0-9]*")) {
            bufferText.append("\tcmp eax, ").append(opB).append("\n");
        } else {
            bufferText.append("\tcmp eax, dword[").append(opB).append("]\n");
        }

        if (operador.equals("<=")) {
            bufferText.append("\tjle " + label + "\n\n");
        } else if (operador.equals(">=")) {
            bufferText.append("\tjge " + label + "\n\n");
        }

    }

    private void geraVariavel(int i) {
        elemento = intermediario.get(i + 2);
        declarados.add(elemento);
        bufferData.append("\t").append(elemento).append(": ").append("dd 0\n");
    }

    private void geraOutput(int i) {
        elemento = intermediario.get(i + 2);
        if (elemento.contains("\"")) {
            bufferData.append("\tmsg").append(contaMSG).append(": db ").append(elemento).append(",10,0\n");
            bufferText.append("\tpush msg").append(contaMSG).append("\n");
            bufferText.append("\tcall _printf" + "\n");
            bufferText.append("\tadd esp, 4" + "\n");
            contaMSG++;
        } else {
            bufferText.append("\tmov ebx, dword[").append(elemento).append("]\n");
            bufferText.append("\tpush ebx\n");
            bufferText.append("\tpush output\n");
            bufferText.append("\tcall _printf" + "\n");
            bufferText.append("\tadd esp, 8" + "\n");
        }
        bufferText.append("\n");
    }

    private void geraInput(int i) {
        elemento = intermediario.get(i + 2);
        bufferText.append("\tpush ").append(elemento).append("\n");
        bufferText.append("\tpush input\n");
        bufferText.append("\tcall _scanf\n");
        bufferText.append("\tadd esp, 8\n");
        bufferText.append("\n");
    }

    private void geraAtribuicao(int i) {
        var anterior = intermediario.get(i - 2);
        var posterior = intermediario.get(i + 2).split(" ");
        String opA = null;
        String opB = null;
        String operador = null;

        for (int cont = 0; cont < posterior.length; cont++) {
            if (cont == 0) {
                opA = posterior[cont];
            } else if (cont == 1) {
                opB = posterior[cont];
            } else if (cont == 2) {
                operador = posterior[cont];
            }
        }
        if (!declarados.contains(anterior)) {
            bufferData.append("\t").append(anterior).append(": ").append("dd 0\n");
        }
        if (opA != null) {

            if (opA.matches("[0-9]*")) {
                bufferText.append("\tmov eax, ").append(opA).append("\n");
            } else {
                bufferText.append("\tmov eax, dword[").append(opA).append("]\n");
            }
        }
        if (opB != null) {
            if (opB.matches("[0-9]*")) {
                bufferText.append("\tmov ecx, ").append(opB).append("\n");
            } else {
                bufferText.append("\tmov ecx, dword[").append(opB).append("]\n");
            }
        }
        if (operador != null) {
            switch (operador) {
                case "+":
                    bufferText.append("\tadd eax, ecx\n");
                    break;
                case "-":
                    bufferText.append("\tsub eax, ecx\n");
                    break;
                case "*":
                    bufferText.append("\tmul ecx\n");
                    break;
                case "/":
                    bufferText.append("\tmov edx, 0\n");
                    bufferText.append("\tdiv ecx\n");
                    break;
            }
        }
        bufferText.append("\tmov dword[").append(anterior).append("], eax\n\n");
    }


    public void geraArquivo(StringBuffer saida, String caminho) throws IOException {
        int index = caminho.lastIndexOf("\\") + 1;
        String caminhoPasta = caminho.substring(0, index);
        FileWriter arq = new FileWriter(caminhoPasta + "CodigoFinal.asm");
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.print(saida);
        arq.close();
    }

}
