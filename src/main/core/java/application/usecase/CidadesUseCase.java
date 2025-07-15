package application.usecase;

import application.service.CidadesService;
import application.service.ProdutosService;
import application.service.ServiceBase;
import domain.model.entity.Cidades;

import java.sql.Connection;

public class CidadesUseCase extends CidadesService {
    public CidadesUseCase(Connection connection){
        super(connection);
    }

    public static void inserirCidades(Cidades cidade){
        Connection connection;
        CidadesService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new CidadesService(connection);
            service.adicionarCidades(cidade);
            connection.close();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void attCidades(Cidades cidade, int opcao){
        Connection connection;
        CidadesService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new CidadesService(connection);
            service.atualizarCidades(cidade, opcao);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarCidades(){
        Connection connection;
        CidadesService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new CidadesService(connection);
            service.getAllCidades();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleCidades(int opcao, int id){
        Connection connection;
        CidadesService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new CidadesService(connection);
            service.deletarCidades(opcao, id);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
