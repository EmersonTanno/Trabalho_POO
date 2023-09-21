package org.example.service;

import org.example.excepption.AplicacaoException;

public interface AplicacaoService {

    public void fazerAplicacao(Double valor) throws AplicacaoException;
    public void receberAplicacao();

}
