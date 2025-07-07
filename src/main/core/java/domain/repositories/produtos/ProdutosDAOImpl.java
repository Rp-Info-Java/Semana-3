package domain.repositories.produtos;

import domain.model.entity.Produtos;
import domain.repositories.DAOImpl;
import shared.FunctionsAux;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAOImpl extends DAOImpl implements ProdutosDAO{

    public ProdutosDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Produtos produto) throws SQLException{
        String sql = "INSERT INTO produtos (prod_nome, prod_preco, prod_categoria, prod_estoque, prod_status) VALUES (?,?,?,?,?)";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, produto.getProd_nome());
            stmt.setDouble(2, produto.getProd_preco());
            stmt.setString(3, produto.getProd_categoria());
            stmt.setInt(4, produto.getProd_estoque());
            stmt.setString(5, produto.getProd_status());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Produtos produto, int opcao) throws SQLException{
        int linhasAfetadas = 0;
        switch(opcao){
            case 1:
                return auxUpdateCategoria(produto, linhasAfetadas);
            case 2:
                return auxUpdateEstoque(produto, linhasAfetadas);
            case 3:
                return auxUpdatePreco(produto, linhasAfetadas);
            default:
                System.out.println("Opção inválida selecionada!");
                return false;
        }
    }

    @Override
    public boolean delete(Produtos produto, int opcao) throws SQLException{
        int linhasAfetadas = 0;
        switch(opcao){
            case 1:
                return auxDesativarProd(produto, linhasAfetadas);
            case 2:
                return auxRemoverProd(produto, linhasAfetadas);
            default:
                System.out.println("Opção inválida selecionada!");
                return false;
        }
    }

    @Override
    public Produtos getProduto(Integer prod_id) throws SQLException {
        Produtos produto = null;
        String query = "SELECT * FROM produtos WHERE prod_id = " + prod_id;

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                produto = this.getProduto(rs);
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
        return produto;
    }
    private Produtos getProduto(ResultSet rs) throws SQLException {

        String nome = rs.getString("prod_nome");
        String categoria = rs.getString("prod_categoria");
        String status = rs.getString("prod_status"); // Puxa o status do banco de dados

        nome = FunctionsAux.ajustarTamanho(nome, 25);
        categoria = FunctionsAux.ajustarTamanho(categoria, 18);
        status = FunctionsAux.ajustarTamanho(status, 10); // Ajusta o tamanho da string status para 10 caracteres

        Produtos produto = new Produtos();
        produto.setProd_id(rs.getInt("prod_id"));
        produto.setProd_nome(nome);
        produto.setProd_categoria(categoria);
        produto.setProd_status(status);
        produto.setProd_estoque(rs.getInt("prod_estoque"));
        produto.setProd_preco(rs.getDouble("prod_preco"));

        return produto;
    }

    @Override
    public List<Produtos> getAllProd() throws SQLException{
        List<Produtos> prods = new ArrayList<>();
        String query = "SELECT * FROM produtos ORDER BY prod_id";

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                prods.add(this.getProduto(rs));
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());

        }
        return prods;
    }

    @Override
    public List<Produtos> getProdNamePrice() throws SQLException{
        String query2 = "SELECT prod_nome, prod_preco FROM produtos";
        List<Produtos> prods = new ArrayList<>();

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query2)) {

            Produtos produto;

            while (rs.next()) {
                String nome = rs.getString("prod_nome");

                nome = FunctionsAux.ajustarTamanho(nome, 25);

                produto = new Produtos();
                produto.setProd_nome(nome);
                produto.setProd_preco(rs.getDouble("prod_preco"));

                prods.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return prods;
    }

    @Override
    public List<Produtos> getProdLimp() throws SQLException{
        String query3 = "SELECT * FROM produtos WHERE lower(prod_categoria) = 'limpeza' ";
        List<Produtos> prods = new ArrayList<>();

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query3)) {

            while (rs.next()) {
                prods.add(this.getProduto(rs));
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return prods;
    }

    @Override
    public List<Produtos> getProdEstoque() throws SQLException{
        String query4 = "SELECT * FROM produtos WHERE prod_estoque > 10 ORDER BY prod_id";
        List<Produtos> prods = new ArrayList<>();

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query4)) {

            while (rs.next()) {
                prods.add(this.getProduto(rs));
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }

        return prods;
    }
    @Override
    public List<Produtos> getProdPriceRange() throws SQLException{
        String query5 = "SELECT * FROM produtos WHERE prod_preco between 100 and 1000";
        List<Produtos> prods = new ArrayList<>();

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query5)) {

            while (rs.next()) {
                prods.add(this.getProduto(rs));
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return prods;
    }
    @Override
    public List<Produtos> getProdGamer() throws SQLException{
        String query6 = "SELECT * FROM produtos WHERE prod_nome ILIKE '%Gamer%' ";
        List<Produtos> prods = new ArrayList<>();

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query6)) {

            while (rs.next()) {
                prods.add(this.getProduto(rs));
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return prods;
    }
    @Override
    public List<Produtos> getProdBarato() throws SQLException{
        String query7 = "SELECT * FROM produtos ORDER BY prod_preco ASC LIMIT 3";
        List<Produtos> prods = new ArrayList<>();

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query7)) {

            while (rs.next()) {
                prods.add(this.getProduto(rs));
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return prods;
    }
    @Override
    public Integer getProdArmazem() throws SQLException{
        String query8 = "SELECT COUNT(prod_id) as qtde_prods FROM produtos WHERE prod_categoria = 'Armazenamento'";         List<Produtos> prods;
        Integer armazem = 0;

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query8)) {

            while (rs.next()) {
                armazem = rs.getInt("qtde_prods");
            }

        }catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return armazem;
    }
    @Override
    public List<Produtos> getProdMedia() throws SQLException{
        String query9 = "SELECT prod_categoria, ROUND(AVG(prod_preco), 2) as media_preco FROM produtos GROUP BY prod_categoria";
        List<Produtos> prods = new ArrayList<>();

        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query9)) {

            Produtos produto;

            while (rs.next()) {
                String categoria = rs.getString("prod_categoria");

                categoria = FunctionsAux.ajustarTamanho(categoria, 20);

                produto = new Produtos();
                produto.setProd_categoria(categoria);
                produto.setProd_preco(rs.getDouble("media_preco"));

                prods.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return prods;
    }


    /* -------------------UPDATE--------------------------------*/
    public boolean auxUpdateCategoria(Produtos produto, int linhasAfetadas){
        String sql = "UPDATE produtos SET prod_categoria = ? WHERE prod_nome = ? AND prod_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, produto.getProd_categoria());
            stmt.setString(2, produto.getProd_nome());
            stmt.setInt(3, produto.getProd_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
        catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }

    public boolean auxUpdateEstoque(Produtos produto, int linhasAfetadas){
        String sql = "UPDATE produtos SET prod_estoque = ? WHERE prod_nome = ? AND prod_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, produto.getProd_estoque());
            stmt.setString(2, produto.getProd_nome());
            stmt.setInt(3, produto.getProd_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
        catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }

    public boolean auxUpdatePreco(Produtos produto, int linhasAfetadas){
        String sql = "UPDATE produtos SET prod_preco = ? WHERE prod_nome = ? AND prod_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setDouble(1, produto.getProd_preco());
            stmt.setString(2, produto.getProd_nome());
            stmt.setInt(3, produto.getProd_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
        catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    /* -------------------UPDATE--------------------------------*/

    /*--------------------DELETE---------------------------------*/
    public boolean auxDesativarProd(Produtos produto, int linhasAfetadas){
        String sqlDesativar = "UPDATE produtos SET prod_status = ? WHERE prod_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sqlDesativar)) {
            stmt.setString(1, produto.getProd_status());
            stmt.setInt(2, produto.getProd_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
        catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }

    public boolean auxRemoverProd(Produtos produto, int linhasAfetadas){
        String sqlDeletar = "DELETE FROM produtos WHERE prod_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sqlDeletar)) {
            stmt.setInt(1, produto.getProd_id());
            linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
        catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
        return false;
    }
    /*--------------------DELETE---------------------------------*/
}
