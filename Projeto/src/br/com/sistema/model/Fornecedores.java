/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.model;

/**
 *
 * @author samsung
 */
public class Fornecedores extends Clientes{
    private String CNPJ;

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }
    
    //fiz isso para lista os forneceodres na tela de produtos
    @Override
    public String toString(){
        return this.getNome();
    }
}
