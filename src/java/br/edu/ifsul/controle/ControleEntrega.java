/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.EntregaDAO;
import br.edu.ifsul.dao.CidadeDAO;
import br.edu.ifsul.modelo.Entrega;
import br.edu.ifsul.modelo.Cidade;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 201413260217
 */
@Named(value = "controleEntrega")
@SessionScoped
public class ControleEntrega implements Serializable{
    @EJB
    private EntregaDAO<Entrega> dao;
    private Entrega objeto;
    private Boolean editando;
    @EJB
    private CidadeDAO<Cidade> CidadeDAO;
    
    public ControleEntrega() {
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/entrega/listar";
    }
    
    public void novo(){
        editando = true;
        objeto = new Entrega();
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

    public EntregaDAO<Entrega> getDao() {
        return dao;
    }

    public void setDao(EntregaDAO<Entrega> dao) {
        this.dao = dao;
    }

    public Entrega getObjeto() {
        return objeto;
    }

    public void setObjeto(Entrega objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public CidadeDAO<Cidade> getCidadeDAO() {
        return CidadeDAO;
    }

    public void setCidadeDAO(CidadeDAO<Cidade> CidadeDAO) {
        this.CidadeDAO = CidadeDAO;
    }
    
}
