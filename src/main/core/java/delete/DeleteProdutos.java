package delete;

import insert.ConsultaProdutos;

import java.sql.*;
import java.util.Scanner;

public class DeleteProdutos {
    private String sqlDesativar = "UPDATE produtos SET prod_status = ? WHERE prod_id = ?";
    private String sqlDeletar = "DELETE FROM produtos WHERE prod_id = ?";
    private String sqlListagem = "SELECT * FROM produtos ORDER BY prod_id";
    int opcao, id, linhasAfetadas = 0;
    Scanner teclado = new Scanner(System.in);

    public void deletarProds(Connection conn) throws SQLException {
        int controle = 0;
        do{
            System.out.println("\n--Desativar/Deletar produto--");
            ConsultaProdutos.listAll(conn, sqlListagem);

            System.out.println("""
                Opções:
                1- Desativar produto.
                2- Deletar produto.
                0- Cancelar operação.
                """);
            System.out.println("O que você deseja fazer? (1-2 ou 0 para sair): ");
            opcao = teclado.nextInt();

            if(opcao == 0){
                System.out.println("Cancelando a operação.");
            }else if(opcao == 1){
                try(PreparedStatement stmt = conn.prepareStatement(sqlDesativar)){
                    try(Statement stmt2 = conn.createStatement();
                        ResultSet rs = stmt2.executeQuery(sqlListagem)){
                        controle = 0;

                        System.out.println("Digite o ID do produto que deseja desativar: ");
                        id = teclado.nextInt();

                        while(rs.next()){
                            int idProd = rs.getInt("prod_id");
                            if(idProd == id && (rs.getString("prod_status").equals("N"))){
                                controle++;
                                break;
                            }
                        }
                    }catch (SQLException e) {
                        System.out.println("Erro ao executar a consulta: " + e.getMessage());
                    }
                    if(controle == 0){
                        stmt.setString(1, "N");
                        stmt.setInt(2, id);
                        linhasAfetadas = stmt.executeUpdate();
                    }

                    if(linhasAfetadas > 0){
                        System.out.println("Produto desativado com sucesso!");
                    }else {
                        if(controle == 0){
                            System.out.println("Nenhum produto encontrado com esse ID");
                        }else{
                            System.out.println("O produto informado já foi desativado.");
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else if(opcao == 2){
                try(PreparedStatement stmt = conn.prepareStatement(sqlDeletar)){
                    try(Statement stmt2 = conn.createStatement();
                        ResultSet rs = stmt2.executeQuery(sqlListagem)){

                        System.out.println("Digite o ID do produto que deseja deletar do banco de dados: ");
                        id = teclado.nextInt();

                        while(rs.next()){
                            int idProd = rs.getInt("prod_id");
                            if(idProd == id && (rs.getString("prod_status").equals("N"))){
                                stmt.setInt(1, id);
                                linhasAfetadas = stmt.executeUpdate();
                                break;
                            }else{
                                controle++;
                            }
                        }
                    }catch (SQLException e) {
                        System.out.println("Erro ao executar a consulta: " + e.getMessage());
                    }
                    if(linhasAfetadas > 0){
                        System.out.println("Produto deletado com sucesso!");
                    }else{
                        if(controle == 0){
                            System.out.println("Nenhum produto encontrado com esse ID");
                        }else{
                            System.out.println("O produto fornecido deve ser desativado primeiro!");
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }while (opcao != 0);
    }
}
