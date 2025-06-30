/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.Fornecedores;
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
public class FornecedoresDAO {
    private Connection conn;
    
    ///isso é um metodo construtor. Assim permite que apenas precisemos chamar
    ////as funções quando necessário
    public FornecedoresDAO (){
        this.conn = new ConexaoBanco().pegarConexao();
    }
    
    public void SalvarFornecedores(Fornecedores
            obj){//salvar os dados de um fornecedor no banco
        try {
            //criar sql
            String sql = "insert into tb_fornecedores (nome, cnpj, email, telefone, celular, cep, endereco, numero, complemento, bairro, cidade, estado)"
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?)";
            //preparar conexao sql para se conectar com o banco
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCNPJ());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getCelular());
            stmt.setString(6, obj.getCep());
            stmt.setString(7, obj.getEndereco());
            stmt.setInt(8, obj.getNumero());
            stmt.setString(9, obj.getComplemento());
            stmt.setString(10, obj.getBairro());
            stmt.setString(11, obj.getCidade());
            stmt.setString(12, obj.getEstado());
                //executar sql
            stmt.execute();
                //fechar a conexao
            stmt.close();
           
            JOptionPane.showMessageDialog(null,"Fornecedor salvo com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao salvar o Fornecedor" + erro);
        }
    }

    public void EditarFornecedor(Fornecedores obj){
        //editar os dados de um fornecedor no banco
        try {
            //criar sql
            String sql = "update tb_fornecedores set nome=?,cnpj=?,email=?,telefone=?,celular=?,cep=?,endereco=?,"
                    + "numero=?,complemento=?, bairro=?, cidade=?, estado=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCNPJ());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getCelular());
            stmt.setString(6, obj.getCep());
            stmt.setString(7, obj.getEndereco());
            stmt.setInt(8, obj.getNumero());
            stmt.setString(9, obj.getComplemento());
            stmt.setString(10, obj.getBairro());
            stmt.setString(11, obj.getCidade());
            stmt.setString(12, obj.getEstado());
            stmt.setInt(13, obj.getId());
                //executar sql
            stmt.execute();
                //fechar a conexao
            stmt.close();
           
            JOptionPane.showMessageDialog(null,"Fornecedor editado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"Fornecedor ao editar o cliente" + erro);
        }
    }
  
    public Fornecedores BuscarFornecedor (String nome){
        //busca os dados de um fornecedor no banco
        //e coloca eles na tela
        try {
            String sql = "select * from tb_fornecedores where nome =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            Fornecedores obj = new Fornecedores();
            if (rs.next()){
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setCNPJ(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));
            }
            return obj;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao buscar fornecedor"+erro);
        }
        return null;
    }
    
    public void ExcluirFornecedor(Fornecedores obj){
        //Exluir os dados de um fornecedor no banco
        try {
            //criar sql e executar
            String sql = "delete from tb_fornecedores where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null,"Fornecedor excluido com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao excluir o fornecedor" + erro);
        }
    }    
    
    
    public List<Fornecedores> ListarFornecedor(){
        //função para listar todos os fornecedor
        //cadastrados no banco para a aba consulta
        List<Fornecedores> lista = new ArrayList<>();
        try {
            String sql = "select * from tb_fornecedores";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Fornecedores obj = new Fornecedores();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setCNPJ(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));
                
                lista.add(obj);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao criar a lista"+erro);
        }
        return null;
    }
    
    public List<Fornecedores> FiltrarFornecedor(String nome){
        //função para listar todos os fornecedor
        //cadastrados no banco que tenham esse nome ou parte dele
        List<Fornecedores> lista = new ArrayList<>();
        try {
            String sql = "select * from tb_fornecedores where nome like ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Fornecedores obj = new Fornecedores();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setCNPJ(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));
                
                lista.add(obj);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao criar a lista"+erro);
        }
        return null;
    }
    
    
}
