package test;

import java.io.*;

public class MenuMain {
    public static void main(String[] args) {
        File nomeFormulario = new File("formulario.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nomeFormulario))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
