package application.usecase;

import application.service.ClientesService;
import application.service.ServiceBase;
import domain.model.entity.Clientes;

import java.sql.Connection;
import java.sql.SQLException;

public class ClientesUseCase extends ClientesService {
    public ClientesUseCase(Connection connection){
        super(connection);
    }

    public static void inserirClientes(Clientes cliente){
        Connection connection;
        ClientesService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new ClientesService(connection);
            service.adicionarClientes(cliente);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void attClientes(Clientes cliente, int opcao){
        Connection connection;
        ClientesService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ClientesService(connection);
            service.atualizarClientes(cliente, opcao);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleClientes(int opcao, int id){
        Connection connection;
        ClientesService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ClientesService(connection);
            service.deletarClientes(opcao, id);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean controleEndeID(Integer ende_id) throws SQLException {
        Connection connection = null;
        ClientesService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new ClientesService(connection);
            return service.getEndeID(ende_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            connection.close();
        }
    }

    public static boolean controleCPF(String cpf) throws SQLException {
        Connection connection = null;
        ClientesService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new ClientesService(connection);
            return service.getCPF(cpf);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            connection.close();
        }
    }

    public static void listarClientes(){
        Connection connection;
        ClientesService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ClientesService(connection);
            service.getAllClientes();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
