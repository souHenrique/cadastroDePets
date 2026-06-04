package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        return formatarNumeroSemZeros(valor) + " anos";
    }

    public static String formatarPesoExibicao(String peso) {
        if (peso.equals(Constantes.NAO_INFORMADO)) {
            return peso;
        }

        String valor = peso.replace("kg", "").trim();
        return formatarNumeroSemZeros(valor) + "kg";
    }

    public static String formatarIdadePersistencia(double idadeValor) {
        return formatarNumeroSemZeros(idadeValor) + " anos";
    }

    public static String formatarPesoPersistencia(double pesoValor) {
        return formatarNumeroSemZeros(pesoValor) + "kg";
    }

    public static String formatarNumeroSemZeros(double valor) {
        return formatarNumeroSemZeros(Double.toString(valor));
    }

    public static String formatarNumeroSemZeros(String valorTexto) {
        double valor = Double.parseDouble(valorTexto.trim().replace(",", "."));
        if (valor == Math.rint(valor)) {
            return String.format(Locale.US, "%.0f", valor);
        }
        return Double.toString(valor);
    }
}
