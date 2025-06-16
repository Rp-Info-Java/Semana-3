package update;

import insert.ConsultaProdutos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateProdutos {
    //metodo para att categoria
    //att estoque
    //att preço
    Scanner teclado = new Scanner(System.in);
    String palavra = "";
    ConsultaProdutos consulta = new ConsultaProdutos();
    private final String queryListagem = "SELECT * FROM produtos ORDER BY prod_id";
    int opcao = 0;

    public void atualizacao(Connection conn) throws SQLException {
        do {
            System.out.println("\n--- Atualização de características de produto(s) ---");
            System.out.println("""
                    Escolha uma das opções de atualização:
                    1- Atualizar categoria do produto.
                    2- Atualizar estoque do produto.
                    3- Atualizar preço do produto.
                    0- Cancelar atualização.
                    """);
            System.out.println("Digite a opção de atualização desejada (1-3 ou 0 para sair): ");
            opcao = teclado.nextInt();

            if(opcao == 0){
                System.out.println("Cancelando atualização!\n");
            }else if(opcao == 1){
                atualizarCategoria(conn);
            }else if(opcao == 2){
                atualizarEstoque(conn);
            }else if(opcao == 3){
                atualizarPreco(conn);
            }else{
                System.out.println("A opção digitada é inválida. Digite uma opção válida!");
            }
        }while (opcao != 0);
    }

    public void atualizarCategoria(Connection conn) throws SQLException {
        String sql = "UPDATE produtos SET prod_categoria = ? WHERE prod_nome = ? AND prod_id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        System.out.println("\n--- Listagem de produtos ---\n");
        ConsultaProdutos.listAll(conn, queryListagem);

        teclado.nextLine(); //limpando buffer
        System.out.println("-Atualização de categoria por NOME e ID do produto-");
        System.out.println("Escreva o nome da NOVA categoria para o produto a ser alterado: ");
        palavra = teclado.nextLine();
        stmt.setString(1, palavra);

        System.out.println("Escreva o nome do produto: ");
        palavra = teclado.nextLine();
        stmt.setString(2, palavra);

        System.out.println("Escreva o id do produto: ");
        palavra = teclado.nextLine();
        stmt.setInt(3, Integer.parseInt(palavra));

        int linhasAfetadas = stmt.executeUpdate();

        if(linhasAfetadas > 0){
            System.out.println("Categoria atualizada com sucesso!\n");
        }else{
            System.out.println("Produto não encontrado!\n");
        }
    }

    public void atualizarEstoque(Connection conn) throws SQLException {
        String sql = "UPDATE produtos SET prod_estoque = ? WHERE prod_nome = ? AND prod_id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        teclado.nextLine();
        System.out.println("\n--- Listagem de produtos ---\n");
        ConsultaProdutos.listAll(conn, queryListagem);

        System.out.println("-Atualização de estoque por NOME e ID do produto-");
        System.out.println("Escreva a quantia do NOVO estoque para o produto a ser alterado: ");
        palavra = teclado.nextLine();
        stmt.setInt(1, Integer.parseInt(palavra));

        System.out.println("Escreva o nome do produto: ");
        palavra = teclado.nextLine();
        stmt.setString(2, palavra);

        System.out.println("Escreva o id do produto: ");
        palavra = teclado.nextLine();
        stmt.setInt(3, Integer.parseInt(palavra));

        int linhasAfetadas = stmt.executeUpdate();

        if(linhasAfetadas > 0){
            System.out.println("Estoque atualizado com sucesso!\n");
        }else{
            System.out.println("Produto não encontrado!\n");
        }
    }

    public void atualizarPreco(Connection conn) throws SQLException {
        String sql = "UPDATE produtos SET prod_preco = ? WHERE prod_nome = ? AND prod_id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        teclado.nextLine();
        System.out.println("\n--- Listagem de produtos ---\n");
        ConsultaProdutos.listAll(conn, queryListagem);

        System.out.println("-Atualização de preço por NOME e ID do produto-");
        System.out.println("Escreva o NOVO preço para o produto a ser alterado (com ponto ao invés de vírgula): ");
        palavra = teclado.nextLine();
        stmt.setDouble(1, Double.parseDouble(palavra));

        System.out.println("Escreva o nome do produto: ");
        palavra = teclado.nextLine();
        stmt.setString(2, palavra);

        System.out.println("Escreva o id do produto: ");
        palavra = teclado.nextLine();
        stmt.setInt(3, Integer.parseInt(palavra));

        int linhasAfetadas = stmt.executeUpdate();

        if(linhasAfetadas > 0){
            System.out.println("Preço atualizado com sucesso!\n");
        }else{
            System.out.println("Produto não encontrado!\n");
        }
    }
}
