package application.service;

import domain.model.entity.Cidades;
import domain.model.entity.Enderecos;
import domain.repositories.enderecos.EnderecosDAO;
import domain.repositories.enderecos.EnderecosDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EnderecosService extends ServiceBase {
    private EnderecosDAO dao;

    public EnderecosService(Connection connection) {
        super(connection);
        this.dao = new EnderecosDAOImpl(connection);
    }

    public void exibeEnderecos(List<Enderecos> enderecos) {
        StringBuilder sb = new StringBuilder();

        System.out.println("---Listagem dos endereços existentes---");

        // --- Cabeçalho estilizado ---
        sb.append("┌").append("─".repeat(7)).append("┬") // ID (7)
                .append("─".repeat(22)).append("┬") // CEP (22)
                .append("─".repeat(27)).append("┬") // RUA (27)
                .append("─".repeat(10)).append("┬") // NÚMERO (10)
                .append("─".repeat(16)).append("┬") // COMPLEMENTO (16)
                .append("─".repeat(20)).append("┬") // BAIRRO (20)
                .append("─".repeat(13)).append("┬") // ID_CIDADE (13)
                .append("─".repeat(10)).append("┐\n"); // STATUS (10)

        sb.append(String.format("│ %-5s │ %-20s │ %-25s │ %-8s │ %-14s │ %-18s │ %-11s │ %-8s │%n",
                "ID", "CEP", "RUA", "NÚMERO", "COMPLEMENTO", "BAIRRO", "ID_CIDADE", "STATUS"));

        sb.append("├").append("─".repeat(7)).append("┼")
                .append("─".repeat(22)).append("┼")
                .append("─".repeat(27)).append("┼")
                .append("─".repeat(10)).append("┼")
                .append("─".repeat(16)).append("┼")
                .append("─".repeat(20)).append("┼")
                .append("─".repeat(13)).append("┼")
                .append("─".repeat(10)).append("┤\n");

        System.out.print(sb.toString());

        for (Enderecos end : enderecos) {
            System.out.printf("│ %-5d │ %-20s │ %-25s │ %-8d │ %-14s │ %-18s │ %-11d │ %-8s │%n",
                    end.getEnde_id(),
                    end.getEnde_cep(),
                    end.getEnde_rua(),
                    end.getEnde_numero(),
                    end.getEnde_complemento(),
                    end.getEnde_bairro(),
                    end.getEnde_cida_id(),
                    end.getEnde_status());
        }

        System.out.println("└" + "─".repeat(7) + "┴" + "─".repeat(22) +
                "┴" + "─".repeat(27) + "┴" + "─".repeat(10) +
                "┴" + "─".repeat(16) + "┴" + "─".repeat(20) +
                "┴" + "─".repeat(13) + "┴" + "─".repeat(10) +
                "┘");
    }

    public void getAllEnderecos() {
        List<Enderecos> enderecos = this.dao.getAllEnde();

        if (!enderecos.isEmpty()) {
            exibeEnderecos(enderecos);
        }
    }

    public boolean getEndeID(int ende_id) throws SQLException {
        Enderecos enderecos = this.dao.getEndereco(ende_id);

        if (enderecos != null && enderecos.getEnde_id() > 0) {
            return true;
        }
        return false;
    }

    public boolean getCidaID(Integer cida_id) throws SQLException {
        CidadesService cidadeService = new CidadesService(this.getConnection());
        if (!cidadeService.getCidaID(cida_id)) {
            System.out.println("O ID digitado não possui nenhuma cidade correspondente!");
            return false;
        } else {
            return true;
        }
    }

    public void cepTamanho (String cep){
        if(cep.length() != 8){
            System.out.println("O CEP inserido não é válido por não possuir 8 caracteres. Digite corretamente!\n");
        }
    }

    public void adicionarEnderecos(Enderecos endereco) throws SQLException {
        List<Enderecos> enderecosList = this.dao.getAllEnde();
        int cepIgual = 0;

        if (!enderecosList.isEmpty()) {
            for (Enderecos end : enderecosList) {
                if (end.getEnde_cep().equals(endereco.getEnde_cep())) {
                    System.out.println("Não podem haver dois endereços com o mesmo CEP!\n");
                    cepIgual++;
                    break;
                }
            }
            if (cepIgual == 0) {
                this.dao.insert(endereco);
                System.out.println("Endereço adicionado com sucesso!");
            }
        }
    }

    public void atualizarEnderecos(Enderecos endereco, int opcao) throws SQLException {
        Enderecos end = this.dao.getEndereco(endereco.getEnde_id());
        if (opcao == 0) {
            System.out.println("Cancelando a atualização!\n");
        } else if (opcao < 0 || opcao > 5) {
            System.out.println("A opção digitada é inválida. Digite uma opção válida!");
        } else if (opcao == 1) {
            if (this.dao.update(endereco, opcao)) {
                System.out.println("Endereço atualizado com sucesso!\n");
            } else {
                System.out.println("Endereço não encontrado!\n");
            }
        } else if (opcao == 2) {
            if (this.dao.update(endereco, opcao)) {
                System.out.println("Endereço atualizado com sucesso!\n");
            } else {
                System.out.println("Endereço não encontrado");
            }
        } else if (opcao == 3) {
            if (this.dao.update(endereco, opcao)) {
                System.out.println("Endereço atualizado com sucesso!\n");
            } else {
                System.out.println("Endereço não encontrado");
            }
        } else if (opcao == 4) {
            if (this.dao.update(endereco, opcao)) {
                System.out.println("Endereço atualizado com sucesso!\n");
            } else {
                System.out.println("Endereço não encontrado");
            }
        } else if (opcao == 5){
            if(end != null && end.getEnde_status().equals("S")){
                System.out.println("O endereço informado já está ativo!\n");
            }else{
                if (this.dao.update(endereco, opcao)) {
                    System.out.println("Endereço reativado com sucesso!\n");
                } else {
                    System.out.println("Endereço não encontrado!\n");
                }
            }
        }
    }

    public void deletarEnderecos(int opcao, int id) throws SQLException {
        Enderecos endereco = this.dao.getEndereco(id);

        if (opcao == 0) {
            System.out.println("Cancelando operação!\n");
        } else if (opcao < 0 || opcao > 2) {
            System.out.println("A opção digitada é inválida. Digite uma opção válida!");
        } else if (opcao == 1) {
            if (endereco != null) {
                if (endereco.getEnde_status().equals("N")) {
                    System.out.println("O endereço informado já foi desativado.");
                } else {
                    endereco.setEnde_status("N");
                    if (this.dao.delete(endereco, opcao)) {
                        System.out.println("Endereço desativado com sucesso!");
                    }
                }
            } else {
                System.out.println("Nenhum endereço encontrado com esse ID.");
            }
        } else if (opcao == 2) {
            if (endereco != null) {
                if (endereco.getEnde_status().equals("N")) {
                    if (this.dao.delete(endereco, opcao)) {
                        System.out.println("Endereço deletado com sucesso!");
                    }
                } else {
                    System.out.println("O endereço fornecido deve ser desativado primeiro!");
                }
            } else {
                System.out.println("Nenhum endereço encontrado com esse ID.");
            }
        }
    }
}
