package domain.repositories;

import java.sql.*;
import java.util.Scanner;

public class DeleteEnderecos {
    private String sqlDesativar = "UPDATE enderecos SET ende_status = ? WHERE ende_id = ?";
    private String sqlDeletar = "DELETE FROM enderecos WHERE ende_id = ?";
    private String sqlListagem = "SELECT * FROM enderecos ORDER BY ende_id";
    int opcao, id;
    Scanner teclado = new Scanner(System.in);

    public void deletarEnderecos(Connection conn) throws SQLException {
        int controle = 0, linhasAfetadas = 0;
        do{
            System.out.println("\n--Desativar/Deletar endereço--");
            UpdateEnderecos.listAll(conn, sqlListagem);

            System.out.println("""
                Opções:
                1- Desativar endereço.
                2- Deletar endereço.
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

                        System.out.println("Digite o ID do endereço que deseja desativar: ");
                        id = teclado.nextInt();

                        while(rs.next()){
                            int idEnde = rs.getInt("ende_id");
                            if(idEnde == id && (rs.getString("ende_status").equals("N"))){
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
                        System.out.println("Endereço desativado com sucesso!");
                    }else{
                        if(controle == 0){
                            System.out.println("Nenhum endereço encontrado com esse ID!");
                        }else{
                            System.out.println("O endereço fornecido já foi desativado!");
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else if(opcao == 2){
                try(PreparedStatement stmt = conn.prepareStatement(sqlDeletar)){
                    try(Statement stmt2 = conn.createStatement();
                        ResultSet rs = stmt2.executeQuery(sqlListagem)){

                        System.out.println("Digite o ID do endereço que deseja deletar do banco de dados: ");
                        id = teclado.nextInt();

                        while(rs.next()){
                            int idEnde = rs.getInt("ende_id");
                            if(idEnde == id && (rs.getString("ende_status").equals("N"))){
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
                        System.out.println("Endereço deletado com sucesso!");
                    }else{
                        if(controle == 0){
                            System.out.println("Nenhum endereço encontrado com esse ID");
                        }else{
                            System.out.println("O endereço fornecido deve ser desativado primeiro!");
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else if(opcao < 0 || opcao > 2){
                System.out.println("Opção inválida!");
            }
        }while (opcao != 0);
    }
}
