/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Pessoa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author 201413260217
 */
@Stateless
public class PessoaDAO<T> extends DAOGenerico<Pessoa> implements Serializable{

    public PessoaDAO() {
        super();
        super.classePersistente = Pessoa.class;
    }
    
    @Override
    public Pessoa getObjectById(Integer id) throws Exception {
        Pessoa obj = (Pessoa) super.getEm().find(super.getClassePersistente(), id);
        obj.getTelefones().size();
        return obj;
    } 
    
    
}
