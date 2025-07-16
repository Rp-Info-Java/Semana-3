package adapter.rest.controller;

import application.usecase.CidadesUseCase;
import application.usecase.EnderecosUseCase;
import domain.model.entity.Enderecos;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EnderecosController {
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
        try{
            return teclado2.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Erro ao digitar: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void listagemEnderecos(){
        EnderecosUseCase.listarEnderecos();
    }

    public void addEndereco() throws SQLException {
        Enderecos endereco = new Enderecos();
        int leitura;
        String leituraCEP = "";
        CidadesUseCase.listarCidades();

        System.out.println("\n-Adicionando um endereço ao banco de dados-");
        System.out.println("Digite o ID da cidade que terá um novo endereço adicionado: ");
        leitura = lerOpcao();
        if(EnderecosUseCase.controleCidaID(leitura)){
            EnderecosUseCase.listarEnderecos();
            System.out.println("Digite o CEP do endereço (CEPs não podem se repitir no banco): ");
            leituraCEP = lerOpcaoString();

            if(leituraCEP.length() == 8){
                endereco.setEnde_cep(leituraCEP);

                endereco.setEnde_cida_id(leitura);
                System.out.println("Digite o nome da rua do endereço: ");
                endereco.setEnde_rua(lerOpcaoString());
                System.out.println("Digite o número do endereço: ");
                endereco.setEnde_numero(lerOpcao());
                System.out.println("Digite o complemento do endereço: ");
                endereco.setEnde_complemento(lerOpcaoString());
                System.out.println("Digite o bairro do endereço: ");
                endereco.setEnde_bairro(lerOpcaoString());

                endereco.setEnde_status("S");
                EnderecosUseCase.inserirEnderecos(endereco);
            }else{
                EnderecosUseCase.tamanhoCEP(leituraCEP);
            }
        }
    }

    public void updateEndereco(){
        do {
            System.out.println("\n--- Atualização de endereço ---");
            System.out.println("""
                    Escolha uma das opções de atualização:
                    1- Atualizar nome da rua do endereço.
                    2- Atualizar número do endereço.
                    3- Atualizar complemento do endereço.
                    4- Atualizar bairro do endereço.
                    5- Reativar um endereço.
                    0- Cancelar atualização.
                    """);
            System.out.println("Digite a opção de atualização desejada (1-5 ou 0 para sair): ");
            opcao = lerOpcao();
            Enderecos endereco = new Enderecos();

            if (opcao == 0) {
                EnderecosUseCase.attEnderecos(endereco, opcao); //mensagem para sair do menu
            } else if (opcao < 0 || opcao > 5) {
                EnderecosUseCase.attEnderecos(endereco, opcao);
            } else if (opcao == 1) {
                EnderecosUseCase.listarEnderecos();

                System.out.println("-Atualização do nome da RUA por ID do endereço-");
                System.out.println("Escreva o NOVO nome da rua para o endereço a ser alterado: ");
                endereco.setEnde_rua(lerOpcaoString());

                System.out.println("Escreva o ID do endereco: ");
                endereco.setEnde_id(lerOpcao());

                EnderecosUseCase.attEnderecos(endereco, opcao);
            } else if (opcao == 2) {
                EnderecosUseCase.listarEnderecos();

                System.out.println("-Atualização do número por ID do endereço-");
                System.out.println("Escreva o NOVO número para o endereço a ser alterado: ");
                endereco.setEnde_numero(lerOpcao());

                System.out.println("Escreva o ID do endereco: ");
                endereco.setEnde_id(lerOpcao());

                EnderecosUseCase.attEnderecos(endereco, opcao);
            } else if (opcao == 3) {
                EnderecosUseCase.listarEnderecos();

                System.out.println("-Atualização de complemento por ID do endereço-");
                System.out.println("Escreva o nome do NOVO complemento para o endereço a ser alterado: ");
                endereco.setEnde_complemento(lerOpcaoString());
                System.out.println("Escreva o ID do endereço: ");
                endereco.setEnde_id(lerOpcao());

                EnderecosUseCase.attEnderecos(endereco, opcao);
            } else if (opcao == 4) {
                EnderecosUseCase.listarEnderecos();

                System.out.println("-Atualização do Bairro por ID do endereço-");
                System.out.println("Escreva o nome do NOVO bairro para o endereço a ser alterado: ");
                endereco.setEnde_bairro(lerOpcaoString());
                System.out.println("Escreva o ID do endereço: ");
                endereco.setEnde_id(lerOpcao());

                EnderecosUseCase.attEnderecos(endereco, opcao);
            } else if (opcao == 5){
                EnderecosUseCase.listarEnderecos();

                System.out.println("-Reativação de Endereço por ID-");
                System.out.println("Escreva o ID do endereço a ser reativado: ");
                endereco.setEnde_id(lerOpcao());

                EnderecosUseCase.attEnderecos(endereco, opcao);
            }
        } while (opcao != 0);
    }

    public void deleteEndereco() throws SQLException{
        int id;
        do{
            EnderecosUseCase.listarEnderecos();

            System.out.println("""
                    Opções:
                    1- Desativar o Endereço.
                    2- Deletar o Endereço.
                    0- Cancelar operação.
                    """);
            System.out.println("O que você deseja fazer? (1-2 ou 0 para sair): ");
            opcao = lerOpcao();

            if(opcao == 0){
                EnderecosUseCase.deleEnderecos(opcao, 0);
            }else if(opcao < 0 || opcao > 2){
                EnderecosUseCase.deleEnderecos(opcao, 0);
            }else if (opcao == 1){
                System.out.println("Digite o ID do endereço que deseja desativar: ");
                id = lerOpcao();

                EnderecosUseCase.deleEnderecos(opcao, id);
            }else if(opcao == 2){
                System.out.println("Digite o ID do endereço que deletar do banco de dados: ");
                id = lerOpcao();

                EnderecosUseCase.deleEnderecos(opcao, id);
            }
        }while (opcao != 0);
    }
}
