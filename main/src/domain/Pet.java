package domain;

public class Pet {
    private String nomeCompleto;
    private TipoPet tipoPet;
    private SexoDoPet sexoDoPet;
    private Endereco endereco;
    private String idade;
    private String peso;
    private String raca;

    public Pet(String nomeCompleto, TipoPet tipoPet, SexoDoPet sexoDoPet, Endereco endereco, String idade, String peso, String raca) {
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

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
