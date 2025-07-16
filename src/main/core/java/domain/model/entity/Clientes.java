package domain.model.entity;


import java.sql.Date;
import java.sql.Timestamp;

public class Clientes {
    private int clie_id;
    private String clie_nome;
    private String clie_email;
    private String clie_cpf;
    private String clie_telefone;
    private Date clie_data_cadastro;
    private int clie_ende_id;
    private String clie_status;

    public Clientes(){
        super();
    }

    public int getClie_id() {
        return clie_id;
    }

    public void setClie_id(int clie_id) {
        this.clie_id = clie_id;
    }

    public String getClie_nome() {
        return clie_nome;
    }

    public void setClie_nome(String clie_nome) {
        this.clie_nome = clie_nome;
    }

    public String getClie_email() {
        return clie_email;
    }

    public void setClie_email(String clie_email) {
        this.clie_email = clie_email;
    }

    public String getClie_cpf() {
        return clie_cpf;
    }

    public void setClie_cpf(String clie_cpf) {
        this.clie_cpf = clie_cpf;
    }

    public String getClie_telefone() {
        return clie_telefone;
    }

    public void setClie_telefone(String clie_telefone) {
        this.clie_telefone = clie_telefone;
    }

    public Date getClie_data_cadastro() {
        return clie_data_cadastro;
    }

    public void setClie_data_cadastro(Date clie_data_cadastro) {
        this.clie_data_cadastro = clie_data_cadastro;
    }

    public int getClie_ende_id() {
        return clie_ende_id;
    }

    public void setClie_ende_id(int clie_ende_id) {
        this.clie_ende_id = clie_ende_id;
    }

    public String getClie_status() {
        return clie_status;
    }

    public void setClie_status(String clie_status) {
        this.clie_status = clie_status;
    }
}
