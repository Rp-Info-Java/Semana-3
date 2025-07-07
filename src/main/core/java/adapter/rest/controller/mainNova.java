package adapter.rest.controller;


import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class mainNova {
    public static void main(String[] args) {
        ProdutosController prodControl = new ProdutosController();
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
                        //case 2 -> menuUsuarios();
                        //case 3 -> menuEnderecos();
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
                    === PRODUTOS ===
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
}
