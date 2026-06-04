package domain;

import enums.SexoDoPet;
import enums.TipoPet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuscarArquivo {

    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    public static List<Pet> buscarPets(String caminhoPastaPet, TipoPet tipoPet, String criterio, String valorBusca) {
        List<Pet> resultados = new ArrayList<>();
        List<Pet> pets = lerPetsDaPasta(caminhoPastaPet);

        for (Pet pet : pets) {
            if (pet.getTipoPet() == tipoPet &&
                    BuscarCriterio.correspondeAoCriterio(pet, criterio, valorBusca)) {
                resultados.add(pet);
            }
        }

        return resultados;
    }

    public static List<Pet> buscarPets(String caminhoPastaPet, TipoPet tipoPet, String criterio1, String valorBusca1, String criterio2, String valorBusca2) {
        List<Pet> resultados = new ArrayList<>();
        List<Pet> pets = lerPetsDaPasta(caminhoPastaPet);

        for (Pet pet : pets) {
            if (pet.getTipoPet() == tipoPet &&
                    BuscarCriterio.correspondeAoCriterio(pet, criterio1, valorBusca1) &&
                    BuscarCriterio.correspondeAoCriterio(pet, criterio2, valorBusca2)) {
                resultados.add(pet);
            }
        }

        return resultados;
    }

    private static List<Pet> lerPetsDaPasta(String caminhoPastaPet) {
        List<Pet> pets = new ArrayList<>();
        File pastaPet = new File(caminhoPastaPet);

        if (!pastaPet.exists() || !pastaPet.isDirectory()) {
            return pets;
        }

        File[] listaArquivosPet = pastaPet.listFiles((dir, name) ->
                name.toUpperCase().endsWith(".TXT") || name.toLowerCase().endsWith(".txt"));

        if (listaArquivosPet == null) {
            return pets;
        }

        for (File arquivoPet : listaArquivosPet) {
            Pet pet = lerPetDoArquivo(arquivoPet);
            if (pet != null) {
                pets.add(pet);
            }
        }

        return pets;
    }

    private static Pet lerPetDoArquivo(File arquivo) {
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

        return new Pet(nomeCompleto, tipoPet, sexoDoPet, endereco, idade, peso, raca);
    }

    private static String removerPrefixo(String linha) {
        return linha.substring(4).trim();
    }

    public static List<Pet> listarTodosPets(String caminhoPastaPet) {
        return lerPetsDaPasta(caminhoPastaPet);
    }
}