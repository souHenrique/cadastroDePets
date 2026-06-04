package domain;

import enums.SexoDoPet;
import enums.TipoPet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuscarArquivo {

    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    public static List<PetArquivo> buscarPets(String caminhoPastaPet, TipoPet tipoPet, String criterio, String valorBusca) {
        List<PetArquivo> resultados = new ArrayList<>();
        List<PetArquivo> registros = lerRegistrosDaPasta(caminhoPastaPet);

        for (PetArquivo registro : registros) {
            Pet pet = registro.getPet();
            if (pet.getTipoPet() == tipoPet &&
                    BuscarCriterio.correspondeAoCriterio(pet, criterio, valorBusca)) {
                resultados.add(registro);
            }
        }

        return resultados;
    }

    public static List<PetArquivo> buscarPets(String caminhoPastaPet, TipoPet tipoPet, String criterio1, String valorBusca1, String criterio2, String valorBusca2) {
        List<PetArquivo> resultados = new ArrayList<>();
        List<PetArquivo> registros = lerRegistrosDaPasta(caminhoPastaPet);

        for (PetArquivo registro : registros) {
            Pet pet = registro.getPet();
            if (pet.getTipoPet() == tipoPet &&
                    BuscarCriterio.correspondeAoCriterio(pet, criterio1, valorBusca1) &&
                    BuscarCriterio.correspondeAoCriterio(pet, criterio2, valorBusca2)) {
                resultados.add(registro);
            }
        }

        return resultados;
    }

    public static List<PetArquivo> listarTodosPets(String caminhoPastaPet) {
        return lerRegistrosDaPasta(caminhoPastaPet);
    }

    public static void salvarPetNoArquivo(Pet pet, File arquivo) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(arquivo))) {
            bufferedWriter.write("1 - " + pet.getNomeCompleto());
            bufferedWriter.newLine();
            bufferedWriter.write("2 - " + pet.getTipoPet());
            bufferedWriter.newLine();
            bufferedWriter.write("3 - " + pet.getSexoDoPet());
            bufferedWriter.newLine();
            bufferedWriter.write("4 - " + pet.getEndereco().getRua() + ", " + pet.getEndereco().getNumeroCasa() + ", " + pet.getEndereco().getCidade());
            bufferedWriter.newLine();
            bufferedWriter.write("5 - " + pet.getIdade());
            bufferedWriter.newLine();
            bufferedWriter.write("6 - " + pet.getPeso());
            bufferedWriter.newLine();
            bufferedWriter.write("7 - " + pet.getRaca());
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao escrever no arquivo.");
        }
    }

    public static void deletarPetArquivo(File arquivo) {
        if (arquivo == null || !arquivo.exists()) {
            throw new IllegalStateException("Arquivo do pet não encontrado.");
        }

        if (!arquivo.delete()) {
            throw new IllegalStateException("Não foi possível deletar o pet.");
        }
    }

    private static List<PetArquivo> lerRegistrosDaPasta(String caminhoPastaPet) {
        List<PetArquivo> registros = new ArrayList<>();
        File pastaPet = new File(caminhoPastaPet);

        if (!pastaPet.exists() || !pastaPet.isDirectory()) {
            return registros;
        }

        File[] listaArquivosPet = pastaPet.listFiles((dir, name) ->
                name.toUpperCase().endsWith(".TXT") || name.toLowerCase().endsWith(".txt"));

        if (listaArquivosPet == null) {
            return registros;
        }

        for (File arquivoPet : listaArquivosPet) {
            PetArquivo registro = lerRegistroDoArquivo(arquivoPet);
            if (registro != null) {
                registros.add(registro);
            }
        }

        return registros;
    }

    private static PetArquivo lerRegistroDoArquivo(File arquivo) {
        List<String> linhas = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                linhas.add(removerPrefixo(linha));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
            return null;
        }

        if (linhas.size() < 7) {
            return null;
        }

        String nomeCompleto = linhas.get(0);
        TipoPet tipoPet = TipoPet.valueOf(linhas.get(1).trim().toUpperCase());
        SexoDoPet sexoDoPet = SexoDoPet.valueOf(linhas.get(2).trim().toUpperCase());

        String[] partesEndereco = linhas.get(3).split(",");
        String rua = partesEndereco.length > 0 ? partesEndereco[0].trim() : NAO_INFORMADO;
        String numeroCasa = partesEndereco.length > 1 ? partesEndereco[1].trim() : NAO_INFORMADO;
        String cidade = partesEndereco.length > 2 ? partesEndereco[2].trim() : NAO_INFORMADO;

        Endereco endereco = new Endereco(numeroCasa, cidade, rua);

        String idade = linhas.get(4);
        String peso = linhas.get(5);
        String raca = linhas.get(6);

        Pet pet = new Pet(nomeCompleto, tipoPet, sexoDoPet, endereco, idade, peso, raca);
        return new PetArquivo(pet, arquivo);
    }

    private static String removerPrefixo(String linha) {
        return linha.substring(4).trim();
    }
}
