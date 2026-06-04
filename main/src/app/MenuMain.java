package app;

import domain.Endereco;
import domain.Pet;
import domain.PetArquivo;
import enums.SexoDoPet;
import enums.TipoPet;
import repository.FormularioRepository;
import repository.PetArquivoRepository;
import service.BuscaPetService;
import service.FormularioService;
import service.PetService;
import util.Constantes;
import util.Formatador;
import util.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuMain {

    private static final FormularioRepository FORMULARIO_REPOSITORY = new FormularioRepository();
    private static final PetArquivoRepository PET_ARQUIVO_REPOSITORY = new PetArquivoRepository();
    private static final BuscaPetService BUSCA_PET_SERVICE = new BuscaPetService(PET_ARQUIVO_REPOSITORY);
    private static final PetService PET_SERVICE = new PetService(PET_ARQUIVO_REPOSITORY);
    private static final FormularioService FORMULARIO_SERVICE = new FormularioService(FORMULARIO_REPOSITORY);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("==================================================================================");
        System.out.println("                                  PET SHOP");

        while (true) {
            try {
                System.out.println("==================================================================================");
                System.out.print("1. Iniciar o sistema para cadastro de PETS\n" +
                        "2. Iniciar o sistema para alterar formulário\n" +
                        "3. Sair\n" +
                        "Selecione a sua opção: ");
                String opcao = input.nextLine().trim();

                if ("1".equals(opcao)) {
                    iniciarSistemaPets(input);
                } else if ("2".equals(opcao)) {
                    iniciarSistemaFormulario(input);
                } else if ("3".equals(opcao)) {
                    System.out.println("O programa foi encerrado...");
                    return;
                } else {
                    System.out.println("Opção inválida, tente novamente...");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void iniciarSistemaPets(Scanner input) {
        while (true) {
            try {
                System.out.println("==================================================================================");
                System.out.print("1. Cadastrar um novo pet\n" +
                        "2. Alterar os dados do pet cadastrado\n" +
                        "3. Deletar um pet cadastrado\n" +
                        "4. Listar todos os pets cadastrados\n" +
                        "5. Listar pets por algum critério (idade, nome, raça)\n" +
                        "6. Voltar\n" +
                        "Selecione a sua opção: ");
                String opcao = input.nextLine().trim();

                if (!opcao.matches("[1-6]")) {
                    System.out.println("Opção inválida, tente novamente...");
                    continue;
                }

                int opc = Integer.parseInt(opcao);

                if (opc == 6) {
                    return;
                } else if (opc == 1) {
                    List<String> listaPerguntas = FORMULARIO_SERVICE.carregarPerguntas();
                    Pet pet = cadastrarNovoPet(input, listaPerguntas);
                    PET_SERVICE.salvarNovoPet(pet);
                } else if (opc == 2) {
                    alterarPetCadastrado(input);
                } else if (opc == 3) {
                    deletarPetCadastrado(input);
                } else if (opc == 4) {
                    listarTodosPets();
                } else if (opc == 5) {
                    buscarPets(input);
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void iniciarSistemaFormulario(Scanner input) {
        while (true) {
            try {
                System.out.println("==================================================================================");
                System.out.print("1. Criar nova pergunta\n" +
                        "2. Alterar pergunta existente\n" +
                        "3. Excluir pergunta existente\n" +
                        "4. Voltar para o menu inicial\n" +
                        "5. Sair\n" +
                        "Selecione a sua opção: ");
                String opcao = input.nextLine().trim();

                if (!opcao.matches("[1-5]")) {
                    System.out.println("Opção inválida, tente novamente...");
                    continue;
                }

                int opc = Integer.parseInt(opcao);

                if (opc == 1) {
                    System.out.print("Digite a nova pergunta: ");
                    String novaPergunta = input.nextLine();
                    FORMULARIO_SERVICE.criarPergunta(novaPergunta);
                    System.out.println("Pergunta criada com sucesso.");
                } else if (opc == 2) {
                    exibirPerguntasFormulario();
                    System.out.print("Digite o número da pergunta que deseja alterar: ");
                    int numeroPergunta = lerNumeroPositivo(input);
                    System.out.print("Digite a nova pergunta: ");
                    String novaPergunta = input.nextLine();
                    FORMULARIO_SERVICE.alterarPergunta(numeroPergunta, novaPergunta);
                    System.out.println("Pergunta alterada com sucesso.");
                } else if (opc == 3) {
                    exibirPerguntasFormulario();
                    System.out.print("Digite o número da pergunta que deseja excluir: ");
                    int numeroPergunta = lerNumeroPositivo(input);

                    while (true) {
                        System.out.print("Confirma a exclusão da pergunta? Digite SIM ou NÃO: ");
                        String confirmacao = input.nextLine().trim();

                        if (confirmacao.equalsIgnoreCase("SIM")) {
                            FORMULARIO_SERVICE.excluirPergunta(numeroPergunta);
                            System.out.println("Pergunta excluída com sucesso.");
                            break;
                        }

                        if (confirmacao.equalsIgnoreCase("NÃO") || confirmacao.equalsIgnoreCase("NAO")) {
                            System.out.println("Exclusão cancelada.");
                            break;
                        }

                        System.out.println("Resposta inválida. Digite SIM ou NÃO.");
                    }
                } else if (opc == 4) {
                    return;
                } else if (opc == 5) {
                    System.out.println("O programa foi encerrado...");
                    System.exit(0);
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void exibirPerguntasFormulario() {
        List<String> perguntas = FORMULARIO_SERVICE.carregarPerguntas();
        for (String pergunta : perguntas) {
            System.out.println(pergunta);
        }
    }

    private static int lerNumeroPositivo(Scanner input) {
        String texto = input.nextLine().trim();
        if (!texto.matches("\\d+") || Integer.parseInt(texto) <= 0) {
            throw new IllegalArgumentException("Digite um número válido maior que zero.");
        }
        return Integer.parseInt(texto);
    }

    private static void listarTodosPets() {
        List<PetArquivo> petsCadastrados = BUSCA_PET_SERVICE.listarTodosPets();

        if (petsCadastrados.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
        } else {
            imprimirListaPets(petsCadastrados);
        }
    }

    private static void buscarPets(Scanner input) {
        List<PetArquivo> resultados = executarBusca(input);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado.");
        } else {
            imprimirListaPets(resultados);
        }

        System.out.println("==============================================================");
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

        List<String> respostasExtras = lerRespostasExtras(input, listaPerguntas);

        return new Pet(nomeCompleto, tipoPet, sexoDoPet, enderecoPet, idade, peso, raca, respostasExtras);
    }

    private static List<String> lerRespostasExtras(Scanner input, List<String> listaPerguntas) {
        List<String> respostasExtras = new ArrayList<>();

        for (int i = 7; i < listaPerguntas.size(); i++) {
            String perguntaCompleta = listaPerguntas.get(i);
            System.out.println(perguntaCompleta);
            String resposta = input.nextLine().trim();
            if (resposta.isBlank()) {
                resposta = Constantes.NAO_INFORMADO;
            }
            respostasExtras.add(extrairTextoPergunta(perguntaCompleta) + " - " + resposta);
        }

        return respostasExtras;
    }

    private static String extrairTextoPergunta(String perguntaCompleta) {
        int posicaoSeparador = perguntaCompleta.indexOf(" - ");
        if (posicaoSeparador < 0) {
            return perguntaCompleta.trim();
        }
        return perguntaCompleta.substring(posicaoSeparador + 3).trim();
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

            try {
                PetArquivo selecionado = PET_SERVICE.selecionarPet(resultados, escolhaTexto);
                Pet petAtualizado = lerDadosAlterados(input, selecionado.getPet());
                PET_SERVICE.alterarPet(selecionado, petAtualizado);
                System.out.println("Pet alterado com sucesso.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void deletarPetCadastrado(Scanner input) {
        while (true) {
            List<PetArquivo> resultados = executarBusca(input);

            if (resultados.isEmpty()) {
                System.out.println("Nenhum resultado encontrado.");
                return;
            }

            imprimirListaPets(resultados);
            System.out.print("Digite o número do pet que deseja deletar: ");
            String escolhaTexto = input.nextLine().trim();

            PetArquivo selecionado;
            try {
                selecionado = PET_SERVICE.selecionarPet(resultados, escolhaTexto);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            while (true) {
                System.out.print("Confirma a exclusão do pet? Digite SIM ou NÃO: ");
                String confirmacao = input.nextLine().trim();

                try {
                    boolean petDeletado = PET_SERVICE.deletarPet(selecionado, confirmacao);
                    if (petDeletado) {
                        System.out.println("Pet deletado com sucesso.");
                    } else {
                        System.out.println("Exclusão cancelada.");
                    }
                    return;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static List<PetArquivo> executarBusca(Scanner input) {
        System.out.println("Digite o tipo de animal que deseja buscar: ");
        TipoPet tipoPet = lerTipoPet(input);

        System.out.println("Você deseja utilizar 1 ou 2 critérios de busca? ");
        String quantidadeTexto = input.nextLine().trim();

        System.out.println("Critérios disponíveis: nome, sexo, idade, peso, raca, endereco");
        System.out.print("Digite o primeiro critério: ");
        String criterio1 = input.nextLine().trim();

        System.out.print("Digite o valor do primeiro critério: ");
        String valor1 = input.nextLine().trim();

        if ("2".equals(quantidadeTexto)) {
            System.out.print("Digite o segundo critério: ");
            String criterio2 = input.nextLine().trim();

            System.out.print("Digite o valor do segundo critério: ");
            String valor2 = input.nextLine().trim();

            return BUSCA_PET_SERVICE.buscarPets(tipoPet, quantidadeTexto, criterio1, valor1, criterio2, valor2);
        }

        return BUSCA_PET_SERVICE.buscarPets(tipoPet, quantidadeTexto, criterio1, valor1, null, null);
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
                racaAtualizada,
                petAtual.getRespostasExtras()
        );
    }

    private static String lerNomeObrigatorio(Scanner input) {
        return Validador.validarNomeObrigatorio(input.nextLine());
    }

    private static String lerNomeAlterado(Scanner input, String valorAtual) {
        return Validador.validarNomeAlterado(input.nextLine(), valorAtual);
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
            numeroCasa = Constantes.NAO_INFORMADO;
        }

        System.out.print("Cidade: ");
        String cidade = input.nextLine().trim();
        if (cidade.isBlank()) {
            cidade = Constantes.NAO_INFORMADO;
        }

        System.out.print("Rua: ");
        String rua = input.nextLine().trim();
        if (rua.isBlank()) {
            rua = Constantes.NAO_INFORMADO;
        }

        return new Endereco(numeroCasa, cidade, rua);
    }

    private static String lerIdadeNova(Scanner input) {
        String idadeTexto = input.nextLine().trim();
        if (idadeTexto.isBlank()) {
            return Constantes.NAO_INFORMADO;
        }

        return Formatador.formatarIdadePersistencia(lerValorIdadeEmAnos(input, idadeTexto));
    }

    private static String lerIdadeAlterada(Scanner input, String valorAtual) {
        String idadeTexto = input.nextLine().trim();
        if (idadeTexto.isBlank()) {
            return valorAtual;
        }

        return Formatador.formatarIdadePersistencia(lerValorIdadeEmAnos(input, idadeTexto));
    }

    private static String lerPesoNovo(Scanner input) {
        String pesoTexto = input.nextLine().trim();
        if (pesoTexto.isBlank()) {
            return Constantes.NAO_INFORMADO;
        }

        return Formatador.formatarPesoPersistencia(Validador.validarValorPeso(pesoTexto));
    }

    private static String lerPesoAlterado(Scanner input, String valorAtual) {
        String pesoTexto = input.nextLine().trim();
        if (pesoTexto.isBlank()) {
            return valorAtual;
        }

        return Formatador.formatarPesoPersistencia(Validador.validarValorPeso(pesoTexto));
    }

    private static String lerRacaNova(Scanner input) {
        return Validador.validarRacaNova(input.nextLine());
    }

    private static String lerRacaAlterada(Scanner input, String valorAtual) {
        return Validador.validarRacaAlterada(input.nextLine(), valorAtual);
    }

    private static String lerCampoMantendoAtual(Scanner input, String valorAtual) {
        String valor = input.nextLine().trim();
        if (valor.isBlank()) {
            return valorAtual;
        }
        return valor;
    }

    private static double lerValorIdadeEmAnos(Scanner input, String idadeTexto) {
        while (true) {
            System.out.print("Digite a unidade da idade (1 para anos, 2 para meses): ");
            String unidade = input.nextLine().trim();

            if ("1".equals(unidade)) {
                return Validador.validarValorIdadeEmAnos(idadeTexto);
            }

            if ("2".equals(unidade)) {
                return Validador.validarValorIdadeEmMeses(idadeTexto);
            }

            System.out.println("Unidade inválida. Digite 1 para anos ou 2 para meses.");
        }
    }

    private static void imprimirListaPets(List<PetArquivo> registros) {
        for (int i = 0; i < registros.size(); i++) {
            Pet pet = registros.get(i).getPet();

            System.out.println((i + 1) + ". " +
                    pet.getNomeCompleto() + " - " +
                    Formatador.formatarTextoExibicao(pet.getTipoPet().name()) + " - " +
                    Formatador.formatarTextoExibicao(pet.getSexoDoPet().name()) + " - " +
                    pet.getEndereco().getRua() + ", " +
                    pet.getEndereco().getNumeroCasa() + " - " +
                    pet.getEndereco().getCidade() + " - " +
                    Formatador.formatarIdadeExibicao(pet.getIdade()) + " - " +
                    Formatador.formatarPesoExibicao(pet.getPeso()) + " - " +
                    pet.getRaca());
        }
    }
}
