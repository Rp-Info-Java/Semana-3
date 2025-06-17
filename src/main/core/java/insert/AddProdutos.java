package insert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddProdutos {
    Scanner teclado = new Scanner(System.in);
    String palavra = "";

    public void adicionar(PreparedStatement stmt) throws SQLException {
            System.out.println("\n--Adicionando produto ao banco de dados da loja--");
            System.out.println("Digite o nome do produto: ");
            palavra = teclado.nextLine();
            stmt.setString(1, palavra);

            System.out.println("Digite o preço do produto: ");
            palavra = teclado.nextLine();
            stmt.setDouble(2, Double.parseDouble(palavra));

            System.out.println("Digite a categoria do produto: ");
            palavra = teclado.nextLine();
            stmt.setString(3, palavra);

            System.out.println("Digite o estoque do produto: ");
            palavra = teclado.nextLine();
            stmt.setInt(4, Integer.parseInt(palavra));

            stmt.setString(5, "S");

            stmt.executeUpdate();

            System.out.println("Produto adicionado com sucesso!");
    }
}
