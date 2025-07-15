package domain.repositories.clientes;

import domain.model.entity.Clientes;

import java.sql.SQLException;
import java.util.List;

public interface ClientesDAO {
    void insert (Clientes cliente) throws SQLException;

    boolean update(Clientes cliente, int opcao) throws SQLException;

    boolean delete(Clientes cliente, int opcao) throws SQLException;

    Clientes getCliente(Integer id) throws SQLException;

    List<Clientes> getAllClie();

    boolean getClienteCPF(String cpf) throws SQLException;
}
