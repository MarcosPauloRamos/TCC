/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.Fornecedores;
import br.com.sistema.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author samsung
 */
public class ProdutosDAO {
    private Connection conn;
    
    ///isso é um metodo construtor. Assim permite que apenas precisemos chamar
    ////as funções quando necessário
    public ProdutosDAO (){
        this.conn = new ConexaoBanco().pegarConexao();
    }
    
    public void SalvarProdutos(Produtos obj){//salvar os dados de um cliente no banco
        try {
            //criar sql
            String sql = "insert into tb_produtos (descricao,preco,qtd_estoque,for_id)"
                    + "values(?,?,?,?)";
            //preparar conexao sql para se conectar com o banco
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQuantidade_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());
                //executar sql
            stmt.execute();
                //fechar a conexao
            stmt.close();
           
            JOptionPane.showMessageDialog(null,"Produto salvo com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao salvar o produto" + erro);
        }
    }

    public void EditarProdutos(Produtos obj){
        //editar os dados de um produtos no banco
        try {
            //criar sql
            String sql = "update tb_produtos set descricao=?,preco=?,qtd_estoque=?,for_id=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQuantidade_estoque());
            stmt.setInt(4, obj.getFornecedor().getId());
            stmt.setInt(5, obj.getId());
                //executar sql
            stmt.execute();
                //fechar a conexao
            stmt.close();
           
            JOptionPane.showMessageDialog(null,"Produto editado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao editar o produto" + erro);
        }
    }
  
    public Produtos BuscarProduto (String nome){
        //busca os dados de um produtos no banco
        //e coloca eles na tela
        try {
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p inner join"
                    + " tb_fornecedores as f on(p.for_id=f.id) where p.descricao=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            Produtos obj = new Produtos();
            Fornecedores f = new Fornecedores();
            if (rs.next()){
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQuantidade_estoque(rs.getInt("p.qtd_estoque"));
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
            }
            return obj;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao buscar produto"+erro);
        }
        return null;
    }

    public Produtos BuscarProdutoCodigo (int codigo){
        //busca os dados de um produtos no banco
        //e coloca eles na tela
        try {
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p inner join"
                    + " tb_fornecedores as f on(p.for_id=f.id) where p.id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            Produtos obj = new Produtos();
            Fornecedores f = new Fornecedores();
            if (rs.next()){
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQuantidade_estoque(rs.getInt("p.qtd_estoque"));
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
            }
            return obj;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao buscar produto"+erro);
        }
        return null;
    }
    
    public void ExcluirProduto(Produtos obj){
        //Exluir os dados de um produtos no banco
        try {
            //criar sql e executar
            String sql = "delete from tb_produtos where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null,"Produto excluido com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao excluir o produto" + erro);
        }
    }    
    
    
    public List<Produtos> ListarProdutos(){
        //função para listar todos os produtos
        //cadastrados no banco para a aba consulta
        List<Produtos> lista = new ArrayList<>();
        try {
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p inner join "
                    + "tb_fornecedores as f on(p.for_id=f.id)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQuantidade_estoque(rs.getInt("p.qtd_estoque"));
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
                lista.add(obj);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao criar a lista"+erro);
        }
        return null;
    }
    
    public List<Produtos> FiltrarProdutos(String nome){
        //função para listar todos os produtos
        //cadastrados no banco que tenham esse nome ou parte dele
        List<Produtos> lista = new ArrayList<>();
        try {
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p inner join "
                    + "tb_fornecedores as f on(p.for_id=f.id) where p.descricao like ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQuantidade_estoque(rs.getInt("p.qtd_estoque"));
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
                lista.add(obj);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao criar a lista"+erro);
        }
        return null;
    }
    
    public void AdicionarEstoque (int id, int qtd_nova){
        //esse metodo usa no inventario para adicionar produtos ao estoque
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null,"Produto adicionado com sucesso ao estoque");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao adicionar o produto ao estoque"+erro);
        }
    }

    public void BaixarEstoque (int id, int qtd_nova){
        //esse metodo usa no inventario para retirar produtos do estoque
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao retirar o produto do estoque"+erro);
        }
    }

    public void AtualizarInventario(int id, int qtd_nova){
        //esse metodo usa no inventario para retirar produtos do estoque
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null,"Produto atualizado com sucesso no estoque");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao atualizar o produto no estoque"+erro);
        }
    }

    public int QuantidadeEstoqueAtual(int id){
        try {
            int q_atual = 0;
            String sql = "select qtd_estoque from tb_produtos where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Produtos obj = new Produtos();
            if (rs.next()){
                obj.setId(rs.getInt("qtd_estoque"));
                q_atual = obj.getId();
            }
            return q_atual;
        } catch (SQLException erro) {
            throw new RuntimeException("erro ao retornar total do estoque" + erro);

        }
    }

}
