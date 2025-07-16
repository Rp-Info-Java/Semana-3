package domain.repositories.cidades;

import domain.model.entity.Cidades;
import domain.model.entity.Produtos;
import domain.repositories.DAOImpl;
import shared.FunctionsAux;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CidadesDAOImpl extends DAOImpl implements CidadesDAO {
    public CidadesDAOImpl (Connection connection){
        super(connection);
    }

    @Override
    public void insert(Cidades cidade){
        String sql = "INSERT INTO cidades (cida_id, cida_nome, cida_uf,cida_status) VALUES (?,?,?,?)";

        try(PreparedStatement stmt = this.getConnection().prepareStatement(sql)){
            stmt.setInt(1, FunctionsAux.getSequenceValue(this.getConnection(), "seq_cida_id"));
            stmt.setString(2, cidade.getCida_nome());
            stmt.setString(3, cidade.getCida_uf().toUpperCase());
            stmt.setString(4, cidade.getCida_status());
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Cidades cidade, int opcao){
        switch(opcao){
            case 1:
                return auxUpdateNome(cidade);
            case 2:
                return auxUpdateUF(cidade);
            case 3:
                return auxUpdateStatus(cidade);
            default:
                System.out.println("Opção inválida selecionada!");
                return false;
        }
    }

    @Override
    public boolean delete(Cidades cidade, int opcao){
        switch (opcao){
            case 1:
                return auxDesativarCida(cidade);
            case 2:
                return auxRemoverCida(cidade);
            default:
                System.out.println("Opção inválida selecionada!");
                return false;
        }
    }

    @Override
    public Cidades getCidade(Integer cida_id){
        Cidades cidade = null;
        String query = "SELECT * FROM cidades WHERE cida_id = " + cida_id;

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                cidade = this.getCidade(rs);
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return cidade;
    }

    private Cidades getCidade(ResultSet rs) throws SQLException{
        String cida_nome = rs.getString("cida_nome");
        String cida_uf = rs.getString("cida_uf");
        String cida_status = rs.getString("cida_status");

        cida_nome = FunctionsAux.ajustarTamanho(cida_nome, 25);
        cida_uf = FunctionsAux.ajustarTamanho(cida_uf, 4);
        cida_status = FunctionsAux.ajustarTamanho(cida_status, 8);

        Cidades cidade = new Cidades();
        cidade.setCida_id(rs.getInt("cida_id"));
        cidade.setCida_nome(cida_nome);
        cidade.setCida_uf(cida_uf.toUpperCase());
        cidade.setCida_status(cida_status);

        return cidade;
    }

    @Override
    public List<Cidades> getAllCida(){
        List<Cidades> cidades = new ArrayList<>();
        String query = "SELECT * FROM cidades ORDER BY cida_id";

        try(Statement stmt = this.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            while(rs.next()){
                cidades.add(this.getCidade(rs));
            }
        }catch (SQLException e){
            System.out.println("Erro ao excecutar a consulta: " + e.getMessage());
        }
        return cidades;
    }

    /*---------------------UPDATES-------------------*/
    public boolean auxUpdateNome(Cidades cidade){
        String sql = "UPDATE cidades SET cida_nome = ? WHERE cida_id = ?";

        try(PreparedStatement stmt = this.getConnection().prepareStatement(sql)){
            stmt.setString(1, cidade.getCida_nome());
            stmt.setInt(2, cidade.getCida_id());
             if(stmt.executeUpdate() > 0){
                 return true;
             }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());;
        }
        return false;
    }

    public boolean auxUpdateUF(Cidades cidade){
        String sql = "UPDATE cidades SET cida_UF = ? WHERE cida_id = ?";

        try(PreparedStatement stmt = this.getConnection().prepareStatement(sql)){
            stmt.setString(1, cidade.getCida_uf().toUpperCase());
            stmt.setInt(2, cidade.getCida_id());
             if(stmt.executeUpdate() > 0){
                 return true;
             }

        }catch(SQLException e){
            System.out.println("Erro ao executar a consulta:  " + e.getMessage());
        }
        return false;
    }

    public boolean auxUpdateStatus(Cidades cidade){
        String sql = "UPDATE cidades SET cida_status = ? WHERE lower(cida_nome) = ? AND cida_id = ?";
        try(PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "S");
            stmt.setString(2, cidade.getCida_nome());
            stmt.setInt(3, cidade.getCida_id());
            if(stmt.executeUpdate() > 0){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    /*---------------------------------------------------*/

    /*-------------------DELETES------------------------------------*/
    public boolean auxDesativarCida(Cidades cidade){
        String sqlDesativar = "UPDATE cidades SET cida_status = ? WHERE cida_id = ?";

        try(PreparedStatement stmt = this.getConnection().prepareStatement(sqlDesativar)){
            stmt.setString(1, cidade.getCida_status());
            stmt.setInt(2, cidade.getCida_id());
             if(stmt.executeUpdate() > 0){
                 return true;
             }

        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    public boolean auxRemoverCida(Cidades cidade){
        String sqlDeletar = "DELETE FROM cidades WHERE cida_id = ?";

        try(PreparedStatement stmt = this.getConnection().prepareStatement(sqlDeletar)){
            stmt.setInt(1, cidade.getCida_id());
             if(stmt.executeUpdate() > 0){
                 return true;
             }

        }catch (SQLException e){
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    /*---------------------------------------------------*/
}
