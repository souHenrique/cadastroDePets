package util;

import java.util.regex.Pattern;

public final class Validador {

    private static final Pattern PADRAO_NOME = Pattern.compile("^[a-zA-Z]+(\\s[a-zA-Z]+)+$");
    private static final Pattern PADRAO_IDADE_E_PESO = Pattern.compile("^\\d+([,.]\\d+)?$");
    private static final Pattern PADRAO_RACA = Pattern.compile("^[a-zA-Z\\s]+$");

    private Validador() {
    }

    public static String validarNomeObrigatorio(String nomeCompleto) {
        String nome = nomeCompleto.trim();

        if (nome.isBlank()) {
            throw new IllegalArgumentException("O pet deve ter um nome e sobrenome.");
        }

        if (!PADRAO_NOME.matcher(nome).matches()) {
            throw new IllegalArgumentException("Nome inválido, tente novamente.");
        }

        return nome;
    }

    public static String validarNomeAlterado(String nomeCompleto, String valorAtual) {
        String nome = nomeCompleto.trim();

        if (nome.isBlank()) {
            return valorAtual;
        }

        if (!PADRAO_NOME.matcher(nome).matches()) {
            throw new IllegalArgumentException("Nome inválido, tente novamente.");
        }

        return nome;
    }

    public static String validarRacaNova(String raca) {
        String valor = raca.trim();
        if (valor.isBlank()) {
            return Constantes.NAO_INFORMADO;
        }

        if (!PADRAO_RACA.matcher(valor).matches()) {
            throw new IllegalArgumentException("Raça inválida.");
        }

        return valor;
    }

    public static String validarRacaAlterada(String raca, String valorAtual) {
        String valor = raca.trim();
        if (valor.isBlank()) {
            return valorAtual;
        }

        if (!PADRAO_RACA.matcher(valor).matches()) {
            throw new IllegalArgumentException("Raça inválida.");
        }

        return valor;
    }

    public static double validarValorIdadeEmAnos(String idadeTexto) {
        String valorNormalizado = idadeTexto.replace(" anos", "").replace(",", ".").trim();

        if (!PADRAO_IDADE_E_PESO.matcher(valorNormalizado).matches()) {
            throw new IllegalArgumentException("Idade inválida. Digite apenas números.");
        }

        double idadeValor = Double.parseDouble(valorNormalizado);
        if (idadeValor > 20) {
            throw new IllegalArgumentException("Idade inválida. O pet não pode ter mais de 20 anos.");
        }

        return idadeValor;
    }

    public static double validarValorIdadeEmMeses(String idadeTexto) {
        String valorNormalizado = idadeTexto.replace(",", ".").trim();

        if (!PADRAO_IDADE_E_PESO.matcher(valorNormalizado).matches()) {
            throw new IllegalArgumentException("Idade inválida. Digite apenas números.");
        }

        double idadeMeses = Double.parseDouble(valorNormalizado);
        double idadeEmAnos = idadeMeses / 12.0;

        if (idadeEmAnos > 20) {
            throw new IllegalArgumentException("Idade inválida. O pet não pode ter mais de 20 anos.");
        }

        return idadeEmAnos;
    }

    public static double validarValorPeso(String pesoTexto) {
        String valorNormalizado = pesoTexto.replace("kg", "").replace(",", ".").trim();

        if (!PADRAO_IDADE_E_PESO.matcher(valorNormalizado).matches()) {
            throw new IllegalArgumentException("Peso inválido. Digite apenas números.");
        }

        double pesoValor = Double.parseDouble(valorNormalizado);
        if (pesoValor > 60 || pesoValor < 0.5) {
            throw new IllegalArgumentException("Peso inválido. O peso deve estar entre 0.5kg e 60kg.");
        }

        return pesoValor;
    }

    public static boolean criterioValido(String criterio) {
        String valor = criterio.toLowerCase().trim();
        return valor.equals("nome") ||
                valor.equals("sexo") ||
                valor.equals("idade") ||
                valor.equals("peso") ||
                valor.equals("raca") ||
                valor.equals("raça") ||
                valor.equals("endereco") ||
                valor.equals("endereço");
    }
}
