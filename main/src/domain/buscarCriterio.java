package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class buscarCriterio {

    public static void buscarCriterioNoArquivo(File arquivo, String criterio) {
        List<String> listaRespostas = new ArrayList<>();
        int contaEncontros = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                listaRespostas.add(linha.substring(4));
            }

            boolean encontrou = existeNaLista(listaRespostas, criterio);

            if (encontrou) {
                imprimeBuscaComCriterio(listaRespostas);
                contaEncontros++;
            }

        } catch (IOException e) {
            System.out.println("Erro na busca.");
        }

        if (contaEncontros == 0) {
            System.out.println("Nenhum resultado encontrado.");
        }
    }

    public static void buscarCriterioNoArquivo(File arquivo, String criterio1, String criterio2) {
        List<String> listaRespostas = new ArrayList<>();
        int contaEncontros = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                listaRespostas.add(linha.substring(4));
            }

            boolean encontrou1 = existeNaLista(listaRespostas, criterio1);
            boolean encontrou2 = existeNaLista(listaRespostas, criterio2);

            if (encontrou1 && encontrou2) {
                imprimeBuscaComCriterio(listaRespostas);
                contaEncontros++;
            }

        } catch (IOException e) {
            System.out.println("Erro na busca.");
        }
        if (contaEncontros == 0) {
            System.out.println("Nenhum resultado encontrado.");
        }
    }

    private static boolean existeNaLista(List<String> listaRespostas, String criterio) {
        String criterioMinusculo = criterio.trim().toLowerCase();

        for (String resposta : listaRespostas) {
            String respostaMinuscula = resposta.toLowerCase();
            if (respostaMinuscula.contains((criterioMinusculo))) {
                return true;
            }
        }
        return false;
    }

    private static void imprimeBuscaComCriterio(List<String> listaRespostas) {
        String resultado = String.join(" - ", listaRespostas);
        System.out.println(resultado);
    }
}
