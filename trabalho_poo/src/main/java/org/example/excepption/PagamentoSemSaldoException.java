package org.example.excepption;

public class PagamentoSemSaldoException extends Exception{
    public PagamentoSemSaldoException() {
        super("Você não possui saldo suficiente para concluir a ação.");
    }
}
