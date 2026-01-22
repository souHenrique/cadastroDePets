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
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int posicao = 1;

            while ((linha = bufferedReader.readLine()) != null) {
                if (linha.contains(criterio)) {
                    System.out.print(posicao + ". ");
                    posicao++;
                    while ((linha = bufferedReader.readLine()) != null) {
                        listaRespostas.add(linha);
                    }
                    int quantidadeRespostas = 0;
                    for (String resposta : listaRespostas) {
                        if (quantidadeRespostas < listaRespostas.size()) {
                            System.out.print(resposta + " - ");
                            quantidadeRespostas++;
                        } else {
                            System.out.print(resposta);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro na busca.");
        }
    }
}
