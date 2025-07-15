package adapter.rest.controller;

import application.usecase.ProdutosUseCase;
import domain.model.entity.Produtos;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProdutosController {
    int opcao = 0;

    private static String lerOpcaoString(){
        Scanner teclado = new Scanner(System.in);
        try{
            return teclado.nextLine();
        }catch (InputMismatchException e){
            System.out.println("Erro ao digitar: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static int lerOpcao() {
        Scanner teclado2 = new Scanner(System.in);
        try {
            return teclado2.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Erro ao digitar: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void listagemProdutos() {
        do {
            System.out.println("""
                    \n--Opções de consulta no banco de dados da loja--
                    1- Listar todos os produtos da loja.
                    2- Listar apenas nome e preço dos produtos.
                    3- Listar os produtos da categoria 'Limpeza'.
                    4- Listar produtos cujo estoque seja maior que 10.
                    5- Listar produtos com o preço entre R$ 100,00 e R$ 1000,00.
                    6- Listar produtos 'Gamer' disponíveis na loja.
                    7- Listar os 3 produtos mais baratos da loja.
                    8- Contar quantos produtos existem na categoria de 'armazenamento' da loja.
                    9- Mostrar a média de preços dos produtos por categoria.
                    0- Cancelar consulta no banco de dados da loja.
                    """);
            System.out.println("Digite a opção de consulta a ser realizada (1-9 ou 0 para cancelar a consulta): ");
            opcao = lerOpcao();

            if (opcao == 0) {
                System.out.println("Cancelando busca.");
            } else if (opcao == 1) {
                ProdutosUseCase.listarProdutos();
            } else if (opcao == 2) {
                ProdutosUseCase.listarProdsNamePrice();
            } else if (opcao == 3) {
                ProdutosUseCase.listarProdLimp();
            } else if (opcao == 4) {
                ProdutosUseCase.listarProdEstoqueMedio();
            } else if (opcao == 5) {
                ProdutosUseCase.listarProdPriceRange();
            } else if (opcao == 6) {
                ProdutosUseCase.listarProdGamer();
            } else if (opcao == 7) {
                ProdutosUseCase.listarProdBarato();
            } else if (opcao == 8) {
                ProdutosUseCase.listarProdArm();
            } else if (opcao == 9) {
                ProdutosUseCase.listarProdMediaPreco();
            } else {
                System.out.println("A opção digitada é inválida. Por favor, digite uma opção válida (1-9 ou 0 para cancelar a consulta).\n");
            }
        } while (opcao != 0);
    }

    public void addProduto() {
        Produtos produto = new Produtos();
        System.out.println("\n--Adicionando produto ao banco de dados da loja--");
        System.out.println("Digite o nome do produto: ");
        produto.setProd_nome(lerOpcaoString());
        System.out.println("Digite o preço do produto (com PONTO ao invés de vírgula): ");
        produto.setProd_preco(Double.parseDouble(lerOpcaoString()));
        System.out.println("Digite a categoria do produto: ");
        produto.setProd_categoria(lerOpcaoString());
        System.out.println("Digite o estoque do produto: ");
        produto.setProd_estoque(Integer.parseInt(lerOpcaoString()));
        System.out.println("Ativando status do novo produto...");
        produto.setProd_status("S");

        ProdutosUseCase.inserirProdutos(produto);
    }

    public void updateProduto() throws SQLException {
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
            opcao = lerOpcao();
            Produtos produto = new Produtos();

            if (opcao == 0) {
                ProdutosUseCase.attProdutos(produto, opcao); //mensagem para sair do menu
            } else if (opcao < 0 || opcao > 3) {
                ProdutosUseCase.attProdutos(produto, opcao);
            } else if (opcao == 1) {
                ProdutosUseCase.listarProdutos();

                System.out.println("-Atualização de categoria por NOME e ID do produto-");
                System.out.println("Escreva o nome da NOVA categoria para o produto a ser alterado: ");
                produto.setProd_categoria(lerOpcaoString());

                System.out.println("Escreva o nome do produto: ");
                produto.setProd_nome(lerOpcaoString());

                System.out.println("Escreva o id do produto: ");
                produto.setProd_id(Integer.parseInt(lerOpcaoString()));

                ProdutosUseCase.attProdutos(produto, opcao);
            } else if (opcao == 2) {
                ProdutosUseCase.listarProdutos();

                System.out.println("-Atualização de estoque por NOME e ID do produto-");
                System.out.println("Escreva a quantia do NOVO estoque para o produto a ser alterado: ");
                produto.setProd_estoque(lerOpcao());

                System.out.println("Escreva o nome do produto: ");
                produto.setProd_nome(lerOpcaoString());

                System.out.println("Escreva o id do produto: ");
                produto.setProd_id(Integer.parseInt(lerOpcaoString()));

                ProdutosUseCase.attProdutos(produto, opcao);
            } else if (opcao == 3) {
                ProdutosUseCase.listarProdutos();

                System.out.println("-Atualização de preço por NOME e ID do produto-");
                System.out.println("Escreva o NOVO preço para o produto a ser alterado (com ponto ao invés de vírgula): ");
                produto.setProd_preco(Double.parseDouble(lerOpcaoString()));
                System.out.println("Escreva o nome do produto: ");
                produto.setProd_nome(lerOpcaoString());
                System.out.println("Escreva o id do produto: ");
                produto.setProd_id(Integer.parseInt(lerOpcaoString()));

                ProdutosUseCase.attProdutos(produto, opcao);
            }
        } while (opcao != 0);
    }

    public void deleteProduto() throws SQLException {
        int id;
        do {
            ProdutosUseCase.listarProdutos();

            System.out.println("""
                    Opções:
                    1- Desativar produto.
                    2- Deletar produto.
                    0- Cancelar operação.
                    """);
            System.out.println("O que você deseja fazer? (1-2 ou 0 para sair): ");
            opcao = lerOpcao();

            if (opcao == 0) {
                ProdutosUseCase.deleProdutos(opcao, 0);
            } else if (opcao < 0 || opcao > 2) {
                ProdutosUseCase.deleProdutos(opcao, 0);
            } else if (opcao == 1) {
                System.out.println("Digite o ID do produto que deseja desativar: ");
                id = lerOpcao();

                ProdutosUseCase.deleProdutos(opcao, id);
            } else if (opcao == 2) {
                System.out.println("Digite o ID do produto que deseja deletar do banco de dados: ");
                id = lerOpcao();

                ProdutosUseCase.deleProdutos(opcao, id);
            }

        } while (opcao != 0);
    }
}
