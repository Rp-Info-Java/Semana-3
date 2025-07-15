package domain.repositories.enderecos;

import domain.model.entity.Cidades;
import domain.model.entity.Enderecos;
import domain.model.entity.Produtos;
import domain.repositories.DAOImpl;
import shared.FunctionsAux;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecosDAOImpl extends DAOImpl implements EnderecosDAO {
    public EnderecosDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Enderecos endereco) {
        String sql = "INSERT INTO enderecos (ende_id, ende_cep, ende_rua, ende_numero, ende_complemento, ende_bairro, ende_cida_id, ende_status) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, FunctionsAux.getSequenceValue(this.getConnection(), "seq_ende_id"));
            stmt.setString(2, endereco.getEnde_cep());
            stmt.setString(3, endereco.getEnde_rua());
            stmt.setInt(4, endereco.getEnde_numero());
            stmt.setString(5, endereco.getEnde_complemento());
            stmt.setString(6, endereco.getEnde_bairro());
            stmt.setInt(7, endereco.getEnde_cida_id());
            stmt.setString(8, "S");
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
    }

    /*--------------------UPDATES ----------------------------------*/
    @Override
    public boolean update(Enderecos endereco, int opcao) throws SQLException {
        int linhasAfetadas = 0;
        switch(opcao){
            case 1:
                return auxUpdateRua(endereco, linhasAfetadas);
            case 2:
                return auxUpdateNumero(endereco, linhasAfetadas);
            case 3:
                return auxUpdateComple(endereco, linhasAfetadas);
            case 4:
                return auxUpdateBairro(endereco, linhasAfetadas);
            default:
                System.out.println("Opção inválida selecionada");
        }
        return false;
    }

    public boolean auxUpdateRua(Enderecos endereco, int linhasAfetadas){
        String sql = "UPDATE enderecos SET ende_rua = ? WHERE ende_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, endereco.getEnde_rua());
            stmt.setInt(2, endereco.getEnde_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    public boolean auxUpdateNumero(Enderecos endereco, int linhasAfetadas){
        String sql = "UPDATE enderecos SET ende_numero = ? WHERE ende_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, endereco.getEnde_numero());
            stmt.setInt(2, endereco.getEnde_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    public boolean auxUpdateComple(Enderecos endereco, int linhasAfetadas){
        String sql = "UPDATE enderecos SET ende_complemento = ? WHERE ende_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, endereco.getEnde_complemento());
            stmt.setInt(2, endereco.getEnde_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    public boolean auxUpdateBairro(Enderecos endereco, int linhasAfetadas){
        String sql = "UPDATE enderecos SET ende_bairro = ? WHERE ende_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, endereco.getEnde_bairro());
            stmt.setInt(2, endereco.getEnde_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }

    /*-------------------------------------------------------------------*/
    /*-------------------------DELETE------------------------------*/
    @Override
    public boolean delete(Enderecos endereco, int opcao) throws SQLException {
        int linhasAfetadas = 0;
        switch (opcao) {
            case 1:
                return auxDesativarEnde(endereco, linhasAfetadas);
            case 2:
                return auxRemoverEnde(endereco, linhasAfetadas);
            default:
                System.out.println("Opção inválida selecionada!");
                return false;
        }
    }

    public boolean auxDesativarEnde(Enderecos endereco, int linhasAfetadas){
        String sqlDesativar = "UPDATE enderecos SET ende_status = ? WHERE ende_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sqlDesativar)) {
            stmt.setString(1, endereco.getEnde_status());
            stmt.setInt(2, endereco.getEnde_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
        catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }

    public boolean auxRemoverEnde(Enderecos endereco, int linhasAfetadas){
        String sqlDeletar = "DELETE FROM enderecos WHERE ende_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sqlDeletar)) {
            stmt.setInt(1, endereco.getEnde_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
        catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    /*-------------------------------------------------------------------*/

    @Override
    public Enderecos getEndereco(Integer ende_id) {
        Enderecos endereco = null;
        String query = "SELECT * FROM enderecos WHERE ende_id = " + ende_id;

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                endereco = this.getEndereco(rs);
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }

        return endereco;
    }

    public Enderecos getEndereco(ResultSet rs) throws SQLException {
        String rua = rs.getString("ende_rua");
        String bairro = rs.getString("ende_bairro");
        String complemento = rs.getString("ende_complemento"); // Obtém o complemento
        String ende_status = rs.getString("ende_status"); // Puxa o status do banco de dados

        rua = FunctionsAux.ajustarTamanho(rua, 25);
        bairro = FunctionsAux.ajustarTamanho(bairro, 18);
        complemento = FunctionsAux.ajustarTamanho(complemento, 14);
        ende_status = FunctionsAux.ajustarTamanho(ende_status, 8);

        Enderecos endereco = new Enderecos();
        endereco.setEnde_id(rs.getInt("ende_id"));
        endereco.setEnde_cep(rs.getString("ende_cep"));
        endereco.setEnde_rua(rua);
        endereco.setEnde_numero(rs.getInt("ende_numero"));
        endereco.setEnde_complemento(complemento);
        endereco.setEnde_bairro(bairro);
        endereco.setEnde_cida_id(rs.getInt("ende_cida_id"));
        endereco.setEnde_status(ende_status);

        return endereco;
    }

    @Override
    public List<Enderecos> getAllEnde() {
        List<Enderecos> enderecos = new ArrayList<>();
        String query = "SELECT * FROM enderecos ORDER BY ende_id";

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                enderecos.add(this.getEndereco(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excecutar a consulta: " + e.getMessage());
        }
        return enderecos;
    }
}
