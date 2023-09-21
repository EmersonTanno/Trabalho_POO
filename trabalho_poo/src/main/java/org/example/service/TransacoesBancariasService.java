package org.example.service;

import org.example.domain.conta.Conta;
import org.example.excepption.DepositoException;
import org.example.excepption.PagamentoException;
import org.example.excepption.PagamentoSemSaldoException;

public interface TransacoesBancariasService {

    public void deposito(Double valor) throws DepositoException;

    public void pagamento(Double valor, Conta conta) throws PagamentoException, PagamentoSemSaldoException, DepositoException;

}
