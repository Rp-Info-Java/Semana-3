package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
