/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CidadeDAO;
import br.edu.ifsul.dao.ContasReceberDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.dao.TelefoneDAO;
import br.edu.ifsul.modelo.Cidade;
import br.edu.ifsul.modelo.ContasReceber;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.modelo.Produtos;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.modelo.Telefone;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 201413260217
 */
@Named(value = "controlePessoa")
@SessionScoped
public class ControlePessoa implements Serializable {

    @EJB
    private PessoaDAO<Pessoa> dao;
    private Pessoa objeto;
    private Boolean editando;
    @EJB
    private TelefoneDAO<Telefone> daoTelefone;
    @EJB
    private CidadeDAO<Cidade> daoCidade;
    private Boolean editandoTelefone;
    private Boolean novoTelefone;
    private Telefone telefone;
    private String senhaC;

    public ControlePessoa() {
        editando = false;
        editandoTelefone = false;
    }

    public String listar() {
        editando = false;
        return "/privado/pessoa/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        editandoTelefone = false;
        objeto = new Pessoa();
    }

    public void alterar(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;
            editandoTelefone = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }

    }

    public void excluir(Integer id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
    }

    public void salvar() {
        try {
            if (objeto.getId() == null) {
                if (objeto.getSenha().equals(senhaC)) {
                    dao.persist(objeto);
                    this.senhaC = "";
                    Util.mensagemInformacao("Objeto Persistido com sucesso");
                    editando = false;
                    editandoTelefone = false;
                } else {
                    Util.mensagemErro("Senhas Incompativeis");
                    this.senhaC = "";
                }
            } else {
                if (objeto.getSenha().equals(senhaC)) {
                    dao.merge(objeto);
                    this.senhaC = "";
                    Util.mensagemInformacao("Objeto Persistido com sucesso");
                    editando = false;
                    editandoTelefone = false;
                } else {
                    Util.mensagemErro("Senhas Incompativeis");
                    this.senhaC = "";
                }
            }
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }

    public void novoTelefone() {
        editandoTelefone = true;
        novoTelefone = true;
        telefone = new Telefone();
    }

    public void alterarItens(int index) {
        telefone = objeto.getTelefones().get(index);
        editandoTelefone = true;
        novoTelefone = false;
    }

    public void excluirTelefone(Integer id) {
        objeto.removerTelefones(id);
        Util.mensagemInformacao("Telefone removido com sucesso!");
    }

    public void alterarTelefone(Integer id) {
        telefone = objeto.getTelefones().get(id);
        editandoTelefone = true;
        novoTelefone = false;
    }

    public void salvarTelefone() {
        if (telefone.getId() == null) {

            if (novoTelefone) {
                objeto.adicionarTelefones(telefone);
            }
        }
        editandoTelefone = false;
        Util.mensagemInformacao("Telefone persistido com sucesso!");
    }

    public void setDao(PessoaDAO<Pessoa> dao) {
        this.dao = dao;
    }

    public Pessoa getObjeto() {
        return objeto;
    }

    public void setObjeto(Pessoa objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public TelefoneDAO<Telefone> getDaoTelefone() {
        return daoTelefone;
    }

    public void setDaoTelefone(TelefoneDAO<Telefone> daoTelefone) {
        this.daoTelefone = daoTelefone;
    }

    public Boolean getEditandoTelefone() {
        return editandoTelefone;
    }

    public void setEditandoTelefone(Boolean editandoTelefone) {
        this.editandoTelefone = editandoTelefone;
    }

    public Boolean getNovoTelefone() {
        return novoTelefone;
    }

    public void setNovoTelefone(Boolean novoTelefone) {
        this.novoTelefone = novoTelefone;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public PessoaDAO<Pessoa> getDao() {
        return dao;
    }

    public CidadeDAO<Cidade> getDaoCidade() {
        return daoCidade;
    }

    public void setDaoCidade(CidadeDAO<Cidade> daoCidade) {
        this.daoCidade = daoCidade;
    }

    public String getSenhaC() {
        return senhaC;
    }

    public void setSenhaC(String senhaC) {
        this.senhaC = senhaC;
    }

}
