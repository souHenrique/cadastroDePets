package test;

import domain.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuMain {
    public static void main(String[] args) {
        File nomeFormulario = new File("formulario.txt");
        Scanner input = new Scanner(System.in);
        List<String> listaPerguntas = new ArrayList<>();
        List<Pet> listaPets = new ArrayList<>();

        Pattern padraoNome = Pattern.compile("^[a-zA-Z]+(\\s[a-zA-Z]+)+$");
        Pattern padraoIdadeEPeso = Pattern.compile("^\\d+([,.]\\d+)?$");
        Pattern padraoRaca = Pattern.compile("^[a-zA-Z\\s]+$");

        while (true) {
            try {
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
                        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nomeFormulario))) {
                            String linha;
                            while ((linha = bufferedReader.readLine()) != null) {
                                listaPerguntas.add(linha);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println(listaPerguntas.get(0));
                        String nomeCompleto = input.nextLine();
                        Matcher matcherNome = padraoNome.matcher(nomeCompleto);
                        if (nomeCompleto.isEmpty()) {
                            nomeCompleto = "NÃO INFORMADO";
                        } else if (!matcherNome.matches()) {
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
                        String numeroCasa = input.nextLine();
                        if (numeroCasa.isEmpty()) {
                            numeroCasa = "NÃO INFORMADO";
                        }
                        System.out.print("Cidade: ");
                        String cidade = input.nextLine();
                        System.out.print("Rua: ");
                        String rua = input.nextLine();
                        Endereco enderecoPet = new Endereco(numeroCasa, cidade, rua
                        );

                        System.out.println(listaPerguntas.get(4));
                        String idade = input.nextLine();
                        Matcher matcherIdade = padraoIdadeEPeso.matcher(idade);
                        idade = idade.replace(",", ".");
                        if (idade.isEmpty()) {
                            idade = "NÃO INFORMADO";
                        } else if (!matcherIdade.matches() || Integer.parseInt(idade) > 20) {
                            throw new IllegalArgumentException("Idade inválida.");
                        }

                        System.out.println(listaPerguntas.get(5));
                        String peso = input.nextLine();
                        Matcher matcherPeso = padraoIdadeEPeso.matcher(peso);
                        peso = peso.replace(",", ".");
                        if (peso.isEmpty()) {
                            peso = "NÃO INFORMADO";
                        }
                        if (!matcherPeso.matches() || (Integer.parseInt(peso) > 60 || Integer.parseInt(peso) < 0.5)) {
                            throw new IllegalArgumentException("Peso inválido.");
                        }

                        System.out.println(listaPerguntas.get(6));
                        String raca = input.nextLine();
                        Matcher matcherRaca = padraoRaca.matcher(raca);
                        if (!matcherRaca.matches()) {
                            throw new IllegalArgumentException("Raça inválida.");
                        }

                        Pet pet = new Pet(nomeCompleto, tipoPet, sexoDoPet, enderecoPet, idade, peso, raca);

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
                        System.out.println("teste");
                    }
                } else {
                    System.out.println("Opção inválida, tente novamente...");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
