package domain.repositories;

import java.sql.Connection;

public class DAOImpl {
    private Connection connection;

    public DAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
