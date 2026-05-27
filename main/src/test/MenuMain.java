package test;

import domain.*;
import enums.SexoDoPet;
import enums.TipoPet;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuMain {

    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> listaPerguntas = new ArrayList<>();
        List<Pet> listaPets = new ArrayList<>();

        File nomeFormulario = new File("formulario.txt");
        File pastaPetsCadastrados = new File("petsCadastrados");

        Pattern padraoNome = Pattern.compile("^[a-zA-Z]+(\\s[a-zA-Z]+)+$");
        Pattern padraoIdadeEPeso = Pattern.compile("^\\d+([,.]\\d+)?$");
        Pattern padraoRaca = Pattern.compile("^[a-zA-Z\\s]+$");

        System.out.println("==================================================================================");
        System.out.println("                                  PET SHOP");

        while (true) {
            try {
                System.out.println("==================================================================================");
                System.out.print("1. Cadastrar um novo pet\n" +
                        "2. Alterar os dados do pet cadastrado\n" +
                        "3. Deletar um pet cadastrado\n" +
                        "4. Listar todos os pets cadastrados\n" +
                        "5. Listar pets por algum critério (idade, nome, raça)\n" +
                        "6. Sair\n" +
                        "Selecione a sua opção: ");
                String opcao = input.nextLine();

                if (opcao.matches("[1-6]")) {
                    int opc = Integer.parseInt(opcao);
                    if (opc == 6) {
                        System.out.println("O programa foi encerrado...");
                        break;
                    }
                    else if (opc == 1) {
                        listaPerguntas.clear();
                        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nomeFormulario))) {
                            String linha;
                            while ((linha = bufferedReader.readLine()) != null) {
                                listaPerguntas.add(linha);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println(listaPerguntas.get(0));
                        String nomeCompleto = input.nextLine().trim();
                        Matcher matcherNome = padraoNome.matcher(nomeCompleto);
                        if (nomeCompleto.isBlank()) {
                            throw new IllegalArgumentException("O pet deve ter um nome e sobrenome.");
                        }
                        if (!matcherNome.matches()) {
                            throw new IllegalArgumentException("Nome inválido, tente novamente.");
                        }

                        TipoPet tipoPet;
                        System.out.println(listaPerguntas.get(1));
                        String tipoPetInformado = input.nextLine();
                        try {
                            tipoPet = TipoPet.valueOf(tipoPetInformado.toUpperCase().trim());
                        } catch (IllegalArgumentException e){
                            throw new IllegalArgumentException("Tipo de Pet inválido.");
                        }

                        SexoDoPet sexoDoPet;
                        System.out.println(listaPerguntas.get(2));
                        String sexoDoPetInformado = input.nextLine();
                        try {
                            sexoDoPet = SexoDoPet.valueOf(sexoDoPetInformado.toUpperCase().trim());
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Sexo do Pet Inválido.");
                        }

                        System.out.println(listaPerguntas.get(3));
                        System.out.print("Número da casa: ");
                        String numeroCasa = input.nextLine().trim();
                        if (numeroCasa.isBlank()) {
                            numeroCasa = NAO_INFORMADO;
                        }
                        System.out.print("Cidade: ");
                        String cidade = input.nextLine().trim();
                        if (cidade.isBlank()) cidade = NAO_INFORMADO;
                        System.out.print("Rua: ");
                        String rua = input.nextLine().trim();
                        if (rua.isBlank()) rua = NAO_INFORMADO;
                        Endereco enderecoPet = new Endereco(numeroCasa, cidade, rua
                        );

                        System.out.println(listaPerguntas.get(4));
                        String idadeTexto = input.nextLine().trim();
                        String idadeSalva;
                        if (idadeTexto.isBlank()) {
                            idadeSalva = NAO_INFORMADO;
                        } else {
                            idadeTexto = idadeTexto.replace(",", ".");
                            Matcher matcherIdade = padraoIdadeEPeso.matcher(idadeTexto);

                            if (!matcherIdade.matches()) {
                                throw new IllegalArgumentException("Idade inválida. Digite apenas números.");
                            }

                            double idadeValor = Double.parseDouble(idadeTexto);

                            if (idadeValor > 20) {
                                throw new IllegalArgumentException("Idade inválida. O pet não pode ter mais de 20 anos.");
                            }

                            if (idadeValor < 1) {
                                idadeSalva = String.valueOf(idadeValor);
                            } else {
                                idadeSalva = String.valueOf(idadeValor);
                            }
                        }

                        System.out.println(listaPerguntas.get(5));
                        String pesoTexto = input.nextLine().trim();

                        String pesoSalvo;
                        if (pesoTexto.isBlank()) {
                            pesoSalvo = NAO_INFORMADO;
                        } else {
                            pesoTexto = pesoTexto.replace(",", ".");
                            Matcher matcherPeso = padraoIdadeEPeso.matcher(pesoTexto);

                            if (!matcherPeso.matches()) {
                                throw new IllegalArgumentException(("Peso inválido. Digite apenas números."));
                            }

                            double pesoValor = Double.parseDouble(pesoTexto);

                            if (pesoValor > 60 || pesoValor < 0.5) {
                                throw new IllegalArgumentException("Peso inválido. O peso deve estar entre 0.5kg e 60kg.");
                            }

                            pesoSalvo = String.valueOf(pesoValor);
                        }

                        System.out.println(listaPerguntas.get(6));
                        String raca = input.nextLine().trim();
                        if (raca.isBlank()) {
                            raca = NAO_INFORMADO;
                        } else {
                            Matcher matcherRaca = padraoRaca.matcher(raca);
                            if (!matcherRaca.matches()) {
                                throw new IllegalArgumentException("Raça inválida.");
                            }
                        }

                        Pet pet = new Pet(nomeCompleto, tipoPet, sexoDoPet, enderecoPet, idadeSalva, pesoSalvo, raca);

                        try {
                            if (!pastaPetsCadastrados.exists()) {
                                pastaPetsCadastrados.mkdirs();
                            }
                        } catch (Exception e) {
                            System.out.println("Erro ao criar a pasta.");
                        }

                        LocalDateTime dataAtual = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
                        String nomePetArquivo = nomeCompleto.replaceAll("\\s", "").toUpperCase();
                        String nomeArquivoFormatado = dataAtual.format(formatter) + "-" + nomePetArquivo + ".TXT";
                        File petCadastrado = new File(pastaPetsCadastrados, nomeArquivoFormatado);

                        try {
                            if (!petCadastrado.exists()) {
                                petCadastrado.createNewFile();
                            }
                        } catch (IOException e) {
                            System.out.println("Erro ao criar o arquivo.");
                        }

                        String idadeFormatada = idadeSalva.equals(NAO_INFORMADO) ? NAO_INFORMADO : idadeSalva + " anos";
                        String pesoFormatado = pesoSalvo.equals(NAO_INFORMADO) ? NAO_INFORMADO : pesoSalvo + "kg";

                        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(petCadastrado))) {
                            bufferedWriter.write("1 - " + nomeCompleto);
                            bufferedWriter.newLine();
                            bufferedWriter.write("2 - " + tipoPet);
                            bufferedWriter.newLine();
                            bufferedWriter.write("3 - " + sexoDoPet);
                            bufferedWriter.newLine();
                            bufferedWriter.write("4 - " + enderecoPet.getRua() + ", " + enderecoPet.getNumeroCasa() + ", " + enderecoPet.getCidade());
                            bufferedWriter.newLine();
                            bufferedWriter.write("5 - " + idadeFormatada);
                            bufferedWriter.newLine();
                            bufferedWriter.write("6 - " + pesoFormatado);
                            bufferedWriter.newLine();
                            bufferedWriter.write("7 - " + raca);
                            bufferedWriter.newLine();
                        } catch (IOException e) {
                            System.out.println("Erro ao escrever no arquivo.");
                        }

                    }
                    else if (opc == 2) {
                        System.out.println("teste");
                    }
                    else if (opc == 3) {
                        System.out.println("teste");
                    }
                    else if (opc == 4) {
                        System.out.println("teste");
                    }
                    else if (opc == 5) {
                        TipoPet tipoPet;
                        System.out.println("Digite o tipo de animal que deseja buscar: ");
                        String tipoPetString = input.nextLine();

                        try {
                            tipoPet = TipoPet.valueOf(tipoPetString.toUpperCase().trim());
                        } catch (IllegalArgumentException e){
                            throw new IllegalArgumentException("Tipo de Pet inválido.");
                        }

                        System.out.println("Você deseja utilizar 1 ou 2 critérios de busca? ");
                        String quantidadeTexto = input.nextLine().trim();

                        if (!quantidadeTexto.matches("[12]")) {
                            throw new IllegalArgumentException("Digite apenas 1 ou 2.");
                        }

                        int quantCriterios = Integer.parseInt(quantidadeTexto);

                        System.out.println("Critérios disponíveis: nome, sexo, idade, peso, raca, endereco");
                        System.out.print("Digite o primeiro critério: ");
                        String criterio1 = input.nextLine().trim();

                        System.out.print("Digite o valor do primeiro critério: ");
                        String valor1 = input.nextLine().trim();

                        List<Pet> resultados;

                        if (quantCriterios == 1) {
                            resultados = buscarArquivo.buscarPets("petsCadastrados", tipoPet, criterio1, valor1);
                        } else {
                            System.out.print("Digite o segundo critério: ");
                            String criterio2 = input.nextLine().trim();

                            System.out.print("Digite o valor do segundo critério: ");
                            String valor2 = input.nextLine().trim();

                            resultados = buscarArquivo.buscarPets("petsCadastrados", tipoPet, criterio1, valor1, criterio2, valor2);
                        }

                        if (resultados.isEmpty()) {
                            System.out.println("Nenhum resultado encontrado.");
                        } else {
                            for (int i = 0; i < resultados.size(); i++) {
                                Pet pet = resultados.get(i);

                                System.out.println((i + 1) + ". " +
                                        pet.getNomeCompleto() + " - " +
                                        pet.getTipoPet() + " - " +
                                        pet.getSexoDoPet() + " - " +
                                        pet.getEndereco().getRua() + ", " +
                                        pet.getEndereco().getNumeroCasa() + " - " +
                                        pet.getEndereco().getCidade() + " - " +
                                        pet.getIdade() + " - " +
                                        pet.getPeso() + " - " +
                                        pet.getRaca());
                            }
                        }

                        System.out.println("==============================================================");
                    }
                } else {
                    System.out.println("Opção inválida, tente novamente...");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
