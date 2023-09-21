package org.example.excepption;

public class PagamentoException extends Exception{
    public PagamentoException() {
        super("Você não pode pagar com um valor negativo ou nulo.");
    }
}
