package domain.repositories.produtos;

import domain.model.entity.Produtos;

import java.sql.SQLException;
import java.util.List;

public interface ProdutosDAO{
    void insert (Produtos produto) throws SQLException;

    boolean update(Produtos produto, int opcao) throws SQLException;

    boolean delete(Produtos produto, int opcao) throws SQLException;

    Produtos getProduto(Integer prod_id) throws SQLException;

    List<Produtos> getProdNamePrice() throws SQLException;

    List<Produtos> getAllProd()throws SQLException;

    List<Produtos> getProdLimp()throws SQLException;
    List<Produtos> getProdEstoque()throws SQLException;
    List<Produtos> getProdPriceRange()throws SQLException;
    List<Produtos> getProdGamer()throws SQLException;
    List<Produtos> getProdBarato()throws SQLException;
    Integer getProdArmazem()throws SQLException;
    List<Produtos> getProdMedia()throws SQLException;
}
