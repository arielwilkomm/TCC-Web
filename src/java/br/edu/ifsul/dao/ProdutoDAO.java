/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Produtos;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author 201413260217
 */
@Stateless
public class ProdutoDAO<T> extends DAOGenerico<Produtos> implements Serializable{

    public ProdutoDAO() {
        super();
        super.classePersistente = Produtos.class;
    }
    
    
    
}
