package domain.repositories.clientes;

import domain.model.entity.Clientes;
import domain.model.entity.Produtos;
import domain.repositories.DAOImpl;
import shared.FunctionsAux;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAOImpl extends DAOImpl implements ClientesDAO {
    public ClientesDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Clientes cliente) {
        String sql = "INSERT INTO clientes (clie_id, clie_nome, clie_email, clie_cpf, clie_telefone, clie_data_cadastro, clie_ende_id, clie_status) VALUES (?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = this.getConnection().prepareStatement(sql)){
            stmt.setInt(1, FunctionsAux.getSequenceValue(this.getConnection(), "seq_cida_id"));
            stmt.setString(2, cliente.getClie_nome());
            stmt.setString(3, cliente.getClie_email());
            stmt.setString(4, cliente.getClie_cpf());
            stmt.setString(5, cliente.getClie_telefone());
            stmt.setDate(6, cliente.getClie_data_cadastro());
            stmt.setInt(7, cliente.getClie_ende_id());
            stmt.setString(8, "S");
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
    }

    /*--------------------UPDATES---------------------*/

    @Override
    public boolean update(Clientes cliente, int opcao) {

        switch(opcao){
            case 1:
                return auxUpdateEmail(cliente);
            case 2:
                return auxUpdateTelef(cliente);
            case 3:
                return auxUpdateEnde(cliente);
            case 4:
                return auxUpdateStatus(cliente);
            default:
                System.out.println("Opção inválida selecionada!");
        }
        return false;
    }

    public boolean auxUpdateEmail(Clientes cliente){
        String sql = "UPDATE clientes SET clie_email = ? WHERE clie_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)){
            stmt.setString(1, cliente.getClie_email());
            stmt.setInt(2, cliente.getClie_id());
            if(stmt.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    public boolean auxUpdateTelef(Clientes cliente){
        String sql = "UPDATE clientes SET clie_telefone = ? WHERE clie_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)){
            stmt.setString(1, cliente.getClie_telefone());
            stmt.setInt(2, cliente.getClie_id());
            if(stmt.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    public boolean auxUpdateEnde(Clientes cliente){
        String sql = "UPDATE clientes SET clie_ende_id = ? WHERE clie_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)){
            stmt.setInt(1, cliente.getClie_ende_id());
            stmt.setInt(2, cliente.getClie_id());
            if(stmt.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    public boolean auxUpdateStatus(Clientes cliente){
        String sql = "UPDATE clientes SET clie_status = ? WHERE lower(clie_nome) = ? AND clie_id = ?";
        try(PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "S");
            stmt.setString(2, cliente.getClie_nome());
            stmt.setInt(3, cliente.getClie_id());
            if(stmt.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }

    /*------------------------------------------------------*/

    /*--------------------DELETES--------------------------*/
    @Override
    public boolean delete(Clientes cliente, int opcao) {
        int linhasAfetadas = 0;
        switch (opcao){
            case 1:
                return auxDesativarCliente(cliente);
            case 2:
                return auxRemoverCliente(cliente);
            default:
                System.out.println("Opção inválida selecionada!");
                return false;
        }
    }

    public boolean auxDesativarCliente(Clientes cliente){
        String sqlDesativar = "UPDATE clientes SET clie_status = ? WHERE clie_id = ?";

        try(PreparedStatement stmt = this.getConnection().prepareStatement(sqlDesativar)){
            stmt.setString(1, cliente.getClie_status());
            stmt.setInt(2, cliente.getClie_id());
            if(stmt.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }

    public boolean auxRemoverCliente(Clientes cliente){
        String sqlDeletar = "DELETE FROM clientes WHERE clie_id = ?";

        try(PreparedStatement stmt = this.getConnection().prepareStatement(sqlDeletar)){
            stmt.setInt(1, cliente.getClie_id());
            if(stmt.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    /*-------------------------------------------------------------*/

    @Override
    public Clientes getCliente(Integer clie_id) throws SQLException {
        Clientes cliente = null;
        String query = "SELECT * FROM clientes WHERE clie_id = " + clie_id;

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                cliente = this.getCliente(rs);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return cliente;
    }

    private Clientes getCliente(ResultSet rs) throws SQLException {
        String clie_nome = rs.getString("clie_nome");
        String clie_email = rs.getString("clie_email");
        String clie_cpf = rs.getString("clie_cpf");
        String clie_telefone = rs.getString("clie_telefone");
        String clie_status = rs.getString("clie_status");

        clie_nome = FunctionsAux.ajustarTamanho(clie_nome, 20);
        clie_email = FunctionsAux.ajustarTamanho(clie_email, 25);
        clie_cpf = FunctionsAux.ajustarTamanho(clie_cpf, 14);
        clie_telefone = FunctionsAux.ajustarTamanho(clie_telefone, 14);
        clie_status = FunctionsAux.ajustarTamanho(clie_status, 8);

        Clientes cliente = new Clientes();
        cliente.setClie_id(rs.getInt("clie_id"));
        cliente.setClie_nome(clie_nome);
        cliente.setClie_email(clie_email);
        cliente.setClie_cpf(clie_cpf);
        cliente.setClie_telefone(clie_telefone);
        cliente.setClie_data_cadastro(rs.getDate("clie_data_cadastro"));
        cliente.setClie_ende_id(rs.getInt("clie_ende_id"));
        cliente.setClie_status(clie_status);

        return cliente;
    }

    @Override
    public List<Clientes> getAllClie(){
        List<Clientes> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes ORDER BY clie_id";

        try(Statement stmt = this.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            while(rs.next()){
                clientes.add(this.getCliente(rs));
            }
        }catch (SQLException e){
            System.out.println("Erro ao excecutar a consulta: " + e.getMessage());
        }
        return clientes;
    }

    @Override
    public boolean getClienteCPF(String cpf){
        String query = "SELECT * FROM clientes WHERE clie_cpf = '" + cpf + "'";
        try(Statement stmt3 = this.getConnection().createStatement();
            ResultSet rs = stmt3.executeQuery(query)){

            if(rs.next()){
                return true;
            }
        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
}
