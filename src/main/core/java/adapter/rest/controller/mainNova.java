package adapter.rest.controller;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class mainNova {
    public static void main(String[] args) {
        ProdutosController prodControl = new ProdutosController();
        CidadesController cidaControl = new CidadesController();
        EnderecosController endeControl = new EnderecosController();
        ClientesController clieControl = new ClientesController();
        int opcao;

            try{
                while(true){
                    System.out.println("""
                        === LOJA - MENU PRINCIPAL ===
                        1. Gerenciar Produtos
                        2. Gerenciar Clientes
                        3. Gerenciar Endereços
                        4. Gerenciar Cidades
                        0. Sair
                        """);
                    System.out.println("Escolha uma opção (1-4 ou 0 para sair): ");
                    opcao = lerOpcao();

                    switch (opcao) {
                        case 1 -> menuProdutos(prodControl);
                        case 2 -> menuUsuarios(clieControl);
                        case 3 -> menuEnderecos(endeControl);
                        case 4 -> menuCidades(cidaControl);
                        case 0 -> {
                            System.out.println("Encerrando a aplicação...");
                            return;
                        }
                        default -> System.out.println("Você digitou uma opção inválida. Por favor, digite uma opção válida (1-4, ou 0 para sair)!\n");
                    }
                }
            } catch (Exception e) {
                throw new InputMismatchException("Você digitou a opção de maneira incorreta: " + e);
            }
    }

    private static int lerOpcao(){
        Scanner teclado = new Scanner(System.in);
        try{
            return teclado.nextInt();
        } catch (Exception e) {
            throw new InputMismatchException("Erro de digitação: " + e.getMessage());
        }
    }

    public static void menuProdutos(ProdutosController prodControl) throws SQLException {
        while(true){
            System.out.println("""
                    \n=== PRODUTOS ===
                    1. Adicionar Produto.
                    2. Listar Produtos.
                    3. Editar Produto.
                    4. Excluir Produto.
                    0. Voltar ao Menu Principal.
                    """);
            System.out.println("Escolha uma opção (1-4 ou 0 para sair): ");
            int opcao = lerOpcao();

            switch(opcao){
                case 1-> prodControl.addProduto();
                case 2-> prodControl.listagemProdutos();
                case 3-> prodControl.updateProduto();
                case 4-> prodControl.deleteProduto();
                case 0-> {return;}
                default-> System.out.println("Opção inválida! Escolha uma opção válida (1-4 ou 0 para sair).");
            }
        }
    }
    public static void menuCidades(CidadesController cidaControl) throws SQLException{
        while (true){
            System.out.println("""
                    \n=== CIDADES ===
                    1. Adicionar Cidade.
                    2. Listar Cidades.
                    3. Editar Cidade.
                    4. Excluir cidade.
                    0. Voltar ao Menu Principal.
                    """);
            System.out.println("Escolha uma opção (1-4 ou 0 para sair): ");
            int opcao = lerOpcao();

            switch(opcao){
                case 1-> cidaControl.addCidade();
                case 2-> cidaControl.listagemCidades();
                case 3-> cidaControl.updateCidade();
                case 4-> cidaControl.deleteCidade();
                case 0->{return;}
                default-> System.out.println("Opção inválida! Escolha uma opção válida (1-4 ou 0 para sair).");
            }
        }
    }
    public static void menuEnderecos(EnderecosController endeControl) throws SQLException{
        while (true){
            System.out.println("""
                    \n=== ENDEREÇOS ===
                    1. Adicionar Endereço.
                    2. Listar Endereços.
                    3. Editar Endereço.
                    4. Excluir Endereço.
                    0. Voltar ao Menu Principal.
                    """);
            System.out.println("Escolha uma opção (1-4 ou 0 para sair): ");
            int opcao = lerOpcao();

            switch(opcao){
                case 1-> endeControl.addEndereco();
                case 2-> endeControl.listagemEnderecos();
                case 3-> endeControl.updateEndereco();
                case 4-> endeControl.deleteEndereco();
                case 0->{return;}
                default-> System.out.println("Opção inválida! Escolha uma opção válida (1-4 ou 0 para sair).");
            }
        }
    }
    public static void menuUsuarios(ClientesController clieControl) throws SQLException{
        while (true){
            System.out.println("""
                    \n=== CLIENTES ===
                    1. Adicionar Cliente.
                    2. Listar Clientes.
                    3. Editar Cliente.
                    4. Excluir Cliente.
                    0. Voltar ao Menu Principal.
                    """);
            System.out.println("Escolha uma opção (1-4 ou 0 para sair): ");
            int opcao = lerOpcao();

            switch(opcao){
                case 1-> clieControl.addCliente();
                case 2-> clieControl.listagemClientes();
                case 3-> clieControl.updateCliente();
                case 4-> clieControl.deleteCliente();
                case 0->{return;}
                default-> System.out.println("Opção inválida! Escolha uma opção válida (1-4 ou 0 para sair).");
            }
        }
    }
}
