package domain.repositories.cidades;

import domain.model.entity.Cidades;

import java.sql.SQLException;
import java.util.List;

public interface CidadesDAO {
    void insert (Cidades cidade) throws SQLException;

    boolean update(Cidades cidade, int opcao) throws SQLException;

    boolean delete(Cidades cidade, int opcao) throws SQLException;

    Cidades getCidade(Integer id) throws SQLException;

    List<Cidades> getAllCida();
}
