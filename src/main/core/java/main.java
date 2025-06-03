import java.sql.*;

public class main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/empresa";
        String user = "postgres";
        String password = "adm";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexão estabelecida com sucesso!");

            String query = "SELECT * FROM usuarios";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("usua_id") + ", Nome: " + rs.getString("usua_nome") + ", Email: " + rs.getString("usua_email"));
                }

                rs.close();
                conn.close();
                stmt.close();

            } catch (SQLException e) {
                System.out.println("Erro ao executar a consulta: " + e.getMessage());

            }

        } catch (SQLException e) {
            System.out.println("Falha na conexão: " + e.getMessage());
        }

    }
}
