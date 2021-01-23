package org.carlos.utilitarios;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface SeparadorDeLinha extends UnaryOperator<List<String>> {
    /*
     * recebe um delimitador e insere no formatador de regex
     * o regex particiona a String de acordo com o delimitador,
     * porem mantem o mesmo na String
     * ao final da Stream e instanciada uma lista que recebe a String particionada
     * Combinator Pattern
     * */
    static SeparadorDeLinha splitBy(String delimiter) {
//        final String formatter = "((%1$s=>?)|(?=%1$s))";
        final String formatter = "((?<=%1$s)|(?=%1$s))";
        final String formattedDelimiter = String.format(formatter, delimiter);
        return line -> line.stream()
                .map(str -> str.split(formattedDelimiter))
                .flatMap(Stream::of)
                .collect(Collectors.toList());
    }

    /*
     * funciona como um encadeador de metodos
     * */
    default SeparadorDeLinha andThen(SeparadorDeLinha other) {
        return line -> {
            List<String> otherResult = other.apply(line);
            return this.apply(otherResult);
        };
    }

}