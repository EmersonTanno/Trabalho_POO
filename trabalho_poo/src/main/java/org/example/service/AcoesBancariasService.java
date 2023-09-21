package org.example.service;

import org.example.domain.conta.Conta;
import org.example.domain.conta.ContaPoupanca;
import org.example.excepption.*;

public interface AcoesBancariasService {

    public void realizarDeposito(Conta minhaConta, Double valor) throws DepositoException, NaoTitularException;

    public void realizarPagamento(Conta mihaConta, Double valor, Conta conta) throws PagamentoException, PagamentoSemSaldoException, DepositoException, NaoTitularException;

    public String verSaldo(Conta minhaConta) throws NaoTitularException;

    public void realizarFinanciamento(Conta minhaConta, Double valor) throws FinanciamentoException, NaoTitularException;

    public void realizarPagamentoFinanceamento(Conta minhaConta) throws FinanciamentoException, NaoTitularException;

    public String tirarExtratoDoMes(Conta minhaConta) throws NaoTitularException;

    public void realizarAplicacao(ContaPoupanca minhaConta, Double valor) throws AplicacaoException, NaoTitularException;

    public void receberAplicacao(ContaPoupanca minhaConta) throws NaoTitularException;
}
