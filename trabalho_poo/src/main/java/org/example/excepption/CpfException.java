package org.example.excepption;

public class CpfException extends Exception {

    public CpfException() {
        super("O CPF informado não não é válido");
    }

}
