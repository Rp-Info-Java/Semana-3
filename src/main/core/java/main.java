import insert.AddProdutos;
import insert.ConsultaProdutos;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/loja";
        String user = "postgres";
        String password = "adm";
        Scanner teclado = new Scanner(System.in);
        int opcao;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexão estabelecida com sucesso!");
            String sql = "INSERT INTO produtos (prod_nome, prod_preco, prod_categoria, prod_estoque) VALUES (?,?,?,?)";
            AddProdutos addProd = new AddProdutos();
            ConsultaProdutos consulta = new ConsultaProdutos();

            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                try{
                    do{
                        System.out.println("""
                        \n-Aplicativo para consulta e cadastro de produtos na loja-
                        1- Fazer uma consulta sobre os produtos cadastrados.
                        2- Cadastrar um novo produto.
                        0- Sair da aplicação.
                        """);
                        System.out.println("Digite a opção do que você quer fazer (1-2 ou 0 para sair): ");
                        opcao = teclado.nextInt();

                        if(opcao < 0 || opcao > 2){
                            System.out.println("Você digitou uma opção inválida. Por favor, digite uma opção válida (1-2, ou 0 para sair)!\n");
                        }
                        else if(opcao == 1){
                            consulta.consultar(conn);
                        }else if (opcao == 2){
                            addProd.adicionar(stmt);
                        }else if (opcao == 0){
                            System.out.println("Até mais!");
                            conn.close();
                        }
                    }while(opcao != 0);

                } catch (Exception e) {
                    throw new InputMismatchException("Você digitou a opção de maneira incorreta: " + e);
                }


            } catch (SQLException e) {
                System.out.println("Erro ao executar a consulta: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Falha na conexão: " + e.getMessage());
        }

    }
}
