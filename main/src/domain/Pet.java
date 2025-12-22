package domain;

import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String nomeCompleto;
    private TipoPet tipoPet;
    private SexoDoPet sexoDoPet;
    private List<String> endereco = new ArrayList<>();
    private int idade;
    private double peso;
    private String raca;

    public Pet(String nomeCompleto, TipoPet tipoPet, SexoDoPet sexoDoPet, List<String> endereco, int idade, double peso, String raca) {
        this.nomeCompleto = nomeCompleto;
        this.tipoPet = tipoPet;
        this.sexoDoPet = sexoDoPet;
        this.endereco = endereco;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public TipoPet getTipoPet() {
        return tipoPet;
    }

    public void setTipoPet(TipoPet tipoPet) {
        this.tipoPet = tipoPet;
    }

    public SexoDoPet getSexoDoPet() {
        return sexoDoPet;
    }

    public void setSexoDoPet(SexoDoPet sexoDoPet) {
        this.sexoDoPet = sexoDoPet;
    }

    public List<String> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<String> endereco) {
        this.endereco = endereco;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
