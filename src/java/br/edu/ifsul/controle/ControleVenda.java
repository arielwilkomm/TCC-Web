/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.dao.VendaDAO;
import br.edu.ifsul.dao.VendaItensDAO;
import br.edu.ifsul.modelo.Produtos;
import br.edu.ifsul.modelo.Venda;
import br.edu.ifsul.modelo.VendaItens;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 201413260217
 */
@Named(value = "controleVenda")
@SessionScoped
public class ControleVenda implements Serializable{
    @EJB
    private VendaDAO<Venda> dao;
    @EJB
    private VendaItensDAO<Venda> daoItens;
    private Venda objeto;
    private VendaItens objetoItens;
    private Boolean editando;
    private Boolean editandoItens;
    
    public ControleVenda() {
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/venda/listar";
    }
    
    public void novo(){
        editando = true;
        objeto = new Venda();
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
    
    public void novoItem(){
        editando = false;
        editandoItens = true;
        objetoItens = new VendaItens();
    }
    
    public void alterarItens(Integer id){
        try {
            objetoItens = daoItens.getObjectById(id);
            editando = false;
            editandoItens = true;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
        
    }
    
    public void excluirItens(Integer id){
        try {
            objetoItens = daoItens.getObjectById(id);
            daoItens.remove(objetoItens);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void salvarItens(){
        try {
            if(objetoItens.getId() == null){
                daoItens.persist(objetoItens);
            } else {
                daoItens.merge(objetoItens);
            }
            Util.mensagemInformacao("Objeto Persistido com sucesso");
            editandoItens = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }

    public VendaDAO<Venda> getDao() {
        return dao;
    }

    public void setDao(VendaDAO<Venda> dao) {
        this.dao = dao;
    }

    public VendaItensDAO<Venda> getDaoItens() {
        return daoItens;
    }

    public void setDaoItens(VendaItensDAO<Venda> daoItens) {
        this.daoItens = daoItens;
    }

    public Venda getObjeto() {
        return objeto;
    }

    public void setObjeto(Venda objeto) {
        this.objeto = objeto;
    }

    public VendaItens getObjetoItens() {
        return objetoItens;
    }

    public void setObjetoItens(VendaItens objetoItens) {
        this.objetoItens = objetoItens;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public Boolean getEditandoItens() {
        return editandoItens;
    }

    public void setEditandoItens(Boolean editandoItens) {
        this.editandoItens = editandoItens;
    }

}
