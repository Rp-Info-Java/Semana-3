package application.service;

import domain.model.entity.Clientes;
import domain.repositories.clientes.ClientesDAO;
import domain.repositories.clientes.ClientesDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientesService extends ServiceBase{
    private ClientesDAO dao;

    public ClientesService(Connection connection){
        super(connection);
        this.dao = new ClientesDAOImpl(connection);
    }

    public void adicionarClientes(Clientes cliente) throws SQLException{
        this.dao.insert(cliente);
        System.out.println("Cliente adicionado com sucesso!\n");
    }

    public void atualizarClientes(Clientes cliente, int opcao) throws SQLException{
        if(opcao == 0){
            System.out.println("Cancelando a atualização!\n");
        }else if (opcao < 0 || opcao > 3){
            System.out.println("A opção digitada é inválida. Digite uma opção válida!");
        }else if (opcao == 1){
            if(this.dao.update(cliente, opcao)){
                System.out.println("Cliente atualizado com sucesso!\n");
            }else{
                System.out.println("Cliente não encontrado!\n");
            }
        }else if (opcao == 2){
            if(this.dao.update(cliente, opcao)){
                System.out.println("Cliente atualizado com sucesso!\n");
            }else{
                System.out.println("Cliente não encontrado!\n");
            }
        }else if (opcao == 3){
            if(this.dao.update(cliente, opcao)){
                System.out.println("Cliente atualizado com sucesso!\n");
            }else{
                System.out.println("Cliente não encontrado!\n");
            }
        }
    }

    public void deletarClientes(int opcao, int id) throws SQLException{
        Clientes clie = this.dao.getCliente(id);

        if (opcao == 0) {
            System.out.println("Cancelando operação!\n");
        } else if (opcao < 0 || opcao > 2) {
            System.out.println("A opção digitada é inválida. Digite uma opção válida!");
        } else if (opcao == 1) {
            if (clie != null) {
                if (clie.getClie_status().equals("N")) {
                    System.out.println("O cliente informado já foi desativado.");
                } else {
                    clie.setClie_status("N");
                    if (this.dao.delete(clie, opcao)) {
                        System.out.println("Cliente desativado com sucesso!");
                    }
                }
            } else {
                System.out.println("Nenhum cliente encontrado com esse ID.");
            }
        } else if (opcao == 2) {
            if (clie != null) {
                if (clie.getClie_status().equals("N")) {
                    if (this.dao.delete(clie, opcao)) {
                        System.out.println("Cliente deletado com sucesso!");
                    }
                } else {
                    System.out.println("O cliente fornecido deve ser desativado primeiro!");
                }
            } else {
                System.out.println("Nenhum cliente encontrado com esse ID.");
            }
        }
    }

    public void exibeClientes(List<Clientes> clie){
        StringBuilder sb = new StringBuilder();

        System.out.println("---Listagem dos clientes cadastrados---");

        // --- Cabeçalho estilizado (usando a mesma lógica de produtos) ---
        sb.append("┌").append("─".repeat(7)).append("┬").append("─".repeat(22)) // ID (7), NOME (22)
                .append("┬").append("─".repeat(27)).append("┬").append("─".repeat(16)) // EMAIL (27), CPF (16)
                .append("┬").append("─".repeat(16)).append("┬").append("─".repeat(26)) // TELEFONE (16), DATA CADASTRO (20)
                .append("┬").append("─".repeat(14)).append("┬").append("─".repeat(10)) // ID_ENDEREÇO (14), STATUS (10)
                .append("┐\n");

        sb.append(String.format("│ %-5s │ %-20s │ %-25s │ %-14s │ %-14s │ %-24s │ %-12s │ %-8s │%n",
                "ID", "NOME", "EMAIL", "CPF", "TELEFONE", "DATA CADASTRO", "ID_ENDEREÇO", "STATUS"));

        sb.append("├").append("─".repeat(7)).append("┼").append("─".repeat(22))
                .append("┼").append("─".repeat(27)).append("┼").append("─".repeat(16))
                .append("┼").append("─".repeat(16)).append("┼").append("─".repeat(26))
                .append("┼").append("─".repeat(14)).append("┼").append("─".repeat(10))
                .append("┤\n");

        System.out.print(sb.toString());

        for(Clientes cli : clie){
            System.out.printf("│ %-5d │ %-20s │ %-25s │ %-14s │ %-14s │ %-24s │ %-12d │ %-8s │%n",
                    cli.getClie_id(),
                    cli.getClie_nome(),
                    cli.getClie_email(),
                    cli.getClie_cpf(),
                    cli.getClie_telefone(),
                    cli.getClie_data_cadastro(),
                    cli.getClie_ende_id(),
                    cli.getClie_status());
        }

        System.out.println("└" + "─".repeat(7) + "┴" + "─".repeat(22) +
                "┴" + "─".repeat(27) + "┴" + "─".repeat(16) +
                "┴" + "─".repeat(16) + "┴" + "─".repeat(26) +
                "┴" + "─".repeat(14) + "┴" + "─".repeat(10) +
                "┘");
    }

    public void getAllClientes() throws SQLException{
        List<Clientes> clientes = this.dao.getAllClie();

        if(!clientes.isEmpty()){
            exibeClientes(clientes);
        }
    }

    public boolean getEndeID(Integer ende_id) throws SQLException{
        EnderecosService enderecoService = new EnderecosService(this.getConnection());
        if(!enderecoService.getEndeID(ende_id)){
            System.out.println("O ID digitado não possui nenhum endereço correspondente!");
            return false;
        }
        return true;
    }

    public boolean getCPF(String cpf) throws SQLException{
        if(this.dao.getClienteCPF(cpf)){
            System.out.println("O CPF inserido já existe no banco de dados!\n");
            return true;
        }else{
            return false;
        }
    }
}
