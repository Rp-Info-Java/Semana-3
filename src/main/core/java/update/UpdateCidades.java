package update;

import utils.FunctionsAux;

import java.sql.*;
import java.util.Scanner;

public class UpdateCidades {
    Scanner teclado = new Scanner (System.in);
    String palavra = "";
    int opcao;
    String sqlListagem = "SELECT * FROM cidades ORDER BY cida_id";

    public void atualizarCida(Connection conn, PreparedStatement stmt) throws SQLException {
        do{
            System.out.println("\n--Gerenciamento de dados de cidades--");
            System.out.println("""
                    Opções de gerenciamento:
                    1- Adicionar uma nova cidade ao banco de dados.
                    2- Atualizar o nome de uma cidade existente.
                    3- Atualizar a UF de uma cidade existente.
                    4- Listar todas as cidades cadastradas no banco de dados.
                    0- Cancelar operação e voltar para o menu.
                    """);
            System.out.println("Digite uma opção (1-3 ou 0 para sair): ");
            opcao = teclado.nextInt();

            if(opcao == 0){
                System.out.println("Cancelando operação e voltando para o menu.");
            }else if(opcao == 1){
                inserirCida(conn, stmt);
            }else if(opcao == 2){
                atualizarNomeCida(conn);
            }else if(opcao == 3){
                atualizarUF(conn);
            }else if(opcao == 4){
                listCidades(conn, sqlListagem);
            }else{
                System.out.println("Opção inválida! Digite uma opção válida (1-3 ou 0 para sair)!");
            }
        }while(opcao != 0);
    }

    public void listCidades(Connection conn, String sql) throws SQLException {

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            System.out.println("--Listagem de Cidades--");
            while(rs.next()){
                System.out.printf("| %-8s | %-25s | %-4s | %n",
                        rs.getInt("cida_id"),
                        rs.getString("cida_nome"),
                        rs.getString("cida_uf"));
            }
        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }

    public void inserirCida(Connection conn, PreparedStatement stmt) throws SQLException{
        int controle = 0;

            try(Statement stmt2 = conn.createStatement();
                ResultSet rs = stmt2.executeQuery(sqlListagem)){

                teclado.nextLine();
                System.out.println("--Adicionando uma cidade ao banco de dados--");
                System.out.println("Digite o nome da cidade: ");
                palavra = teclado.nextLine();

                while(rs.next()){
                    String cidade = rs.getString("cida_nome");
                    if(cidade.equals(palavra)){
                        controle++;
                    }
                }
            }catch (SQLException e) {
                System.out.println("Erro ao executar a consulta: " + e.getMessage());
            }
            if(controle == 0){
                stmt.setInt(1, FunctionsAux.getSequenceValue(conn, "seq_cida_id"));
                stmt.setString(2, palavra);

                System.out.println("Digite a UF da cidade (2 letras apenas): ");
                palavra = teclado.nextLine();

                stmt.setString(3, palavra.toUpperCase());

                stmt.executeUpdate();

                System.out.println("Cidade adicionada com sucesso!");
            }else{
                System.out.println("A cidade informada já existe no banco de dados!\n");
            }
    }

    public void atualizarNomeCida(Connection conn) throws SQLException {
        String sql = "UPDATE cidades SET cida_nome = ? WHERE cida_id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        listCidades(conn, sqlListagem);

        teclado.nextLine();
        System.out.println("\n-Atualização do nome da cidade por ID da cidade-");
        System.out.println("Escreva o NOVO nome da cidade a ser alterada: ");
        palavra = teclado.nextLine();
        stmt.setString(1, palavra);

        System.out.println("Escreva o ID da cidade que irá ser alterada: ");
        palavra = teclado.nextLine();
        stmt.setInt(2, Integer.parseInt(palavra));

        int linhasAfetadas = stmt.executeUpdate();

        if(linhasAfetadas > 0){
            System.out.println("Cidade atualizada com sucesso!\n");
        }else{
            System.out.println("Cidade não encontrada!\n");
        }
    }

    public void atualizarUF(Connection conn) throws SQLException {
        String sql = "UPDATE cidades SET cida_UF = ? WHERE cida_id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        listCidades(conn, sqlListagem);

        teclado.nextLine();
        System.out.println("\n-Atualização da UF da cidade, por ID da cidade-");
        System.out.println("Escreva a NOVA UF da cidade a ser alterada: ");
        palavra = teclado.nextLine();
        stmt.setString(1, palavra.toUpperCase());

        System.out.println("Escreva o ID da cidade que irá ser alterada: ");
        palavra = teclado.nextLine();
        stmt.setInt(2, Integer.parseInt(palavra));

        int linhasAfetadas = stmt.executeUpdate();

        if(linhasAfetadas > 0){
            System.out.println("Cidade atualizada com sucesso!\n");
        }else{
            System.out.println("Cidade não encontrada!\n");
        }

    }
}
