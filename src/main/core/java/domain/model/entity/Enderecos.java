package domain.model.entity;

public class Enderecos {
    private int ende_id;
    private String ende_cep;
    private String ende_rua;
    private int ende_numero;
    private String ende_complemento;
    private String ende_bairro;
    private int ende_cida_id;
    private String ende_status;

    public Enderecos() {
        super();
    }

    public int getEnde_id() {
        return ende_id;
    }

    public String getEnde_cep() {
        return ende_cep;
    }

    public String getEnde_rua() {
        return ende_rua;
    }

    public int getEnde_numero() {
        return ende_numero;
    }

    public String getEnde_complemento() {
        return ende_complemento;
    }

    public String getEnde_bairro() {
        return ende_bairro;
    }

    public int getEnde_cida_id() {
        return ende_cida_id;
    }

    public String getEnde_status() {
        return ende_status;
    }

    public void setEnde_id(int ende_id) {
        this.ende_id = ende_id;
    }

    public void setEnde_cep(String ende_cep) {
        this.ende_cep = ende_cep;
    }

    public void setEnde_rua(String ende_rua) {
        this.ende_rua = ende_rua;
    }

    public void setEnde_numero(int ende_numero) {
        this.ende_numero = ende_numero;
    }

    public void setEnde_complemento(String ende_complemento) {
        this.ende_complemento = ende_complemento;
    }

    public void setEnde_bairro(String ende_bairro) {
        this.ende_bairro = ende_bairro;
    }

    public void setEnde_cida_id(int ende_cida_id) {
        this.ende_cida_id = ende_cida_id;
    }

    public void setEnde_status(String ende_status) {
        this.ende_status = ende_status;
    }
}
