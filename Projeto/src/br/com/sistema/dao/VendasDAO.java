/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.Clientes;
import br.com.sistema.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author samsung
 */
public class VendasDAO {    
    private Connection conn;
    
    ///isso é um metodo construtor. Assim permite que apenas precisemos chamar
    ////as funções quando necessário
    public VendasDAO (){
        this.conn = new ConexaoBanco().pegarConexao();
    }
    
    public void SalvarVenda(Vendas obj){
        //salva uma venda do banco de dados para posterior consulta
        try {
            String sql = "insert into tb_vendas (cliente_id,data_venda,total_venda,observacoes) "
                    + "values(?,?,?,?)";
            //preparar conexao sql para se conectar com o banco
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getClientes().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());
            stmt.setString(4, obj.getObservacoes());
            stmt.execute();
            //fechar a conexao
            stmt.close();
            JOptionPane.showMessageDialog(null,"Venda realizada com sucesso!");
        } catch (SQLException erro) {
            throw new RuntimeException("erro ao salvar a venda" + erro);
        }
    }

    public int RetornaUltimaVenda(){
        //retorna o ID da ultima venda salva
        try {
            int u_id = 0;
            String sql = "select max(id) id from tb_vendas";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Vendas obj = new Vendas();
                obj.setId(rs.getInt("id"));
                u_id = obj.getId();
            }
            return u_id;
        } catch (SQLException erro) {
            throw new RuntimeException("erro ao retornar ultima venda" + erro);
        }
    }
    
    public List<Vendas>HistoricoVendas(LocalDate data_inicio, LocalDate data_fim){
        //função que gera a lista de vendas em uma data para o formulario de 
        //pesquisa de historico de vendas, após digitar a data inicial e final
        try {
            List<Vendas> lista = new ArrayList<>();
            String sql = "select v.id,c.nome,date_format(v.data_venda, '%d/%m/%y')"
                    + "as data_formatada,v.total_venda,v.observacoes"
                    + " from tb_vendas as v inner join "
                    + "tb_clientes as c on(v.cliente_id=c.id) "
                    + "where DATE(v.data_venda) between ? and ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Vendas v = new Vendas();
                Clientes c = new Clientes();
                v.setId(rs.getInt("v.id"));
                c.setNome(rs.getString("c.nome"));
                v.setClientes(c);
                v.setData_venda(rs.getString("data_formatada"));
                v.setTotal_venda(rs.getDouble("v.total_venda"));
                v.setObservacoes(rs.getString("v.observacoes"));
                lista.add(v);
            }
            return lista;
        } catch (SQLException erro) {
            throw new RuntimeException("erro ao criar o historico de vendas" + erro);
        }
    }  
    
    public double VendasdoDia (LocalDate data_venda){
        double total_do_dia = 0;
        try {
            String sql = "select sum(total_venda) as total from tb_vendas where data_venda=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, data_venda.toString());
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                total_do_dia = rs.getDouble("total");
            }
            return total_do_dia;
        } catch (SQLException erro) {
            throw new RuntimeException("erro ao criar o historico de vendas" + erro);
        }

    }
}

