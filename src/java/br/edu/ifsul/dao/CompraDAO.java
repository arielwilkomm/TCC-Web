/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Compra;
import br.edu.ifsul.modelo.Venda;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author 201413260217
 */
@Stateless
public class CompraDAO<T> extends DAOGenerico<Compra> implements Serializable{

    public CompraDAO() {
        super();
        super.classePersistente = Compra.class;
    }
    
    @Override
    public Compra getObjectById(Integer id) throws Exception {
        Compra obj = (Compra) super.getEm().find(super.getClassePersistente(), id);
        obj.getContasPagar().size();
        obj.getItens().size();
        return obj;
    } 
    
}
