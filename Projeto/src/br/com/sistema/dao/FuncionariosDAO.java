/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.Funcionarios;
import br.com.sistema.view.AreaTrabalho;
import br.com.sistema.view.FormularioLogin;
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
public class FuncionariosDAO {
    private Connection conn;
    
    ///isso é um metodo construtor. Assim permite que apenas precisemos chamar
    ////as funções quando necessário
    public FuncionariosDAO (){
        this.conn = new ConexaoBanco().pegarConexao();
    }
    
    public void SalvarFuncionario(Funcionarios obj){//salvar os dados de um Funcionario no banco
        try {
            //criar sql
            String sql = "insert into tb_funcionarios (nome, rg, cpf, email, senha, cargo, nivel_acesso, telefone, celular, cep, endereco, numero, complemento, bairro, cidade, estado)"
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //preparar conexao sql para se conectar com o banco
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getRg());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getCargo());
            stmt.setString(7, obj.getNivel());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getCep());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());
            stmt.setString(13, obj.getComplemento());
            stmt.setString(14, obj.getBairro());
            stmt.setString(15, obj.getCidade());
            stmt.setString(16, obj.getEstado());
                //executar sql
            stmt.execute();
                //fechar a conexao
            stmt.close();
           
            JOptionPane.showMessageDialog(null,"Funcionario salvo com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao salvar o Funcionario" + erro);
        }
    }

    public void EditarFuncionario(Funcionarios obj){
        //editar os dados de um Funcionario no banco
        try {
            //criar sql
            String sql = "update tb_funcionarios set nome=?,rg=?,cpf=?,email=?,senha=?,cargo=?,nivel_acesso=?,telefone=?,celular=?,cep=?,endereco=?,"
                    + "numero=?,complemento=?, bairro=?, cidade=?, estado=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getRg());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getCargo());
            stmt.setString(7, obj.getNivel());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getCep());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());
            stmt.setString(13, obj.getComplemento());
            stmt.setString(14, obj.getBairro());
            stmt.setString(15, obj.getCidade());
            stmt.setString(16, obj.getEstado());
            stmt.setInt(17, obj.getId());
                //executar sql
            stmt.execute();
                //fechar a conexao
            stmt.close();
           
            JOptionPane.showMessageDialog(null,"Funcionario editado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao editar o funcionario" + erro);
        }
    }
  
    public Funcionarios BuscarFuncionario (String nome){
        //busca os dados de um Funcionario no banco
        //e coloca eles na tela
        try {
            String sql = "select * from tb_funcionarios  where nome =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            Funcionarios obj = new Funcionarios();
            if (rs.next()){
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel(rs.getString("nivel_acesso"));
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
            JOptionPane.showMessageDialog(null, "erro ao buscar Funcionario"+erro);
        }
        return null;
    }
    
    public void ExcluirFuncionario(Funcionarios obj){
        //Exluir os dados de um funcionario no banco
        try {
            //criar sql e executar
            String sql = "delete from tb_funcionarios  where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null,"Funcionario excluido com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao Funcionario o cliente" + erro);
        }
    }    
    
    
    public List<Funcionarios> ListarFuncionario(){
        //função para listar todos os funcionarios
        //cadastrados no banco para a aba consulta
        List<Funcionarios> lista = new ArrayList<>();
        try {
            String sql = "select * from tb_funcionarios";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Funcionarios obj = new Funcionarios();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel(rs.getString("nivel_acesso"));
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
    
    public List<Funcionarios> FiltrarFuncionario(String nome){
        //função para listar todos os funcionarios
        //cadastrados no banco que tenham esse nome ou parte dele
        List<Funcionarios> lista = new ArrayList<>();
        try {
            String sql = "select * from tb_funcionarios where nome like ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Funcionarios obj = new Funcionarios();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel(rs.getString("nivel_acesso"));
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
    
    public void efetuarLogin(String senha, String nome){
        try {
            String sql = "select * from tb_funcionarios where nome=? and senha=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                AreaTrabalho at = new AreaTrabalho();
                at.usuarioLogado = nome;
                if(rs.getString("nivel_acesso").equals("Usuario")){
                    at.formulario_produtos.setEnabled(false);
                    at.formulario_funcionarios.setEnabled(false);
                    at.formulario_fornecedores.setEnabled(false);
                    at.inventario_produtos.setEnabled(false);
                }else{
                    at.formulario_produtos.setEnabled(true);
                    at.formulario_funcionarios.setEnabled(true);
                    at.formulario_fornecedores.setEnabled(true);
                    at.inventario_produtos.setEnabled(true);
                }
                JOptionPane.showMessageDialog(null, "Seja bem vindo ao sistema\n"+at.usuarioLogado);
                at.setVisible(true); 
            }else{
                FormularioLogin login = new FormularioLogin();
                JOptionPane.showMessageDialog(null, "Usuario ou senha invalidos");
                login.setVisible(true);
            }        
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao acessar o sistema: " + erro);
        }
    }
}
