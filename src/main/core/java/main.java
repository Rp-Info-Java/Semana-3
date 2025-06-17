import delete.DeleteCidades;
import delete.DeleteClientes;
import delete.DeleteEnderecos;
import delete.DeleteProdutos;
import insert.AddProdutos;
import insert.ConsultaProdutos;
import update.UpdateCidades;
import update.UpdateClientes;
import update.UpdateEnderecos;
import update.UpdateProdutos;

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
            AddProdutos addProd = new AddProdutos();
            ConsultaProdutos consulta = new ConsultaProdutos();
            UpdateProdutos att = new UpdateProdutos();
            UpdateEnderecos end = new UpdateEnderecos();
            UpdateCidades cid = new UpdateCidades();
            UpdateClientes clie = new UpdateClientes();
            DeleteProdutos delProd = new DeleteProdutos();
            DeleteClientes delClie = new DeleteClientes();
            DeleteCidades delCida = new DeleteCidades();
            DeleteEnderecos delEnde = new DeleteEnderecos();
            String sql = "INSERT INTO produtos (prod_nome, prod_preco, prod_categoria, prod_estoque, prod_status) VALUES (?,?,?,?,?)";
            String sql2 = "INSERT INTO cidades (cida_id, cida_nome, cida_uf,cida_status) VALUES (?,?,?,?)";
            String sql3 = "INSERT INTO enderecos (ende_id, ende_cep, ende_rua, ende_numero, ende_complemento, ende_bairro, ende_cida_id, ende_status) VALUES (?,?,?,?,?,?,?,?)";
            String sql4 = "INSERT INTO clientes (clie_id, clie_nome, clie_email, clie_cpf, clie_telefone, clie_data_cadastro, clie_ende_id, clie_status) VALUES (?,?,?,?,?,?,?,?)";
                try{
                    do{
                        System.out.println("""
                        \n-Aplicativo de consulta e cadastro de dados de Produtos, Clientes, Endereços e Cidades-
                        1-  Fazer uma consulta sobre os produtos cadastrados.
                        2-  Cadastrar um novo produto.
                        3-  Atualizar um produto existente.
                        4-  Deletar/Desativar um produto.
                        5-  Editar dados de endereço.
                        6-  Deletar/Desativar um endereço.
                        7-  Editar dados de clientes.
                        8-  Deletar/Desativar um cliente.
                        9-  Editar dados de cidades.
                        10- Deletar/Desativar cidade.
                        0-  Sair da aplicação.
                        """);
                        System.out.println("Digite a opção do que você quer fazer (1-9 ou 0 para sair): ");
                        opcao = teclado.nextInt();

                        if(opcao < 0 || opcao > 10){
                            System.out.println("Você digitou uma opção inválida. Por favor, digite uma opção válida (1-6, ou 0 para sair)!\n");
                        }
                        else if(opcao == 1){
                            consulta.consultar(conn);
                        }else if (opcao == 2){
                            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                                addProd.adicionar(stmt);
                            }
                            catch (SQLException e) {
                                System.out.println("Erro ao executar a consulta: " + e.getMessage());
                            }
                        }else if(opcao == 3){
                            att.atualizacao(conn);
                        }else if(opcao == 4){
                            delProd.deletarProds(conn);
                        }else if(opcao == 5){
                            try (PreparedStatement stmt = conn.prepareStatement(sql3)) {
                                end.atualizacaoEnde(conn, stmt);
                            }catch (SQLException e) {
                                System.out.println("Erro ao executar a consulta: " + e.getMessage());
                            }
                        }else if(opcao == 6){
                            delEnde.deletarEnderecos(conn);
                        }else if(opcao == 7){
                            try (PreparedStatement stmt = conn.prepareStatement(sql4)) {
                                clie.atualizacaoClie(conn, stmt);
                            }
                            catch (SQLException e) {
                                System.out.println("Erro ao executar a consulta: " + e.getMessage());
                            }
                        }else if(opcao == 8){
                            delClie.deletarClientes(conn);
                        }else if(opcao == 9){
                            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)){
                                cid.atualizarCida(conn, stmt2);
                            }catch (SQLException e) {
                                System.out.println("Erro ao executar a consulta: " + e.getMessage());
                            }
                        }else if(opcao == 10){
                            delCida.deletarCidades(conn);
                        }else if (opcao == 0){
                            System.out.println("Até mais!");
                            conn.close();
                        }
                    }while(opcao != 0);

                } catch (Exception e) {
                    throw new InputMismatchException("Você digitou a opção de maneira incorreta: " + e);
                }


        } catch (SQLException e) {
            System.out.println("Falha na conexão: " + e.getMessage());
        }

    }
}
