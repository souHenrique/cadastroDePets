package domain;

import java.text.Normalizer;

public class BuscarCriterio {

    public static boolean correspondeAoCriterio(Pet pet, String criterio, String valorBuscado) {
        String criterioNormalizado = normalizar(criterio);
        String valorNormalizado = normalizar(valorBuscado);

        switch (criterioNormalizado) {
            case "nome":
                return normalizar(pet.getNomeCompleto()).contains(valorNormalizado);

            case "sexo":
                return normalizar(pet.getSexoDoPet().name()).contains(valorNormalizado);

            case "idade":
                return normalizar(pet.getIdade()).contains(valorNormalizado);

            case "peso":
                return normalizar(pet.getPeso()).contains(valorNormalizado);

            case "raca":
            case "raça":
                return normalizar(pet.getRaca()).contains(valorNormalizado);

            case "endereco":
            case "endereço":
                String enderecoCompleto = pet.getEndereco().getRua() + " " +
                        pet.getEndereco().getNumeroCasa() + " " +
                        pet.getEndereco().getCidade();
                return normalizar(enderecoCompleto).contains(valorNormalizado);

            default:
                throw new IllegalArgumentException("Critério inválido.");
        }
    }

    private static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return textoNormalizado.replaceAll("\\p{M}", "").toLowerCase().trim();
    }
}
