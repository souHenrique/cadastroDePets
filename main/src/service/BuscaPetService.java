package service;

import domain.PetArquivo;
import enums.TipoPet;
import repository.PetArquivoRepository;
import util.Constantes;
import util.Validador;

import java.util.List;

public class BuscaPetService {

    private final PetArquivoRepository petArquivoRepository;

    public BuscaPetService(PetArquivoRepository petArquivoRepository) {
        this.petArquivoRepository = petArquivoRepository;
    }

    public List<PetArquivo> listarTodosPets() {
        return petArquivoRepository.listarTodosPets(Constantes.CAMINHO_PASTA_PETS);
    }

    public List<PetArquivo> buscarPets(TipoPet tipoPet, String quantidadeTexto, String criterio1, String valor1, String criterio2, String valor2) {
        if (!quantidadeTexto.matches("[12]")) {
            throw new IllegalArgumentException("Digite apenas 1 ou 2.");
        }

        if (!Validador.criterioValido(criterio1)) {
            throw new IllegalArgumentException("Critério inválido.");
        }

        int quantCriterios = Integer.parseInt(quantidadeTexto);
        if (quantCriterios == 1) {
            return petArquivoRepository.buscarPets(Constantes.CAMINHO_PASTA_PETS, tipoPet, criterio1, valor1);
        }

        if (!Validador.criterioValido(criterio2)) {
            throw new IllegalArgumentException("Critério inválido.");
        }

        if (criterio1.equalsIgnoreCase(criterio2)) {
            throw new IllegalArgumentException("Os critérios não podem ser iguais.");
        }

        return petArquivoRepository.buscarPets(Constantes.CAMINHO_PASTA_PETS, tipoPet, criterio1, valor1, criterio2, valor2);
    }
}
