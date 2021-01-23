package org.carlos.intermediario;

import org.carlos.lexico.Tokens;
import org.carlos.utilitarios.ConverteInfixo;
import org.carlos.utilitarios.Flag;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static org.carlos.sintatico.Terminal.*;

public class Intermediario {

    private final List<Tokens> tokens;
    private final List<String> declarados;
    private final Stack<String> stackLabel;
    private int labelCont;
    private int varCount;

    private final LinkedList<String> codigo;

    public Intermediario(List<Tokens> tokens, List<String> declarados) {
        this.tokens = tokens;
        this.declarados = declarados;
        this.codigo = new LinkedList<>();
        this.stackLabel = new Stack<>();
    }

    public LinkedList<String> gerarCodigo() {
        AtomicInteger contador = new AtomicInteger(0);
        while (contador.get() < tokens.size()) {
            var token = tokens.get(contador.get());
            // Gerar código para o IF
            if (IF.equals(token.getToken())) {
                geraIf(contador);
            }
            // Gerar código para o Else
            else if (ELSE.equals(token.getToken())) {
                geraElse();
            }
            // Gerar código para o While
            else if (WHILE.equals(token.getToken())) {
                geraWhile(contador);
            }
            // Gerar código para endif
            else if (ENDIF.equals(token.getToken())) {
                geraEndIf();
            }
            // Gerar código para endwhile
            else if (ENDWHILE.equals(token.getToken())) {
                geraEndWhile();
            }
            // Gerar código para variavel
            else if (NUMBER.equals(token.getToken())) {
                geraNumber(contador);
            }
            // Gerar código para Identificador
            else if (IDENTIFIER.equals(token.getToken())) {
                geraIdentificador(contador);
            }
            // Gerar código para output
            else if (OUTPUT.equals(token.getToken())) {
                geraOutput(contador);
            }
            // Gerar código para input
            else if (INPUT.equals(token.getToken())) {
                geraInput(contador);
            }
            contador.incrementAndGet();
        }
        print();
        //this.codigo.forEach(System.out::print);
        // devolver o pseudo código asm para o main
        // System.out.println(this.codigo);
        return codigo;

    }

    private void geraIf(AtomicInteger cont) {
        codigo.add("IF");
        codigo.add(" ");
        cont.addAndGet(2);
        List<String> condicao = new ArrayList<>();
        // Percorre a condicional até o final e armazena na lista condicao
        while (!(tokens.get(cont.get())).getIdentificador().equals(")")) {
            condicao.add(tokens.get(cont.getAndIncrement()).getIdentificador());
            condicao.add(" ");
        }

        // Inverte a condição da lista
        inverteCondicao(condicao);
        codigo.add(converteList(condicao));
        stackLabel.push(".L" + (++labelCont) + ":");
        codigo.add("GOTO ");
        //codigo.add(" ");
        codigo.add(".L" + labelCont);
        codigo.add("\n");
    }

    private void geraElse() {
        codigo.add("GOTO ");
       // codigo.add(" ");
        codigo.add(".L" + (++labelCont));

        codigo.add("\n");
        codigo.add(stackLabel.peek());
        stackLabel.pop();
        stackLabel.push(".L" + labelCont + ":");
        codigo.add(" ");
        codigo.add("\n");
    }

    private void geraEndIf() {
        codigo.add(stackLabel.peek());
        stackLabel.pop();
        codigo.add("\n");
    }

    private void geraWhile(AtomicInteger cont) {
        var condicao = new LinkedList<String>();
        codigo.add(".L" + (++labelCont) + ":");
        codigo.add("\n");
        codigo.add("IF");
        codigo.add(" ");
        cont.addAndGet(2);
        while (!(tokens.get(cont.get()).getIdentificador().equals(")"))) {
            condicao.add(tokens.get(cont.getAndIncrement()).getIdentificador());
            condicao.add(" ");
        }
        inverteCondicao(condicao);
        codigo.add(converteList(condicao));
        codigo.add("GOTO ");
        //codigo.add(" ");
        codigo.add(".L" + (labelCont + 1));

        codigo.add("\n");

        stackLabel.push(".L" + (labelCont + 1) + ":");
        stackLabel.push(".L" + (labelCont++));
        stackLabel.push("GOTO ");
       // stackLabel.push(" ");
    }

    private void geraEndWhile() {
        codigo.add(stackLabel.peek());
        stackLabel.pop();
        codigo.add(stackLabel.peek());
        stackLabel.pop();
        codigo.add("\n");
        codigo.add(stackLabel.peek());
        stackLabel.pop();
        codigo.add("\n");
    }

    private void geraNumber(AtomicInteger cont) {
        cont.incrementAndGet();
        codigo.add("NUMBER");
        codigo.add(" ");
        codigo.add(tokens.get(cont.getAndIncrement()).getIdentificador());
        codigo.add(" ");
        codigo.add("\n");
    }

    private void geraIdentificador(AtomicInteger cont) {
        String comando = tokens.get(cont.get()).getIdentificador();
        if (tokens.get(cont.incrementAndGet()).getIdentificador().equals("=")) {

            var expressao = new StringBuilder();
            while (!(tokens.get(cont.incrementAndGet()).getIdentificador().equals(";"))) {
                expressao.append(tokens.get(cont.get()).getIdentificador());

            }
            var posfixo = ConverteInfixo.infixToPostFix(expressao.toString());

            var arr = threeAddressCode(posfixo);

            for (int j = 0; j < (arr.size() - 1); j++) {
                //codigo.add("_t" + ((varCount++) % 2) + " = " + arr.get(j) + "\n");
                codigo.add("_t" + (varCount++));
                codigo.add(" ");
                codigo.add("=");
                codigo.add(" ");
//                for (String o : arr){
//                    codigo.add(o);
//                    codigo.add(" ");
//                }
                codigo.add(arr.get(j));
                codigo.add(" ");
                codigo.add("\n");
            }
            //codigo.add(comando + arr.get(arr.size() - 1));
            codigo.add(comando);
            codigo.add(" ");
            codigo.add("=");
            codigo.add(" ");
            codigo.add(arr.get(arr.size() - 1));
            codigo.add(" ");

        }
        codigo.add("\n");
    }

    private void geraOutput(AtomicInteger cont) {
        cont.addAndGet(2);
        codigo.add("OUTPUT");
        codigo.add(" ");
        codigo.add(tokens.get(cont.get()).getIdentificador());
        codigo.add("\n");
    }

    private void geraInput(AtomicInteger cont) {
        cont.addAndGet(2);
        codigo.add("INPUT");
        codigo.add("  ");
        codigo.add(tokens.get(cont.get()).getIdentificador());
        codigo.add("\n");
    }

    private void inverteCondicao(List<String> condicao) {
        for (int i = 0; i < condicao.size(); i++) {
            var membroDaCondicional = condicao.get(i);
            switch (membroDaCondicional) {
                case ">":
                    condicao.set(i, "<=");
                    break;
                case "<":
                    condicao.set(i, ">=");
                    break;
            }
        }
    }

    private String converteList(List<String> condicao) {
        var builder = new StringBuilder();
        condicao.forEach(builder::append);
        return builder.toString();
    }

    private List<String> threeAddressCode(List<String> postfix) {
        LinkedList<String> result = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        Predicate<String> isOperator = (str) -> asList("+", "-", "*", "/").contains(str);

        var temp = this.varCount;

        for (String token : postfix) {
            if (isOperator.test(token)) {
                var b = stack.peek();
                stack.pop();
                var a = stack.peek();
                stack.pop();
                result.addLast(a + " " + b + " " + token);
                stack.push("_t" + (varCount++));
            } else {
                stack.push(token);
            }
        }

        if (result.isEmpty()) {
            result.addLast(stack.peek());
        }

        varCount = temp;
        return result;
    }

    public void print() {
        if (Flag.INTERMEDIARIO.isAtivo()) {
            this.codigo.forEach(System.out::print);
        }
    }
}
