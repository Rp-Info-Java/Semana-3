package application.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServiceBase {
    private Connection connection;

    public ServiceBase(Connection conn){
        this.connection = conn;
    }

//    public Connection getConnection() {
//        try{
//            if(connection == null || connection.isClosed()){
//                System.out.println("Reabrindo conexão...");
//                this.connection = connectionManager();
//            }
//        } catch (SQLException e){
//            System.out.println("Erro ao revalidar/reabrir a conexão: " + e.getMessage());
//            throw new RuntimeException("Falha ao garantir uma conexão válida.", e);
//        }
//        return connection;
//    }

    public static Connection connectionManager () throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/loja";
        String user = "postgres";
        String password = "adm";

        System.out.println("Estabelecendo nova conexão com o banco de dados...");
        return DriverManager.getConnection(url, user, password);
    }

    /*public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }*/
}
