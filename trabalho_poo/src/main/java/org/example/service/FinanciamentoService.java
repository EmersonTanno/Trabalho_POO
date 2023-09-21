package org.example.service;

import org.example.excepption.FinanciamentoException;

public interface FinanciamentoService {

    public void fazerFinanciamento(Double valor) throws FinanciamentoException;

    public void pagarFinanceamento() throws FinanciamentoException;

}
