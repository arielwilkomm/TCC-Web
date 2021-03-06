/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ContasReceberDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.dao.VendaDAO;
import br.edu.ifsul.dao.VendaItensDAO;
import br.edu.ifsul.modelo.ContasReceber;
import br.edu.ifsul.modelo.Pessoa;
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
    private Venda objeto;
    private Boolean editando;
    @EJB
    private ProdutoDAO<Produtos> daoProdutos;
    @EJB
    private PessoaDAO<Pessoa> daoPessoa;
    @EJB
    private VendaItensDAO<VendaItens> daoVendaItens;
    private Boolean editandoVendaItens;
    private Boolean novoVendaItens;
    private VendaItens vendaItens;
    @EJB
    private ContasReceberDAO<ContasReceber> daoContas;
    private Boolean editandoContas;
    private Boolean novoContas;
    private ContasReceber contas;
    
    
    public ControleVenda() {
        editando = false;
        editandoVendaItens=false;
        editandoContas=false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/venda/listar?faces-redirect=true";
    }
    
    public void novo(){
        editando = true;
        editandoVendaItens=false;
        editandoContas=false;
        objeto = new Venda();
    }
    
    public void alterar(Integer id){
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoVendaItens=false;
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
            editandoVendaItens=false;
            editandoContas=false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void novoVendaItens(){
        editandoVendaItens = true;
        novoVendaItens = true;
        vendaItens = new VendaItens();
    }
    
    public void alterarItens(int index) {
        vendaItens = objeto.getItens().get(index);
        editandoVendaItens = true;
        novoVendaItens = false;
    }
    
    public void excluirVendaItens(Integer id){
        objeto.removerItens(id);
        Util.mensagemInformacao("Item removido com sucesso!");
    }
    
    public void alterarVendaItens(Integer id){
        vendaItens = objeto.getItens().get(id);
        editandoVendaItens = true;
        novoVendaItens = false;
    }
    
    public void salvarVendaItens(){
        if (vendaItens.getId() == null) {
            vendaItens.setValorTotal(vendaItens.getValorUnitario()*vendaItens.getQuantidade());
            objeto.setValor(vendaItens.getValorTotal());
            novoContas();
            contas.setData(objeto.getData());
            contas.setTotal(vendaItens.getValorTotal());
            salvarContas();
            if (novoVendaItens) {
                objeto.adicionarItens(vendaItens);
            }
        }
        editandoVendaItens = false;
        Util.mensagemInformacao("Item persistido com sucesso!");
    }
    
    public void novoContas(){
        editandoContas = true;
        novoContas = true;
        contas = new ContasReceber();
    }
    
    public void alterarContas(int index) {
        contas = objeto.getContasReceber().get(index);
        editandoContas = true;
        novoContas = false;
    }
    
    public void excluirContas(Integer id){
        objeto.removerItens(id);
        Util.mensagemInformacao("Item removido com sucesso!");
    }
    
    public void alterarContas(Integer id){
        contas = objeto.getContasReceber().get(id);
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

    public VendaDAO<Venda> getDao() {
        return dao;
    }

    public void setDao(VendaDAO<Venda> dao) {
        this.dao = dao;
    }

    public Venda getObjeto() {
        return objeto;
    }

    public void setObjeto(Venda objeto) {
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

    public VendaItensDAO<VendaItens> getDaoVendaItens() {
        return daoVendaItens;
    }

    public void setDaoVendaItens(VendaItensDAO<VendaItens> daoVendaItens) {
        this.daoVendaItens = daoVendaItens;
    }

    public Boolean getEditandoVendaItens() {
        return editandoVendaItens;
    }

    public void setEditandoVendaItens(Boolean editandoVendaItens) {
        this.editandoVendaItens = editandoVendaItens;
    }

    public Boolean getNovoVendaItens() {
        return novoVendaItens;
    }

    public void setNovoVendaItens(Boolean novoVendaItens) {
        this.novoVendaItens = novoVendaItens;
    }

    public VendaItens getVendaItens() {
        return vendaItens;
    }

    public void setVendaItens(VendaItens vendaItens) {
        this.vendaItens = vendaItens;
    }

    public ContasReceberDAO<ContasReceber> getDaoContas() {
        return daoContas;
    }

    public void setDaoContas(ContasReceberDAO<ContasReceber> daoContas) {
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

    public ContasReceber getContas() {
        return contas;
    }

    public void setContas(ContasReceber contas) {
        this.contas = contas;
    }
    
    
    
}
