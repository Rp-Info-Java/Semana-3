package domain.model.entity;

public class Produtos {
    private int prod_id;
    private String prod_nome;
    private double prod_preco;
    private String prod_categoria;
    private int prod_estoque;
    private String prod_status;

    public Produtos() {
        super();
    }

    public Produtos(int prod_id, String prod_nome, String prod_status){
        this.prod_id = prod_id;
        this.prod_nome = prod_nome;
        this.prod_status = prod_status;
    }

    public Produtos(int prod_id, String prod_nome, double prod_preco, String prod_categoria, int prod_estoque, String prod_status) {
        this.prod_id = prod_id;
        this.prod_nome = prod_nome;
        this.prod_preco = prod_preco;
        this.prod_categoria = prod_categoria;
        this.prod_estoque = prod_estoque;
        this.prod_status = prod_status;
    }

    public String getProd_nome() {
        return prod_nome;
    }

    public int getProd_id() {
        return prod_id;
    }

    public String getProd_categoria() {
        return prod_categoria;
    }

    public double getProd_preco() {
        return prod_preco;
    }

    public int getProd_estoque() {
        return prod_estoque;
    }

    public String getProd_status() {
        return prod_status;
    }

    public void setProd_nome(String prod_nome) {
        this.prod_nome = prod_nome;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public void setProd_preco(double prod_preco) {
        this.prod_preco = prod_preco;
    }

    public void setProd_categoria(String prod_categoria) {
        this.prod_categoria = prod_categoria;
    }

    public void setProd_estoque(int prod_estoque) {
        this.prod_estoque = prod_estoque;
    }

    public void setProd_status(String prod_status) {
        this.prod_status = prod_status;
    }
}
