package domain.model.entity;

public class Cidades {
    private int cida_id;
    private String cida_nome;
    private String cida_uf;
    private String cida_status;

    public Cidades(){
        super();
    }

    public int getCida_id() {
        return cida_id;
    }

    public String getCida_nome() {
        return cida_nome;
    }

    public String getCida_uf() {
        return cida_uf;
    }

    public String getCida_status() {
        return cida_status;
    }

    public void setCida_id(int cida_id) {
        this.cida_id = cida_id;
    }

    public void setCida_nome(String cida_nome) {
        this.cida_nome = cida_nome;
    }

    public void setCida_uf(String cida_uf) {
        this.cida_uf = cida_uf;
    }

    public void setCida_status(String cida_status) {
        this.cida_status = cida_status;
    }
}
