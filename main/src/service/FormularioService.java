package service;

import repository.FormularioRepository;

import java.util.ArrayList;
import java.util.List;

public class FormularioService {

    private static final int QUANTIDADE_PERGUNTAS_ORIGINAIS = 7;

    private final FormularioRepository formularioRepository;

    public FormularioService(FormularioRepository formularioRepository) {
        this.formularioRepository = formularioRepository;
    }

    public List<String> carregarPerguntas() {
        return formularioRepository.carregarPerguntas();
    }

    public List<String> criarPergunta(String novaPergunta) {
        String texto = normalizarTextoPergunta(novaPergunta);
        List<String> perguntas = extrairTextosPerguntas();
        perguntas.add(texto);
        return salvarReorganizando(perguntas);
    }

    public List<String> alterarPergunta(int numeroPergunta, String novaPergunta) {
        validarPerguntaExtra(numeroPergunta);
        String texto = normalizarTextoPergunta(novaPergunta);

        List<String> perguntas = extrairTextosPerguntas();
        if (numeroPergunta > perguntas.size()) {
            throw new IllegalArgumentException("Pergunta não encontrada.");
        }

        perguntas.set(numeroPergunta - 1, texto);
        return salvarReorganizando(perguntas);
    }

    public List<String> excluirPergunta(int numeroPergunta) {
        validarPerguntaExtra(numeroPergunta);

        List<String> perguntas = extrairTextosPerguntas();
        if (numeroPergunta > perguntas.size()) {
            throw new IllegalArgumentException("Pergunta não encontrada.");
        }

        perguntas.remove(numeroPergunta - 1);
        return salvarReorganizando(perguntas);
    }

    private List<String> extrairTextosPerguntas() {
        List<String> linhas = carregarPerguntas();
        List<String> perguntas = new ArrayList<>();

        for (String linha : linhas) {
            perguntas.add(removerPrefixoNumeracao(linha));
        }

        return perguntas;
    }

    private List<String> salvarReorganizando(List<String> perguntasSemNumero) {
        List<String> perguntasNumeradas = new ArrayList<>();

        for (int i = 0; i < perguntasSemNumero.size(); i++) {
            perguntasNumeradas.add((i + 1) + " - " + perguntasSemNumero.get(i));
        }

        formularioRepository.salvarPerguntas(perguntasNumeradas);
        return perguntasNumeradas;
    }

    private void validarPerguntaExtra(int numeroPergunta) {
        if (numeroPergunta <= QUANTIDADE_PERGUNTAS_ORIGINAIS) {
            throw new IllegalArgumentException("As perguntas originais de 1 a 7 não podem ser alteradas ou excluídas.");
        }
    }

    private String normalizarTextoPergunta(String pergunta) {
        String texto = pergunta.trim();
        if (texto.isBlank()) {
            throw new IllegalArgumentException("A pergunta não pode ficar vazia.");
        }
        return texto;
    }

    private String removerPrefixoNumeracao(String linha) {
        String texto = linha.trim();

        while (true) {
            int posicaoSeparador = texto.indexOf(" - ");
            if (posicaoSeparador < 0) {
                return texto.trim();
            }

            String prefixo = texto.substring(0, posicaoSeparador).trim();
            if (!prefixo.matches("\\d+")) {
                return texto.trim();
            }

            texto = texto.substring(posicaoSeparador + 3).trim();
        }
    }
}
