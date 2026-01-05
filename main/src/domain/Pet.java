package domain;

public class Pet {
    private String nomeCompleto;
    private TipoPet tipoPet;
    private SexoDoPet sexoDoPet;
    private Endereco endereco;
    private double idade;
    private double peso;
    private String raca;

    public Pet(String nomeCompleto, TipoPet tipoPet, SexoDoPet sexoDoPet, Endereco endereco, double idade, double peso, String raca) {
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public double getIdade() {
        return idade;
    }

    public void setIdade(double idade) {
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
