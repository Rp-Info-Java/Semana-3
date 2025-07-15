package application.service;

import domain.model.entity.Cidades;
import domain.repositories.cidades.CidadesDAO;
import domain.repositories.cidades.CidadesDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CidadesService extends ServiceBase{
    private CidadesDAO dao;

    public CidadesService(Connection connection){
        super(connection);
        this.dao = new CidadesDAOImpl(connection);
    }

    public void adicionarCidades(Cidades cidade) throws SQLException{
        List<Cidades> cidadesList = this.dao.getAllCida();
        int cidadeIgual = 0;

        if(!cidadesList.isEmpty()){
            for(Cidades cid : cidadesList){
                if(cid.getCida_nome().equals(cidade.getCida_nome())){
                    System.out.println("A cidade informada já existe no banco de dados!\n");
                    cidadeIgual++;
                    break;
                }
            }
            if(cidadeIgual == 0){
                this.dao.insert(cidade);
                System.out.println("Cidade adicionada com sucesso!");
            }
        }
    }

    public void atualizarCidades(Cidades cidade, int opcao) throws SQLException{
        if (opcao == 0){
            System.out.println("Cancelando a atualização!\n");
        }else if (opcao < 0 || opcao > 2){
            System.out.println("A opção digitada é inválida. Digite uma opção válida!");
        }else if (opcao == 1){
            if (this.dao.update(cidade, opcao)){
                System.out.println("Cidade atualizada com sucesso!\n");
            }else{
                System.out.println("Cidade não encontrada!\n");
            }
        }else if(opcao == 2){
            if(this.dao.update(cidade, opcao)){
                System.out.println("Cidade atualizada com sucesso!\n");
            }else{
                System.out.println("Cidade não encontrada!\n");
            }
        }
    }

    public void exibeCidades(List<Cidades> cidades){
        StringBuilder sb = new StringBuilder();

        System.out.println("---Listagem de Cidades---");

        sb.append("┌").append("─".repeat(10)).append("┬") // ID (10)
                .append("─".repeat(27)).append("┬") // NOME (27)
                .append("─".repeat(6)).append("┬") // UF (6)
                .append("─".repeat(10)).append("┐\n"); // STATUS (10)

        sb.append(String.format("│ %-8s │ %-25s │ %-4s │ %-8s │%n",
                "ID", "NOME", "UF", "STATUS"));

        sb.append("├").append("─".repeat(10)).append("┼")
                .append("─".repeat(27)).append("┼")
                .append("─".repeat(6)).append("┼")
                .append("─".repeat(10)).append("┤\n");

        System.out.print(sb.toString());

        for(Cidades cida : cidades){
            System.out.printf("│ %-8d │ %-25s │ %-4s │ %-8s │%n",
                    cida.getCida_id(),
                    cida.getCida_nome(),
                    cida.getCida_uf(),
                    cida.getCida_status()); // Imprime o status da cidade
        }

        System.out.println("└" + "─".repeat(10) + "┴"
                + "─".repeat(27) + "┴"
                + "─".repeat(6) + "┴"
                + "─".repeat(10) + "┘");
    }

    public void getAllCidades() throws SQLException{
        List<Cidades> cidades = this.dao.getAllCida();

        if(!cidades.isEmpty()){
            exibeCidades(cidades);
        }
    }

    public boolean getCidaID(int cida_id) throws SQLException{
        List<Cidades> cidades = this.dao.getAllCida();

        if(!cidades.isEmpty()){
            for(Cidades cid : cidades){
                if(cid.getCida_id() == cida_id){
                    return true;
                }
            }
        }
        return false;
    }

    public void deletarCidades(int opcao, int id) throws SQLException {
        Cidades cida = this.dao.getCidade(id);

        if(opcao == 0){
            System.out.println("Cancelando a operação!\n");
        }else if (opcao < 0 || opcao > 2) {
            System.out.println("A opção digitada é inválida. Digite uma opção válida!");
        } else if (opcao == 1) {
            if (cida != null) {
                if (cida.getCida_status().equals("N")) {
                    System.out.println("A cidade informada já foi desativada.");
                } else {
                    cida.setCida_status("N");
                    if (this.dao.delete(cida, opcao)) {
                        System.out.println("Cidade desativada com sucesso!");
                    }
                }
            } else {
                System.out.println("Nenhuma cidade encontrada com esse ID.");
            }
        } else if (opcao == 2) {
            if (cida != null) {
                if (cida.getCida_status().equals("N")) {
                    if (this.dao.delete(cida, opcao)) {
                        System.out.println("Cidade deletada com sucesso!");
                    }
                } else {
                    System.out.println("A cidade fornecida deve ser desativada primeiro!");
                }
            } else {
                System.out.println("Nenhuma cidade encontrada com esse ID.");
            }
        }
    }
}
