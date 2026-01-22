package domain;

import java.io.File;

public class buscarArquivo {

    public static void buscarArquivoNaPasta(String caminhoPastaPet, String criterio) {
        File pastaPet = new File(caminhoPastaPet);

        if (pastaPet.exists() && pastaPet.isDirectory()) {
            File[] listaArquivosPet = pastaPet.listFiles();
            if (listaArquivosPet != null) {
                for (File arquivoPet : listaArquivosPet) {
                    if (arquivoPet.isFile() && arquivoPet.getName().endsWith(".txt")) {
                        buscarCriterio.buscarCriterioNoArquivo(arquivoPet, criterio);
                    }
                }
            }
        }
    }
}
