package domain.repositories.enderecos;

import domain.model.entity.Enderecos;

import java.sql.SQLException;
import java.util.List;

public interface EnderecosDAO {
    void insert(Enderecos endereco) throws SQLException;
    boolean update(Enderecos cidade, int opcao) throws SQLException;
    boolean delete (Enderecos cidade, int opcao) throws SQLException;
    Enderecos getEndereco(Integer ende_id) throws SQLException;
    List<Enderecos> getAllEnde();
}
