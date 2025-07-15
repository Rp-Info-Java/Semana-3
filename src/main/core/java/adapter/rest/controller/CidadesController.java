package adapter.rest.controller;

import application.usecase.CidadesUseCase;
import domain.model.entity.Cidades;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CidadesController {
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

    private static int lerOpcao(){
        Scanner teclado2 = new Scanner(System.in);
        try {
            return teclado2.nextInt();
        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    public void listagemCidades(){
        CidadesUseCase.listarCidades();
    }

    public void addCidade(){
        Cidades cidade = new Cidades();
        System.out.println("\n--Adicionando uma cidade ao banco de dados--");
        System.out.println("Digite o nome da cidade: ");
        cidade.setCida_nome(lerOpcaoString());
        System.out.println("Digite a Unidade Federativa(UF) da cidade: ");
        cidade.setCida_uf(lerOpcaoString());
        cidade.setCida_status("S");

        CidadesUseCase.inserirCidades(cidade);
    }

    public void updateCidade(){
        do{
            System.out.println("\n--- Atualização de dados da cidade ---");
            System.out.println("""
                    Escolha uma das opções de atualização:
                    1- Atualizar nome da cidade.
                    2- Atualizar Unidade Federativa (UF) da cidade.
                    0- Cancelar atualização.
                    """);
            System.out.println("Digite a opção de atualização desejada (1-2 ou 0 para sair): ");
            opcao = lerOpcao();
            Cidades cidade = new Cidades();

            if(opcao == 0){
                CidadesUseCase.attCidades(cidade, opcao);
            }else if(opcao < 0 || opcao > 2){
                CidadesUseCase.attCidades(cidade, opcao);
            }else if(opcao == 1){
                CidadesUseCase.listarCidades();

                System.out.println("\n-Atualização do nome da cidade por ID da cidade-");
                System.out.println("Escreva o NOVO nome da cidade que será alterada: ");
                cidade.setCida_nome(lerOpcaoString());

                System.out.println("Escreva o ID da cidade que irá ser alterada: ");
                cidade.setCida_id(lerOpcao());

                CidadesUseCase.attCidades(cidade, opcao);
            }else if(opcao == 2){
                CidadesUseCase.listarCidades();

                System.out.println("\n-Atualização da UF da cidade, por ID da cidade-");
                System.out.println("Escreva a NOVA UF da cidade a ser alterada: ");
                cidade.setCida_uf(lerOpcaoString().toUpperCase());

                System.out.println("Escreva o ID da cidade que irá ser alterada: ");
                cidade.setCida_id(lerOpcao());

                CidadesUseCase.attCidades(cidade, opcao);
            }
        } while(opcao != 0);
    }

    public void deleteCidade(){
        int id;
        do{
            CidadesUseCase.listarCidades();
            System.out.println("""
                    Opções:
                    1- Desativar cidade.
                    2- Deletar cidade.
                    0- Cancelar operação.
                    """);
            System.out.println("O que você deseja fazer? (1-2 ou 0 para sair): ");
            opcao = lerOpcao();

            if (opcao == 0) {
                CidadesUseCase.deleCidades(opcao, 0); //o id 0 é passado pq, nessa opcao, n vai fazer nada com a cidade, mas o parametro deve ser passado
            } else if (opcao < 0 || opcao > 2) {
                CidadesUseCase.deleCidades(opcao, 0);
            } else if (opcao == 1) {
                System.out.println("Digite o ID da cidade que deseja desativar: ");
                id = lerOpcao();

                CidadesUseCase.deleCidades(opcao, id);
            } else if (opcao == 2) {
                System.out.println("Digite o ID da cidade que deseja deletar do banco de dados: ");
                id = lerOpcao();

                CidadesUseCase.deleCidades(opcao, id);
            }
        }while(opcao != 0);
    }
}
