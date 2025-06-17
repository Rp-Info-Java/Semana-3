package insert;

import java.sql.*;
import java.util.Scanner;

public class ConsultaProdutos {
    String query1 = "SELECT * FROM produtos ORDER BY prod_id"; //Listar todos os prods
    String query2 = "SELECT prod_nome, prod_preco FROM produtos"; //Listar apenas nome e preço dos prods
    String query3 = "SELECT * FROM produtos WHERE lower(prod_categoria) = 'limpeza' "; //Listar os prods da categoria 'Limpeza'
    String query4 = "SELECT * FROM produtos WHERE prod_estoque > 10 ORDER BY prod_id"; //Listar prods estoque > 10
    String query5 = "SELECT * FROM produtos WHERE prod_preco between 100 and 1000"; //Listar prods com preco entre 100 e 1000
    String query6 = "SELECT * FROM produtos WHERE prod_nome ILIKE '%Gamer%' "; //Listar prods com nome que contennha a palavra Gamer
    String query7 = "SELECT * FROM produtos ORDER BY prod_preco ASC LIMIT 3"; //Listar 3 prods mais baratos
    String query8 = "SELECT COUNT(prod_id) as qtde_prods FROM produtos WHERE prod_categoria = 'Armazenamento'"; //Contar quantos prods existem em 'armazenamento'
    String query9 = "SELECT prod_categoria, ROUND(AVG(prod_preco), 2) as media_preco FROM produtos GROUP BY prod_categoria"; //group by e mostrar media preços
    int opcao;
    Scanner teclado = new Scanner(System.in);

    public void consultar(Connection conn) throws SQLException {
        do{
            System.out.println("""
                \n--Opções de consulta no banco de dados da loja--
                1- Listar todos os produtos da loja.
                2- Listar apenas nome e preço dos produtos.
                3- Listar os produtos da categoria 'Limpeza'.
                4- Listar produtos cujo estoque seja maior que 10.
                5- Listar produtos com o preço entre R$ 100,00 e R$ 1000,00.
                6- Listar produtos 'Gamer' disponíveis na loja.
                7- Listar os 3 produtos mais baratos da loja.
                8- Contar quantos produtos existem na categoria de 'armazenamento' da loja.
                9- Mostrar a média de preços dos produtos por categoria.
                0- Cancelar consulta no banco de dados da loja.
                """);
            System.out.println("Digite a opção de consulta a ser realizada (1-9 ou 0 para cancelar a consulta): ");
            opcao = teclado.nextInt();

            if(opcao == 0){
                System.out.println("Cancelando busca.");
            }else if(opcao == 1){
                listAll(conn, query1);
            }else if(opcao == 2){
                listNamePrice(conn, query2);
            }else if(opcao == 3){
                listLimp(conn, query3);
            }else if(opcao == 4){
                listEst(conn, query4);
            }else if(opcao == 5){
                listRange(conn, query5);
            }else if(opcao == 6){
                listGamer(conn, query6);
            }else if(opcao == 7){
                listCheap(conn, query7);
            }else if(opcao == 8){
                listArm(conn, query8);
            }else if(opcao == 9){
                listAvg(conn, query9);
            }else{
                System.out.println("A opção digitada é inválida. Por favor, digite uma opção válida (1-9 ou 0 para cancelar a consulta).\n");
            }
        }while(opcao != 0);
    }
    public static void listAll(Connection conn, String query){
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            StringBuilder sb = new StringBuilder();

            // Cabeçalho estilizado
            sb.append("┌").append("─".repeat(7)).append("┬").append("─".repeat(27))
                    .append("┬").append("─".repeat(14)).append("┬").append("─".repeat(20))
                    .append("┬").append("─".repeat(10))
                    .append("┬").append("─".repeat(12)).append("┐\n"); // Adicionado para a coluna STATUS (12 caracteres)

            sb.append(String.format("│ %-5s │ %-25s │ %-12s │ %-18s │ %-8s │ %-10s │%n", // Ajustado para STATUS
                    "ID", "NOME", "PREÇO", "CATEGORIA", "ESTOQUE", "STATUS")); // Adicionado "STATUS"

            sb.append("├").append("─".repeat(7)).append("┼").append("─".repeat(27))
                    .append("┼").append("─".repeat(14)).append("┼").append("─".repeat(20))
                    .append("┼").append("─".repeat(10))
                    .append("┼").append("─".repeat(12)).append("┤\n"); // Adicionado para a coluna STATUS

            System.out.print(sb.toString());

            while (rs.next()) {
                String nome = rs.getString("prod_nome");
                String categoria = rs.getString("prod_categoria");
                String status = rs.getString("prod_status"); // Puxa o status do banco de dados

                // Ajusta tamanho das strings
                nome = ajustarTamanho(nome, 25);
                categoria = ajustarTamanho(categoria, 18);
                status = ajustarTamanho(status, 10); // Ajusta o tamanho da string status para 10 caracteres

                System.out.printf("│ %-5d │ %-25s │ R$ %9.2f │ %-18s │ %8d │ %10s │%n", // Adicionado para STATUS
                        rs.getInt("prod_id"),
                        nome,
                        rs.getDouble("prod_preco"),
                        categoria,
                        rs.getInt("prod_estoque"),
                        status); // Imprime o status do banco de dados
            }

            // Rodapé da tabela
            System.out.println("└" + "─".repeat(7) + "┴" + "─".repeat(27) +
                    "┴" + "─".repeat(14) + "┴" + "─".repeat(20) +
                    "┴" + "─".repeat(10) +
                    "┴" + "─".repeat(12) + "┘"); // Adicionado para a coluna STATUS

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }


    private static String ajustarTamanho(String str, int tamanho) {
        if (str == null) str = "";
        if (str.length() > tamanho) {
            return str.substring(0, tamanho - 3) + "...";
        }
        return str;
    }

    public static void listNamePrice(Connection conn, String query){
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            StringBuilder sb = new StringBuilder();

            // Cabeçalho da tabela
            sb.append("┌").append("─".repeat(27)).append("┬").append("─".repeat(15)).append("┐\n");
            sb.append(String.format("│ %-25s │ %-12s │%n", "NOME DO PRODUTO", "PREÇO"));
            sb.append("├").append("─".repeat(27)).append("┼").append("─".repeat(15)).append("┤\n");

            System.out.print(sb.toString());

            while (rs.next()) {
                String nome = rs.getString("prod_nome");

                // Ajusta tamanho do nome
                nome = ajustarTamanho(nome, 25);

                System.out.printf("│ %-25s │ R$ %10.2f │%n",
                        nome,
                        rs.getDouble("prod_preco")); // Use getDouble se o campo for numérico
            }

            // Rodapé da tabela
            System.out.println("└" + "─".repeat(27) + "┴" + "─".repeat(15) + "┘");

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
    public static void listLimp(Connection conn, String query){
        try (Statement stmt = conn.createStatement()) {
            listAll(conn, query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
    public static void listEst(Connection conn, String query){
        try (Statement stmt = conn.createStatement()) {
            listAll(conn, query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
    public static void listRange(Connection conn, String query){
        try (Statement stmt = conn.createStatement()) {
            listAll(conn, query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
    public static void listGamer(Connection conn, String query){
        try (Statement stmt = conn.createStatement()) {
            listAll(conn, query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
    public static void listCheap(Connection conn, String query){
        try (Statement stmt = conn.createStatement()) {
            listAll(conn, query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
    public static void listArm(Connection conn, String query){
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Quantidade de produtos na categoria Armazenamento: " + rs.getInt("qtde_prods"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
    public static void listAvg(Connection conn, String query){
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            StringBuilder sb = new StringBuilder();

            // Cabeçalho da tabela
            sb.append("┌").append("─".repeat(22)).append("┬").append("─".repeat(18)).append("┐\n");
            sb.append(String.format("│ %-20s │ %-16s │%n", "CATEGORIA", "MÉDIA PREÇO"));
            sb.append("├").append("─".repeat(22)).append("┼").append("─".repeat(18)).append("┤\n");

            System.out.print(sb.toString());

            while (rs.next()) {
                String categoria = rs.getString("prod_categoria");

                // Ajusta tamanho da categoria
                categoria = ajustarTamanho(categoria, 20);

                System.out.printf("│ %-20s │ R$ %12.2f │%n",
                        categoria,
                        rs.getDouble("media_preco"));
            }

            // Rodapé da tabela
            System.out.println("└" + "─".repeat(22) + "┴" + "─".repeat(18) + "┘");

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
    }
}
