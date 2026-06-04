package service;

import domain.Pet;
import domain.PetArquivo;
import repository.PetArquivoRepository;
import util.Constantes;
import util.Formatador;

import java.io.File;
import java.util.List;

public class PetService {

    private final PetArquivoRepository petArquivoRepository;

    public PetService(PetArquivoRepository petArquivoRepository) {
        this.petArquivoRepository = petArquivoRepository;
    }

    public void salvarNovoPet(Pet pet) {
        File pastaPetsCadastrados = new File(Constantes.CAMINHO_PASTA_PETS);
        if (!pastaPetsCadastrados.exists() && !pastaPetsCadastrados.mkdirs()) {
            throw new IllegalStateException("Erro ao criar a pasta.");
        }

        File petCadastrado = new File(pastaPetsCadastrados, Formatador.gerarNomeArquivo(pet.getNomeCompleto()));
        petArquivoRepository.salvarPetNoArquivo(pet, petCadastrado);
    }

    public PetArquivo selecionarPet(List<PetArquivo> resultados, String escolhaTexto) {
        if (!escolhaTexto.matches("\\d+")) {
            throw new IllegalArgumentException("Número inválido. A busca será exibida novamente.");
        }

        int escolha = Integer.parseInt(escolhaTexto);
        if (escolha < 1 || escolha > resultados.size()) {
            throw new IllegalArgumentException("Número inválido. A busca será exibida novamente.");
        }

        return resultados.get(escolha - 1);
    }

    public void alterarPet(PetArquivo selecionado, Pet petAtualizado) {
        petArquivoRepository.salvarPetNoArquivo(petAtualizado, selecionado.getArquivo());
    }

    public boolean deletarPet(PetArquivo selecionado, String confirmacao) {
        if (confirmacao.equalsIgnoreCase("SIM")) {
            petArquivoRepository.deletarPetArquivo(selecionado.getArquivo());
            return true;
        }

        if (confirmacao.equalsIgnoreCase("NÃO") || confirmacao.equalsIgnoreCase("NAO")) {
            return false;
        }

        throw new IllegalArgumentException("Resposta inválida. Digite SIM ou NÃO.");
    }
}
