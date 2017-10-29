/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.ContasReceber;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author 201413260217
 */
@Stateless
public class ContasReceberDAO<T> extends DAOGenerico<ContasReceber> implements Serializable{

    public ContasReceberDAO() {
        super();
        super.classePersistente = ContasReceber.class;
    }
    
    
    
}
