package application.usecase;

import application.service.EnderecosService;
import application.service.ServiceBase;
import domain.model.entity.Enderecos;

import java.sql.Connection;
import java.sql.SQLException;

public class EnderecosUseCase extends EnderecosService {
    public EnderecosUseCase(Connection connection){
        super(connection);
    }
    public static void inserirEnderecos(Enderecos endereco){
        Connection connection;
        EnderecosService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new EnderecosService(connection);
            service.adicionarEnderecos(endereco);
            connection.close();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static void listarEnderecos(){
        Connection connection;
        EnderecosService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new EnderecosService(connection);
            service.getAllEnderecos();
            connection.close();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static boolean controleCidaID(Integer cida_id){
        Connection connection;
        EnderecosService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new EnderecosService(connection);
            connection.close();
            return service.getCidaID(cida_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void tamanhoCEP(String cep) throws SQLException {
        Connection connection = null;
        EnderecosService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new EnderecosService(connection);
            service.cepTamanho(cep);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            connection.close();
        }
    }

    public static void attEnderecos(Enderecos endereco, int opcao){
        Connection connection;
        EnderecosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new EnderecosService(connection);
            service.atualizarEnderecos(endereco, opcao);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleEnderecos(int opcao, int id){
        Connection connection;
        EnderecosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new EnderecosService(connection);
            service.deletarEnderecos(opcao, id);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
