package shared;

import domain.model.entity.Produtos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//Funcao para conseguir incrementar o valor do ID SEQUENCE de uma tabela em bando de dados
public class FunctionsAux {
    public static Integer getSequenceValue(Connection conn, String nameSequence){
        String sql = "SELECT nextval('"+ nameSequence + "')";
        int sequence = 0;

        try(Statement stmt2 = conn.createStatement()){
            ResultSet rs = stmt2.executeQuery(sql);
            if(rs.next()){
                sequence = (int) rs.getLong(1);
            }
            stmt2.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return sequence;
    }

    public static String ajustarTamanho(String str, int tamanho) {
        if (str == null) str = "";
        if (str.length() > tamanho) {
            return str.substring(0, tamanho - 3) + "...";
        }
        return str;
    }

    public static List<Produtos> getResultSet(Connection conn, String query) {
        List<Produtos> produtos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int idProd = rs.getInt("prod_id");
                String nomeProd = rs.getString("prod_nome");
                String statusProd = rs.getString("prod_status");

                Produtos produto = new Produtos(idProd, nomeProd, statusProd);
                produtos.add(produto);
            }
            return produtos;

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return produtos;
    }
}
