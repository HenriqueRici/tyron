package utilitarios;

public enum Flag {
    LEXICO("-lexico", false),
    SINTATICO("-sintatico", false),
    SEMANTICO("-semantico", false),
    CODIGO_FINAL("-codigoFinal", false);

    private String flag;
    private boolean ativo;

    Flag(String flag, boolean ativo) {
        this.flag = flag;
        this.ativo = ativo;
    }

    public static void ativarFlag(String str) {
        for (Flag flag : Flag.values()) {
            if (flag.flag.equals(str)) {
                flag.setAtivo(true);
                return;
            }
        }
        throw new IllegalArgumentException("Flag " + str + " nao reconhecida!");
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getFlag() {
        return flag;
    }
}
