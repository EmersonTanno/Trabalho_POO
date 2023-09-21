package org.example.excepption;

public class DepositoException extends Exception{
    public DepositoException() {
        super("Não é possível depositar valores negativos ou nulos.");
    }
}
