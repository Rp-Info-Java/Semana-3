package adapter.rest.controller;

import application.usecase.ClientesUseCase;
import application.usecase.EnderecosUseCase;
import domain.model.entity.Clientes;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientesController {
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

    public void listagemClientes(){
        ClientesUseCase.listarClientes();
    }

    public void addCliente() throws SQLException {
        Clientes cliente = new Clientes();
        LocalDate hoje = LocalDate.now();
        int leitura;
        String leituraCPF = "";

        ClientesUseCase.listarClientes();

        System.out.println("--Adicionando cliente ao banco de dados--");
        System.out.println("Digite o CPF do cliente: ");
        leituraCPF = lerOpcaoString();
        if(leituraCPF.length() == 11 && ClientesUseCase.controleCPF(leituraCPF)){
            cliente.setClie_cpf(leituraCPF);

            EnderecosUseCase.listarEnderecos();
            System.out.println("Digite o ID do endereço do cliente: ");
            leitura = lerOpcao();
            if(ClientesUseCase.controleEndeID(leitura)){
                cliente.setClie_ende_id(leitura);

                System.out.println("Digite o nome do cliente: ");
                cliente.setClie_nome(lerOpcaoString());
                System.out.println("Digite o e-mail do cliente: ");
                cliente.setClie_email(lerOpcaoString());
                System.out.println("Digite o telefone do cliente (apenas números): ");
                cliente.setClie_telefone(lerOpcaoString());
                System.out.println("Inserindo a data atual do cadastro do cliente...");
                cliente.setClie_data_cadastro(Date.valueOf(hoje));
                System.out.println("Inserindo cliente no banco de dados...");

                ClientesUseCase.inserirClientes(cliente);
            }
        }else{
            if(leituraCPF.length() != 11){
                ClientesUseCase.charCPF(leituraCPF);
            }
        }
    }

    public void updateCliente() throws SQLException {
        int leitura = 0;
        do{
            System.out.println("\n--- Atualização de cliente ---");
            System.out.println("""
                    Escolha uma das opções de atualização:
                    1- Atualizar e-mail do cliente.
                    2- Atualizar número de telefone do cliente.
                    3- Atualizar o endereço do cliente (pelo ID do endereço).
                    4- Reativar um cliente.
                    0- Cancelar atualização.
                    """);
            System.out.println("Digite a opção de atualização desejada(1-4 ou 0 para sair): ");
            opcao = lerOpcao();
            Clientes cliente = new Clientes();

            if(opcao == 0){
                ClientesUseCase.attClientes(cliente, opcao);
            }else if(opcao < 0 || opcao > 4){
                ClientesUseCase.attClientes(cliente, opcao);
            }else if(opcao == 1){
                ClientesUseCase.listarClientes();

                System.out.println("-- Atualização de email por ID do cliente --");
                System.out.println("Escreva o ID do cliente: ");
                cliente.setClie_id(lerOpcao());
                System.out.println("Escreva o NOVO email do cliente: ");
                cliente.setClie_email(lerOpcaoString());

                ClientesUseCase.attClientes(cliente, opcao);
            }else if (opcao == 2){
                ClientesUseCase.listarClientes();

                System.out.println("-- Atualização de telefone por ID do cliente --");
                System.out.println("Escreva o ID do cliente: ");
                cliente.setClie_id(lerOpcao());
                System.out.println("Escreva o NOVO telefone do cliente (sem parênteses, caracteres especiais ou espaços): ");
                cliente.setClie_telefone(lerOpcaoString());

                ClientesUseCase.attClientes(cliente, opcao);
            }else if (opcao == 3){
                ClientesUseCase.listarClientes();

                System.out.println("-- Atualização de endereço (ID) por ID do cliente --");
                System.out.println("Escreva o ID do cliente: ");
                cliente.setClie_id(lerOpcao());

                EnderecosUseCase.listarEnderecos();

                System.out.println("\nO endereço escolhido deve estar cadastrado no banco de dados." +
                        " Em caso de não existir o endereço, cadastrá-lo pelo menu.");
                System.out.println("Escreva o NOVO ID de endereço do cliente: ");
                leitura = lerOpcao();

                if(ClientesUseCase.controleEndeID(leitura)){
                    cliente.setClie_ende_id(leitura);
                    ClientesUseCase.attClientes(cliente, opcao);
                }
            }else if (opcao == 4){
                ClientesUseCase.listarClientes();

                System.out.println("-- Reativação de cliente --");
                System.out.println("Escreva o nome do cliente: ");
                cliente.setClie_nome(lerOpcaoString());
                System.out.println("Escreva o ID do cliente: ");
                cliente.setClie_id(lerOpcao());

                ClientesUseCase.attClientes(cliente, opcao);
            }
        }while(opcao != 0);
    }

    public void deleteCliente(){
        int id;
        do{
            ClientesUseCase.listarClientes();
            System.out.println("""
                    Opções:
                    1- Desativar cliente.
                    2- Deletar cliente.
                    0- Cancelar operação.
                    """);
            System.out.println("O que você deseja fazer? (1-2 ou 0 para sair): ");
            opcao = lerOpcao();

            if (opcao == 0) {
                ClientesUseCase.deleClientes(opcao, 0); //o id 0 é passado pq, nessa opcao, n vai fazer nada com o cliente, mas o parametro deve ser passado
            } else if (opcao < 0 || opcao > 2) {
                ClientesUseCase.deleClientes(opcao, 0);
            } else if (opcao == 1) {
                System.out.println("Digite o ID do cliente que deseja desativar: ");
                id = lerOpcao();

                ClientesUseCase.deleClientes(opcao, id);
            } else if (opcao == 2) {
                System.out.println("Digite o ID do cliente que deseja deletar do banco de dados: ");
                id = lerOpcao();

                ClientesUseCase.deleClientes(opcao, id);
            }
        }while (opcao != 0);
    }
}
