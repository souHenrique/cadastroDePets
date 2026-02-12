package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class buscarCriterio {

    public static void buscarCriterioNoArquivo(File arquivo, String criterio) {
        boolean encontrou = false;
        List<String> listaRespostas = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                listaRespostas.add(linha.substring(4));
            }
            for (String resposta : listaRespostas) {
                if (resposta.equalsIgnoreCase(criterio)) {
                    encontrou = true;
                    for (int i = 0; i < listaRespostas.size(); i++) {
                        System.out.print(listaRespostas.get(i));
                        if (i < listaRespostas.size() - 1) {
                            System.out.print(" - ");
                        }
                        else {
                            System.out.println();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro na busca.");
        }
        if (!encontrou) {
            System.out.println("Nenhum valor encontrado.");
        }
    }

    //CORRIGIR: PRECISA MOSTRAR APENAS OS QUE TIVEREM AMBOS OS CRITÉRIOS

    public static void buscarCriterioNoArquivo(File arquivo, String criterio1, String criterio2) {
        List<String> listaRespostas = new ArrayList<>();
        boolean encontrou = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivo))) {
            String linha;


            while ((linha = bufferedReader.readLine()) != null) {
                listaRespostas.add(linha.substring(4));
            }
            for (String resposta : listaRespostas) {
                if (resposta.equalsIgnoreCase(criterio1) || resposta.equalsIgnoreCase(criterio2)) {
                    encontrou = true;
                    for (int i = 0; i < listaRespostas.size(); i++) {
                        System.out.print(listaRespostas.get(i));
                        if (i < listaRespostas.size() - 1) {
                            System.out.print(" - ");
                        }
                        else {
                            System.out.println();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro na busca.");
        }
        if (!encontrou) {
            System.out.println("Nenhum valor encontrado.");
        }
    }
}
