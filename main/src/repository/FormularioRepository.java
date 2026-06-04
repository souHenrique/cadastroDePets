package repository;

import util.Constantes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormularioRepository {

    public List<String> carregarPerguntas() {
        List<String> perguntas = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Constantes.NOME_ARQUIVO_FORMULARIO))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                perguntas.add(linha);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler o formulário.");
        }

        return perguntas;
    }
}
