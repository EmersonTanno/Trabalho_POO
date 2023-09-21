package org.example.domain;

import org.example.domain.conta.Conta;
import org.example.domain.conta.ContaPoupanca;
import org.example.excepption.*;
import org.example.service.AcoesBancariasService;

import java.util.ArrayList;

public class Empresa implements AcoesBancariasService {
    private String nomeEmpresa;
    private String cnpj;
    private ArrayList<Conta> contas = new ArrayList<Conta>();

    public Empresa(String nomeEmpresa, String cnpj) {
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public String getListaContas(){
        String lista = "";
        for(int x = 0; x < contas.size(); x++){
            lista += contas.get(x).getNumeroConta() + " " + contas.get(x).getTipoConta().getNomeTipoConta();
            if(x+1 < contas.size()){
                lista += "\n";
            }
        }
        return lista;
    }

    @Override
    public void realizarDeposito(Conta minhaConta, Double valor) throws DepositoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.deposito(valor);
    }

    @Override
    public void realizarPagamento(Conta minhaConta, Double valor, Conta conta) throws PagamentoException, PagamentoSemSaldoException, DepositoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.pagamento(valor, conta);
    }

    @Override
    public String verSaldo(Conta minhaConta) throws NaoTitularException {
        verificaTitular(minhaConta);
        return minhaConta.verSaldo();
    }

    @Override
    public void realizarFinanciamento(Conta minhaConta, Double valor) throws FinanciamentoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.fazerFinanciamento(valor);
    }

    @Override
    public void realizarPagamentoFinanceamento(Conta minhaConta) throws FinanciamentoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.pagarFinanceamento();
    }

    @Override
    public String tirarExtratoDoMes(Conta minhaConta) throws NaoTitularException {
        verificaTitular(minhaConta);
        return minhaConta.tirarExtrato();
    }

    @Override
    public void realizarAplicacao(ContaPoupanca minhaConta, Double valor) throws AplicacaoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.fazerAplicacao(valor);
    }

    @Override
    public void receberAplicacao(ContaPoupanca minhaConta) throws NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.receberAplicacao();
    }

    public void verificaTitular(Conta conta) throws NaoTitularException {
        if(conta.getEmpresa() != this) {
            throw new NaoTitularException();
        }
    }

}
