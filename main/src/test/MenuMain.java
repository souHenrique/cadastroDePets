package test;

import java.io.*;
import java.util.Scanner;

public class MenuMain {
    public static void main(String[] args) {
        File nomeFormulario = new File("formulario.txt");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("1. Cadastrar um novo pet\n" +
                                "2. Alterar os dados do pet cadastrado\n" +
                                "3. Deletar um pet cadastrado\n" +
                                "4. Listar todos os pets cadastrados\n" +
                                "5. Listar pets por algum critério (idade, nome, raça)\n" +
                                "6. Sair\n" +
                                "Selecione a sua opção: ");
            int opcao = input.nextInt();

            if (opcao == 6) {
                System.out.println("O programa foi encerrado...");
                break;
            }
            else if (opcao == 1) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nomeFormulario))) {
                    String linha;
                    while ((linha = bufferedReader.readLine()) != null) {
                        System.out.println(linha);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (opcao == 2) {
                System.out.println("teste");
            }
            else if (opcao == 3) {
                System.out.println("teste");
            }
            else if (opcao == 4) {
                System.out.println("teste");
            }
            else if (opcao == 5) {
                System.out.println("teste");
            }
            else {
                System.out.println("Opção inválida, tente novamente...");
            }
        }
    }
}
