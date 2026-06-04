package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Formatador {

    private Formatador() {
    }

    public static String gerarNomeArquivo(String nomeCompleto) {
        LocalDateTime dataAtual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String nomePetArquivo = nomeCompleto.replaceAll("\\s", "").toUpperCase();
        return dataAtual.format(formatter) + "-" + nomePetArquivo + ".TXT";
    }

    public static String formatarTextoExibicao(String texto) {
        String textoMinusculo = texto.toLowerCase();
        return Character.toUpperCase(textoMinusculo.charAt(0)) + textoMinusculo.substring(1);
    }

    public static String formatarIdadeExibicao(String idade) {
        if (idade.equals(Constantes.NAO_INFORMADO)) {
            return idade;
        }

        String valor = idade.replace(" anos", "").trim();
        if (valor.endsWith(".0")) {
            valor = valor.substring(0, valor.length() - 2);
        }

        return valor + " anos";
    }

    public static String formatarPesoExibicao(String peso) {
        if (peso.equals(Constantes.NAO_INFORMADO)) {
            return peso;
        }

        String valor = peso.replace("kg", "").trim();
        if (valor.endsWith(".0")) {
            valor = valor.substring(0, valor.length() - 2);
        }

        return valor + "kg";
    }

    public static String formatarIdadePersistencia(double idadeValor) {
        return idadeValor + " anos";
    }

    public static String formatarPesoPersistencia(double pesoValor) {
        return pesoValor + "kg";
    }
}
