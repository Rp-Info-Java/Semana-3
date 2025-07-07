package application.service;

import domain.model.entity.Produtos;
import domain.repositories.produtos.ProdutosDAO;
import domain.repositories.produtos.ProdutosDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutosService extends ServiceBase {
    private ProdutosDAO dao;

    public ProdutosService(Connection connection) {
        super(connection);
        this.dao = new ProdutosDAOImpl(connection);
    }

    public void adicionarProdutos(Produtos produto) throws SQLException {
        this.dao.insert(produto);
        System.out.println("Produto adicionado com sucesso!");
    }

    public void atualizarProdutos(Produtos produto, int opcao) throws SQLException {
        if (opcao == 0) {
            System.out.println("Cancelando atualização!\n");
        } else if (opcao < 0 || opcao > 3) {
            System.out.println("A opção digitada é inválida. Digite uma opção válida!");
        } else if (opcao == 1) {
            if (this.dao.update(produto, opcao)) {
                System.out.println("Produto atualizado com sucesso!\n");
            } else {
                System.out.println("Produto não encontrado!\n");
            }
        } else if (opcao == 2) {
            if (this.dao.update(produto, opcao)) {
                System.out.println("Produto atualizado com sucesso!\n");
            } else {
                System.out.println("Produto não encontrado!\n");
            }
        } else if (opcao == 3) {
            if (this.dao.update(produto, opcao)) {
                System.out.println("Produto atualizado com sucesso!\n");
            } else {
                System.out.println("Produto não encontrado!\n");
            }
        }
    }

    public void deletarProdutos(int opcao, int id) throws SQLException {
        Produtos produ = this.dao.getProduto(id);

        if (opcao == 0) {
            System.out.println("Cancelando operação!\n");
        } else if (opcao < 0 || opcao > 2) {
            System.out.println("A opção digitada é inválida. Digite uma opção válida!");
        } else if (opcao == 1) {
            if (produ != null) {
                if (produ.getProd_status().equals("N")) {
                    System.out.println("O produto informado já foi desativado.");
                } else {
                    produ.setProd_status("N");
                    if (this.dao.delete(produ, opcao)) {
                        System.out.println("Produto desativado com sucesso!");
                    }
                }
            } else {
                System.out.println("Nenhum produto encontrado com esse ID.");
            }
        } else if (opcao == 2) {
            if (produ != null) {
                if (produ.getProd_status().equals("N")) {
                    if (this.dao.delete(produ, opcao)) {
                        System.out.println("Produto deletado com sucesso!");
                    }
                } else {
                    System.out.println("O produto fornecido deve ser desativado primeiro!");
                }
            } else {
                System.out.println("Nenhum produto encontrado com esse ID.");
            }
        }
    }

    public void exibeProdutos(List<Produtos> prods) {
        StringBuilder sb = new StringBuilder();

        System.out.println("\n--- Listagem de produtos ---\n");

        sb.append("┌").append("─".repeat(7)).append("┬").append("─".repeat(27))
                .append("┬").append("─".repeat(14)).append("┬").append("─".repeat(20))
                .append("┬").append("─".repeat(10))
                .append("┬").append("─".repeat(12)).append("┐\n");

        sb.append(String.format("│ %-5s │ %-25s │ %-12s │ %-18s │ %-8s │ %-10s │%n",
                "ID", "NOME", "PREÇO", "CATEGORIA", "ESTOQUE", "STATUS"));

        sb.append("├").append("─".repeat(7)).append("┼").append("─".repeat(27))
                .append("┼").append("─".repeat(14)).append("┼").append("─".repeat(20))
                .append("┼").append("─".repeat(10))
                .append("┼").append("─".repeat(12)).append("┤\n");

        System.out.print(sb.toString());

        for (Produtos prod : prods) {
            System.out.printf("│ %-5d │ %-25s │ R$ %9.2f │ %-18s │ %8d │ %10s │%n",
                    prod.getProd_id(),
                    prod.getProd_nome(),
                    prod.getProd_preco(),
                    prod.getProd_categoria(),
                    prod.getProd_estoque(),
                    prod.getProd_status());
        }

        System.out.println("└" + "─".repeat(7) + "┴" + "─".repeat(27) +
                "┴" + "─".repeat(14) + "┴" + "─".repeat(20) +
                "┴" + "─".repeat(10) +
                "┴" + "─".repeat(12) + "┘");
    }

    public void getAllProdutos() throws SQLException {
        List<Produtos> produtos = this.dao.getAllProd();

        if (!produtos.isEmpty()) {
            exibeProdutos(produtos);
        }
    }

    public void getProdutoNamePrice() throws SQLException {
        List<Produtos> produtos = this.dao.getProdNamePrice();

        if (!produtos.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            System.out.println("\n--- Listagem de produtos ---\n");
            sb.append("┌").append("─".repeat(27)).append("┬").append("─".repeat(15)).append("┐\n");
            sb.append(String.format("│ %-25s │ %-12s │%n", "NOME DO PRODUTO", "PREÇO"));
            sb.append("├").append("─".repeat(27)).append("┼").append("─".repeat(15)).append("┤\n");

            System.out.print(sb.toString());

            for (Produtos prod : produtos) {
                System.out.printf("│ %-25s │ R$ %10.2f │%n",
                        prod.getProd_nome(),
                        prod.getProd_preco());
            }

            System.out.println("└" + "─".repeat(27) + "┴" + "─".repeat(15) + "┘");
        }
    }

    public void getProdutoLimp() throws SQLException {
        List<Produtos> produtos = this.dao.getProdLimp();

        if (!produtos.isEmpty()) {
            exibeProdutos(produtos);
        }
    }

    public void getProdutoEstoque() throws SQLException {
        List<Produtos> produtos = this.dao.getProdEstoque();

        if (!produtos.isEmpty()) {
            exibeProdutos(produtos);
        }
    }

    public void getProdutoPriceRange() throws SQLException {
        List<Produtos> produtos = this.dao.getProdPriceRange();

        if (!produtos.isEmpty()) {
            exibeProdutos(produtos);
        }
    }

    public void getProdutoGamer() throws SQLException {
        List<Produtos> produtos = this.dao.getProdGamer();

        if (!produtos.isEmpty()) {
            exibeProdutos(produtos);
        }
    }

    public void getProdutoBarato() throws SQLException {
        List<Produtos> produtos = this.dao.getProdBarato();

        if (!produtos.isEmpty()) {
            exibeProdutos(produtos);
        }
    }

    public void getProdutoArmazem() throws SQLException {
        Integer arm = this.dao.getProdArmazem();

        System.out.println("Quantidade de produtos na categoria Armazenamento: " + arm);
    }

    public void getProdutoMedia() throws SQLException {
        List<Produtos> produtos = this.dao.getProdMedia();

        if (!produtos.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            System.out.println("\n--- Listagem de produtos ---\n");
            sb.append("┌").append("─".repeat(22)).append("┬").append("─".repeat(18)).append("┐\n");
            sb.append(String.format("│ %-20s │ %-16s │%n", "CATEGORIA", "MÉDIA PREÇO"));
            sb.append("├").append("─".repeat(22)).append("┼").append("─".repeat(18)).append("┤\n");

            System.out.print(sb.toString());
            for (Produtos prod : produtos) {
                System.out.printf("│ %-20s │ R$ %12.2f │%n",
                        prod.getProd_categoria(),
                        prod.getProd_preco());
            }
            System.out.println("└" + "─".repeat(22) + "┴" + "─".repeat(18) + "┘");
        }
    }
}
