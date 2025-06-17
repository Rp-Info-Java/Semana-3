package delete;

import update.UpdateCidades;

import java.sql.*;
import java.util.Scanner;

public class DeleteCidades {
    private String sqlDesativar = "UPDATE cidades SET cida_status = ? WHERE cida_id = ?";
    private String sqlDeletar = "DELETE FROM cidades WHERE cida_id = ?";
    private String sqlListagem = "SELECT * FROM cidades ORDER BY cida_id";
    int opcao, id;
    Scanner teclado = new Scanner(System.in);

    public void deletarCidades(Connection conn) throws SQLException {
        int controle = 0, linhasAfetadas = 0;
        do{
            System.out.println("\n--Desativar/Deletar cidade--");
            UpdateCidades.listCidades(conn, sqlListagem);

            System.out.println("""
                Opções:
                1- Desativar cidade.
                2- Deletar cidade.
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

                        System.out.println("Digite o ID da cidade que deseja desativar: ");
                        id = teclado.nextInt();

                        while(rs.next()){
                            int idCida = rs.getInt("cida_id");
                            if(idCida == id && (rs.getString("cida_status").equals("N"))){
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
                        System.out.println("Cidade desativada com sucesso!");
                    }else {
                        if(controle != 0){
                            System.out.println("A cidade informada já foi desativada.");
                        }else{
                            System.out.println("Nenhuma cidade encontrada com esse ID");
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else if(opcao == 2){
                try(PreparedStatement stmt = conn.prepareStatement(sqlDeletar)){
                    try(Statement stmt2 = conn.createStatement();
                        ResultSet rs = stmt2.executeQuery(sqlListagem)){

                        System.out.println("Digite o ID da cidade que deseja deletar do banco de dados: ");
                        id = teclado.nextInt();

                        while(rs.next()){
                            int idCida = rs.getInt("cida_id");
                            if(idCida == id && (rs.getString("cida_status").equals("N"))){
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
                        System.out.println("Cidade deletada com sucesso!");
                    }else{
                        if(controle == 0){
                            System.out.println("Nenhuma cidade encontrada com esse ID");
                        }else{
                            System.out.println("A cidade fornecida deve ser desativada primeiro!");
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }while (opcao != 0);
    }
}
