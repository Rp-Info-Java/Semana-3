package application.usecase;

import application.service.ProdutosService;
import application.service.ServiceBase;
import domain.model.entity.Produtos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class ProdutosUseCase extends ProdutosService{
    public ProdutosUseCase(Connection connection) {
        super(connection);
    }

    public static void inserirProdutos(Produtos produto){
        Connection connection;
        ProdutosService service;
        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.adicionarProdutos(produto);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void attProdutos(Produtos produto, int opcao){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.atualizarProdutos(produto, opcao);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleProdutos(int opcao, int id){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.deletarProdutos(opcao, id);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarProdutos(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getAllProdutos();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarProdsNamePrice(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getProdutoNamePrice();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void listarProdLimp(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getProdutoLimp();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void listarProdEstoqueMedio(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getProdutoEstoque();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void listarProdPriceRange(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getProdutoPriceRange();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void listarProdGamer(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getProdutoGamer();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void listarProdBarato(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getProdutoBarato();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void listarProdArm(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getProdutoArmazem();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void listarProdMediaPreco(){
        Connection connection;
        ProdutosService service;

        try{
            connection = ServiceBase.connectionManager();
            service = new ProdutosService(connection);
            service.getProdutoMedia();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
