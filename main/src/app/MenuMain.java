package app;

import domain.BuscarArquivo;
import domain.Endereco;
import domain.Pet;
import domain.PetArquivo;
import enums.SexoDoPet;
import enums.TipoPet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MenuMain {

    private static final String NAO_INFORMADO = "NÃO INFORMADO";
    private static final Pattern PADRAO_NOME = Pattern.compile("^[a-zA-Z]+(\\s[a-zA-Z]+)+$");
    private static final Pattern PADRAO_IDADE_E_PESO = Pattern.compile("^\\d+([,.]\\d+)?$");
    private static final Pattern PADRAO_RACA = Pattern.compile("^[a-zA-Z\\s]+$");

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> listaPerguntas = new ArrayList<>();

        File nomeFormulario = new File("formulario.txt");
        File pastaPetsCadastrados = new File("petsCadastrados");

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

                if (!opcao.matches("[1-6]")) {
                    System.out.println("Opção inválida, tente novamente...");
                    continue;
                }

                int opc = Integer.parseInt(opcao);

                if (opc == 6) {
                    System.out.println("O programa foi encerrado...");
                    break;
                } else if (opc == 1) {
                    listaPerguntas.clear();
                    carregarPerguntas(nomeFormulario, listaPerguntas);

                    Pet pet = cadastrarNovoPet(input, listaPerguntas);

                    if (!pastaPetsCadastrados.exists() && !pastaPetsCadastrados.mkdirs()) {
                        throw new IllegalStateException("Erro ao criar a pasta.");
                    }

                    File petCadastrado = new File(pastaPetsCadastrados, gerarNomeArquivo(pet.getNomeCompleto()));
                    BuscarArquivo.salvarPetNoArquivo(pet, petCadastrado);
                } else if (opc == 2) {
                    alterarPetCadastrado(input);
                } else if (opc == 3) {
                    System.out.println("teste");
                } else if (opc == 4) {
                    List<PetArquivo> petsCadastrados = BuscarArquivo.listarTodosPets("petsCadastrados");

                    if (petsCadastrados.isEmpty()) {
                        System.out.println("Nenhum pet cadastrado.");
                    } else {
                        imprimirListaPets(petsCadastrados);
                    }
                } else if (opc == 5) {
                    List<PetArquivo> resultados = executarBusca(input);

                    if (resultados.isEmpty()) {
                        System.out.println("Nenhum resultado encontrado.");
                    } else {
                        imprimirListaPets(resultados);
                    }

                    System.out.println("==============================================================");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void carregarPerguntas(File nomeFormulario, List<String> listaPerguntas) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nomeFormulario))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                listaPerguntas.add(linha);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler o formulário.");
        }
    }

    private static Pet cadastrarNovoPet(Scanner input, List<String> listaPerguntas) {
        System.out.println(listaPerguntas.get(0));
        String nomeCompleto = lerNomeObrigatorio(input);

        System.out.println(listaPerguntas.get(1));
        TipoPet tipoPet = lerTipoPet(input);

        System.out.println(listaPerguntas.get(2));
        SexoDoPet sexoDoPet = lerSexoPet(input);

        System.out.println(listaPerguntas.get(3));
        Endereco enderecoPet = lerEnderecoNovo(input);

        System.out.println(listaPerguntas.get(4));
        String idade = lerIdadeNova(input);

        System.out.println(listaPerguntas.get(5));
        String peso = lerPesoNovo(input);

        System.out.println(listaPerguntas.get(6));
        String raca = lerRacaNova(input);

        return new Pet(nomeCompleto, tipoPet, sexoDoPet, enderecoPet, idade, peso, raca);
    }

    private static void alterarPetCadastrado(Scanner input) {
        while (true) {
            List<PetArquivo> resultados = executarBusca(input);

            if (resultados.isEmpty()) {
                System.out.println("Nenhum resultado encontrado.");
                return;
            }

            imprimirListaPets(resultados);
            System.out.print("Digite o número do pet que deseja alterar: ");
            String escolhaTexto = input.nextLine().trim();

            if (!escolhaTexto.matches("\\d+")) {
                System.out.println("Número inválido. A busca será exibida novamente.");
                continue;
            }

            int escolha = Integer.parseInt(escolhaTexto);
            if (escolha < 1 || escolha > resultados.size()) {
                System.out.println("Número inválido. A busca será exibida novamente.");
                continue;
            }

            PetArquivo selecionado = resultados.get(escolha - 1);
            Pet petAtualizado = lerDadosAlterados(input, selecionado.getPet());
            BuscarArquivo.salvarPetNoArquivo(petAtualizado, selecionado.getArquivo());
            System.out.println("Pet alterado com sucesso.");
            return;
        }
    }

    private static List<PetArquivo> executarBusca(Scanner input) {
        System.out.println("Digite o tipo de animal que deseja buscar: ");
        TipoPet tipoPet = lerTipoPet(input);

        System.out.println("Você deseja utilizar 1 ou 2 critérios de busca? ");
        String quantidadeTexto = input.nextLine().trim();

        if (!quantidadeTexto.matches("[12]")) {
            throw new IllegalArgumentException("Digite apenas 1 ou 2.");
        }

        int quantCriterios = Integer.parseInt(quantidadeTexto);

        System.out.println("Critérios disponíveis: nome, sexo, idade, peso, raca, endereco");
        System.out.print("Digite o primeiro critério: ");
        String criterio1 = input.nextLine().trim();

        if (!criterioValido(criterio1)) {
            throw new IllegalArgumentException("Critério inválido.");
        }

        System.out.print("Digite o valor do primeiro critério: ");
        String valor1 = input.nextLine().trim();

        if (quantCriterios == 1) {
            return BuscarArquivo.buscarPets("petsCadastrados", tipoPet, criterio1, valor1);
        }

        System.out.print("Digite o segundo critério: ");
        String criterio2 = input.nextLine().trim();
        if (!criterioValido(criterio2)) {
            throw new IllegalArgumentException("Critério inválido.");
        }

        if (criterio1.equalsIgnoreCase(criterio2)) {
            throw new IllegalArgumentException("Os critérios não podem ser iguais.");
        }

        System.out.print("Digite o valor do segundo critério: ");
        String valor2 = input.nextLine().trim();

        return BuscarArquivo.buscarPets("petsCadastrados", tipoPet, criterio1, valor1, criterio2, valor2);
    }

    private static Pet lerDadosAlterados(Scanner input, Pet petAtual) {
        System.out.println("Digite os novos dados do pet. Pressione Enter para manter o valor atual.");

        System.out.println("Nome atual: " + petAtual.getNomeCompleto());
        System.out.print("Novo nome: ");
        String nomeAtualizado = lerNomeAlterado(input, petAtual.getNomeCompleto());

        Endereco enderecoAtual = petAtual.getEndereco();
        System.out.println("Número da casa atual: " + enderecoAtual.getNumeroCasa());
        System.out.print("Novo número da casa: ");
        String numeroCasa = lerCampoMantendoAtual(input, enderecoAtual.getNumeroCasa());

        System.out.println("Cidade atual: " + enderecoAtual.getCidade());
        System.out.print("Nova cidade: ");
        String cidade = lerCampoMantendoAtual(input, enderecoAtual.getCidade());

        System.out.println("Rua atual: " + enderecoAtual.getRua());
        System.out.print("Nova rua: ");
        String rua = lerCampoMantendoAtual(input, enderecoAtual.getRua());
        Endereco enderecoAtualizado = new Endereco(numeroCasa, cidade, rua);

        System.out.println("Idade atual: " + petAtual.getIdade());
        System.out.print("Nova idade: ");
        String idadeAtualizada = lerIdadeAlterada(input, petAtual.getIdade());

        System.out.println("Peso atual: " + petAtual.getPeso());
        System.out.print("Novo peso: ");
        String pesoAtualizado = lerPesoAlterado(input, petAtual.getPeso());

        System.out.println("Raça atual: " + petAtual.getRaca());
        System.out.print("Nova raça: ");
        String racaAtualizada = lerRacaAlterada(input, petAtual.getRaca());

        return new Pet(
                nomeAtualizado,
                petAtual.getTipoPet(),
                petAtual.getSexoDoPet(),
                enderecoAtualizado,
                idadeAtualizada,
                pesoAtualizado,
                racaAtualizada
        );
    }

    private static String lerNomeObrigatorio(Scanner input) {
        String nomeCompleto = input.nextLine().trim();

        if (nomeCompleto.isBlank()) {
            throw new IllegalArgumentException("O pet deve ter um nome e sobrenome.");
        }

        if (!PADRAO_NOME.matcher(nomeCompleto).matches()) {
            throw new IllegalArgumentException("Nome inválido, tente novamente.");
        }

        return nomeCompleto;
    }

    private static String lerNomeAlterado(Scanner input, String valorAtual) {
        String nomeCompleto = input.nextLine().trim();

        if (nomeCompleto.isBlank()) {
            return valorAtual;
        }

        if (!PADRAO_NOME.matcher(nomeCompleto).matches()) {
            throw new IllegalArgumentException("Nome inválido, tente novamente.");
        }

        return nomeCompleto;
    }

    private static TipoPet lerTipoPet(Scanner input) {
        String tipoPetInformado = input.nextLine();
        try {
            return TipoPet.valueOf(tipoPetInformado.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de Pet inválido.");
        }
    }

    private static SexoDoPet lerSexoPet(Scanner input) {
        String sexoDoPetInformado = input.nextLine();
        try {
            return SexoDoPet.valueOf(sexoDoPetInformado.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sexo do Pet Inválido.");
        }
    }

    private static Endereco lerEnderecoNovo(Scanner input) {
        System.out.print("Número da casa: ");
        String numeroCasa = input.nextLine().trim();
        if (numeroCasa.isBlank()) {
            numeroCasa = NAO_INFORMADO;
        }

        System.out.print("Cidade: ");
        String cidade = input.nextLine().trim();
        if (cidade.isBlank()) {
            cidade = NAO_INFORMADO;
        }

        System.out.print("Rua: ");
        String rua = input.nextLine().trim();
        if (rua.isBlank()) {
            rua = NAO_INFORMADO;
        }

        return new Endereco(numeroCasa, cidade, rua);
    }

    private static String lerIdadeNova(Scanner input) {
        String idadeTexto = input.nextLine().trim();
        if (idadeTexto.isBlank()) {
            return NAO_INFORMADO;
        }

        return validarEFormatarIdade(idadeTexto);
    }

    private static String lerIdadeAlterada(Scanner input, String valorAtual) {
        String idadeTexto = input.nextLine().trim();
        if (idadeTexto.isBlank()) {
            return valorAtual;
        }

        return validarEFormatarIdade(idadeTexto);
    }

    private static String validarEFormatarIdade(String idadeTexto) {
        idadeTexto = idadeTexto.replace(" anos", "").replace(",", ".").trim();

        if (!PADRAO_IDADE_E_PESO.matcher(idadeTexto).matches()) {
            throw new IllegalArgumentException("Idade inválida. Digite apenas números.");
        }

        double idadeValor = Double.parseDouble(idadeTexto);
        if (idadeValor > 20) {
            throw new IllegalArgumentException("Idade inválida. O pet não pode ter mais de 20 anos.");
        }

        return idadeValor + " anos";
    }

    private static String lerPesoNovo(Scanner input) {
        String pesoTexto = input.nextLine().trim();
        if (pesoTexto.isBlank()) {
            return NAO_INFORMADO;
        }

        return validarEFormatarPeso(pesoTexto);
    }

    private static String lerPesoAlterado(Scanner input, String valorAtual) {
        String pesoTexto = input.nextLine().trim();
        if (pesoTexto.isBlank()) {
            return valorAtual;
        }

        return validarEFormatarPeso(pesoTexto);
    }

    private static String validarEFormatarPeso(String pesoTexto) {
        pesoTexto = pesoTexto.replace("kg", "").replace(",", ".").trim();

        if (!PADRAO_IDADE_E_PESO.matcher(pesoTexto).matches()) {
            throw new IllegalArgumentException("Peso inválido. Digite apenas números.");
        }

        double pesoValor = Double.parseDouble(pesoTexto);
        if (pesoValor > 60 || pesoValor < 0.5) {
            throw new IllegalArgumentException("Peso inválido. O peso deve estar entre 0.5kg e 60kg.");
        }

        return pesoValor + "kg";
    }

    private static String lerRacaNova(Scanner input) {
        String raca = input.nextLine().trim();
        if (raca.isBlank()) {
            return NAO_INFORMADO;
        }

        if (!PADRAO_RACA.matcher(raca).matches()) {
            throw new IllegalArgumentException("Raça inválida.");
        }

        return raca;
    }

    private static String lerRacaAlterada(Scanner input, String valorAtual) {
        String raca = input.nextLine().trim();
        if (raca.isBlank()) {
            return valorAtual;
        }

        if (!PADRAO_RACA.matcher(raca).matches()) {
            throw new IllegalArgumentException("Raça inválida.");
        }

        return raca;
    }

    private static String lerCampoMantendoAtual(Scanner input, String valorAtual) {
        String valor = input.nextLine().trim();
        if (valor.isBlank()) {
            return valorAtual;
        }
        return valor;
    }

    private static String gerarNomeArquivo(String nomeCompleto) {
        LocalDateTime dataAtual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String nomePetArquivo = nomeCompleto.replaceAll("\\s", "").toUpperCase();
        return dataAtual.format(formatter) + "-" + nomePetArquivo + ".TXT";
    }

    private static boolean criterioValido(String criterio) {
        String valor = criterio.toLowerCase().trim();
        return valor.equals("nome") ||
                valor.equals("sexo") ||
                valor.equals("idade") ||
                valor.equals("peso") ||
                valor.equals("raca") ||
                valor.equals("raça") ||
                valor.equals("endereco") ||
                valor.equals("endereço");
    }

    private static String formatarTextoExibicao(String texto) {
        String textoMinusculo = texto.toLowerCase();
        return Character.toUpperCase(textoMinusculo.charAt(0)) + textoMinusculo.substring(1);
    }

    private static String formatarIdade(String idade) {
        if (idade.equals(NAO_INFORMADO)) {
            return idade;
        }

        String valor = idade.replace(" anos", "").trim();
        if (valor.endsWith(".0")) {
            valor = valor.substring(0, valor.length() - 2);
        }

        return valor + " anos";
    }

    private static String formatarPeso(String peso) {
        if (peso.equals(NAO_INFORMADO)) {
            return peso;
        }

        String valor = peso.replace("kg", "").trim();
        if (valor.endsWith(".0")) {
            valor = valor.substring(0, valor.length() - 2);
        }

        return valor + "kg";
    }

    private static void imprimirListaPets(List<PetArquivo> registros) {
        for (int i = 0; i < registros.size(); i++) {
            Pet pet = registros.get(i).getPet();

            System.out.println((i + 1) + ". " +
                    pet.getNomeCompleto() + " - " +
                    formatarTextoExibicao(pet.getTipoPet().name()) + " - " +
                    formatarTextoExibicao(pet.getSexoDoPet().name()) + " - " +
                    pet.getEndereco().getRua() + ", " +
                    pet.getEndereco().getNumeroCasa() + " - " +
                    pet.getEndereco().getCidade() + " - " +
                    formatarIdade(pet.getIdade()) + " - " +
                    formatarPeso(pet.getPeso()) + " - " +
                    pet.getRaca());
        }
    }
}
