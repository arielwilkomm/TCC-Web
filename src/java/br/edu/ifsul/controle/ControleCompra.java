/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ContasPagarDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.dao.CompraDAO;
import br.edu.ifsul.dao.CompraItensDAO;
import br.edu.ifsul.modelo.ContasPagar;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.modelo.Produtos;
import br.edu.ifsul.modelo.Compra;
import br.edu.ifsul.modelo.CompraItens;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 201413260217
 */
@Named(value = "controleCompra")
@SessionScoped
public class ControleCompra implements Serializable{
    @EJB
    private CompraDAO<Compra> dao;
    private Compra objeto;
    private Boolean editando;
    @EJB
    private ProdutoDAO<Produtos> daoProdutos;
    @EJB
    private PessoaDAO<Pessoa> daoPessoa;
    @EJB
    private CompraItensDAO<CompraItens> daoCompraItens;
    private Boolean editandoCompraItens;
    private Boolean novoCompraItens;
    private CompraItens compraItens;
    @EJB
    private ContasPagarDAO<ContasPagar> daoContas;
    private Boolean editandoContas;
    private Boolean novoContas;
    private ContasPagar contas;
    
    
    public ControleCompra() {
        editando = false;
        editandoCompraItens=false;
        editandoContas=false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/compra/listar?faces-redirect=true";
    }
    
    public void novo(){
        editando = true;
        editandoCompraItens=false;
        editandoContas=false;
        objeto = new Compra();
    }
    
    public void alterar(Integer id){
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoCompraItens=false;
            editandoContas=false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
        
    }
    
    public void excluir(Integer id){
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
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
            editandoCompraItens=false;
            editandoContas=false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void novoCompraItens(){
        editandoCompraItens = true;
        novoCompraItens = true;
        compraItens = new CompraItens();
    }
    
    public void alterarItens(int index) {
        compraItens = objeto.getItens().get(index);
        editandoCompraItens = true;
        novoCompraItens = false;
    }
    
    public void excluirCompraItens(Integer id){
        objeto.removerItens(id);
        Util.mensagemInformacao("Item removido com sucesso!");
    }
    
    public void alterarCompraItens(Integer id){
        compraItens = objeto.getItens().get(id);
        editandoCompraItens = true;
        novoCompraItens = false;
    }
    
    public void salvarCompraItens(){
        if (compraItens.getId() == null) {
            compraItens.setValorTotal(compraItens.getValorUnitario()*compraItens.getQuantidade());
            objeto.setValor(compraItens.getValorTotal());
            novoContas();
            contas.setData(objeto.getData());
            contas.setTotal(compraItens.getValorTotal());
            salvarContas();
            if (novoCompraItens) {
                objeto.adicionarItens(compraItens);
            }
        }
        editandoCompraItens = false;
        Util.mensagemInformacao("Item persistido com sucesso!");
    }
    
    public void novoContas(){
        editandoContas = true;
        novoContas = true;
        contas = new ContasPagar();
    }
    
    public void alterarContas(int index) {
        contas = objeto.getContasPagar().get(index);
        editandoContas = true;
        novoContas = false;
    }
    
    public void excluirContas(Integer id){
        objeto.removerItens(id);
        Util.mensagemInformacao("Item removido com sucesso!");
    }
    
    public void alterarContas(Integer id){
        contas = objeto.getContasPagar().get(id);
        editandoContas = true;
        novoContas = false;
    }
    
    public void salvarContas(){
        if (contas.getId() == null) {
            
            if (novoContas) {
                objeto.adicionarContas(contas);
            }
        }
        editandoContas = false;
        Util.mensagemInformacao("Item persistido com sucesso!");
    }

    public CompraDAO<Compra> getDao() {
        return dao;
    }

    public void setDao(CompraDAO<Compra> dao) {
        this.dao = dao;
    }

    public Compra getObjeto() {
        return objeto;
    }

    public void setObjeto(Compra objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public ProdutoDAO<Produtos> getDaoProdutos() {
        return daoProdutos;
    }

    public void setDaoProdutos(ProdutoDAO<Produtos> daoProdutos) {
        this.daoProdutos = daoProdutos;
    }

    public PessoaDAO<Pessoa> getDaoPessoa() {
        return daoPessoa;
    }

    public void setDaoPessoa(PessoaDAO<Pessoa> daoPessoa) {
        this.daoPessoa = daoPessoa;
    }

    public CompraItensDAO<CompraItens> getDaoCompraItens() {
        return daoCompraItens;
    }

    public void setDaoCompraItens(CompraItensDAO<CompraItens> daoCompraItens) {
        this.daoCompraItens = daoCompraItens;
    }

    public Boolean getEditandoCompraItens() {
        return editandoCompraItens;
    }

    public void setEditandoCompraItens(Boolean editandoCompraItens) {
        this.editandoCompraItens = editandoCompraItens;
    }

    public Boolean getNovoCompraItens() {
        return novoCompraItens;
    }

    public void setNovoCompraItens(Boolean novoCompraItens) {
        this.novoCompraItens = novoCompraItens;
    }

    public CompraItens getCompraItens() {
        return compraItens;
    }

    public void setCompraItens(CompraItens compraItens) {
        this.compraItens = compraItens;
    }

    public ContasPagarDAO<ContasPagar> getDaoContas() {
        return daoContas;
    }

    public void setDaoContas(ContasPagarDAO<ContasPagar> daoContas) {
        this.daoContas = daoContas;
    }

    public Boolean getEditandoContas() {
        return editandoContas;
    }

    public void setEditandoContas(Boolean editandoContas) {
        this.editandoContas = editandoContas;
    }

    public Boolean getNovoContas() {
        return novoContas;
    }

    public void setNovoContas(Boolean novoContas) {
        this.novoContas = novoContas;
    }

    public ContasPagar getContas() {
        return contas;
    }

    public void setContas(ContasPagar contas) {
        this.contas = contas;
    }
    
    
    
}
