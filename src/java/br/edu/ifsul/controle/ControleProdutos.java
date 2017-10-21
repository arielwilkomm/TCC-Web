/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.modelo.Produtos;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 201413260217
 */
@Named(value = "controleProdutos")
@SessionScoped
public class ControleProdutos implements Serializable{
    @EJB
    private ProdutoDAO<Produtos> dao;
    private Produtos objeto;
    private Boolean editando;
    
    public ControleProdutos() {
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/produto/listar";
    }
    
    public void novo(){
        editando = true;
        objeto = new Produtos();
    }
    
    public void alterar(Integer id){
        try {
            objeto = dao.getObjectById(id);
            editando = true;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
        
    }
    
    public void excluir(Integer id){
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {
            if(objeto.getId() == null){
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto Persistido com sucesso");
            editando = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }

    public ProdutoDAO<Produtos> getDao() {
        return dao;
    }

    public void setDao(ProdutoDAO<Produtos> dao) {
        this.dao = dao;
    }

    public Produtos getObjeto() {
        return objeto;
    }

    public void setObjeto(Produtos objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }
    
}
