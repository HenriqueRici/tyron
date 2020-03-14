package utilitarios;

import java.util.Arrays;
import java.util.List;

public class FlagService {

    public void separador(String[] args) {
        List<String> listArgs = Arrays.asList(args);
        for (String argumento : listArgs) {
            if (argumento.contains("-")) {
                Flag.ativarFlag(argumento);
            }
        }
    }

    public static String caminho(String[] args) {
        for (String s: args){
            if (!(s.charAt(0) == '-')){
                return s;
            }
        }
       return null;
    }
}
