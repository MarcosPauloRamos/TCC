/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.ItensVenda;
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
public class ItensVendasDAO {
    private Connection conn;
    
    ///isso é um metodo construtor. Assim permite que apenas precisemos chamar
    ////as funções quando necessário
    public ItensVendasDAO (){
        this.conn = new ConexaoBanco().pegarConexao();
    }    
    
    public void SalvarItemVendas(ItensVenda obj){
        try {
            String sql = "insert into tb_itensvendas (venda_id,produto_id,qtd,subtotal) "
                    + "values(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getVendas().getId());
            stmt.setInt(2, obj.getProdutos().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubtotal());
            stmt.execute();
            //fechar a conexao
            stmt.close();
        } catch (SQLException erro) {
            throw new RuntimeException("erro ao salvar os itens do carrinho" + erro);
        }
    }
    public void ExcluirItemVenda(ItensVenda obj){
        try {
            String sql = "delete from tb_itensvendas where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null,"Item excluido com sucesso!");       
        } catch (Exception erro) {
            throw new RuntimeException("erro ao excluir os itens do carrinho" + erro);
        }
    }
    
    public List<ItensVenda> ListaItens(int vendaID){
        //função para listar todos os funcionarios
        //cadastrados no banco que tenham esse nome ou parte dele
        List<ItensVenda> lista = new ArrayList<>();
        try {
            String sql = "select p.id,p.descricao,i.qtd,p.preco,i.subtotal "
                    + "from tb_itensvendas as i inner join tb_produtos p "
                    + "on(i.produto_id = p.id) where i.venda_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vendaID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ItensVenda obj = new ItensVenda();
                Produtos p = new Produtos();
                p.setId(rs.getInt("p.id"));
                obj.setProdutos(p);
                p.setDescricao(rs.getString("p.descricao"));
                obj.setProdutos(p);
                obj.setQtd(rs.getInt("i.qtd"));
                p.setPreco(rs.getDouble("p.preco"));
                obj.setProdutos(p);
                obj.setSubtotal(rs.getDouble("i.subtotal"));
                
                lista.add(obj);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao criar a lista"+erro);
        }
        return null;
    }
}
