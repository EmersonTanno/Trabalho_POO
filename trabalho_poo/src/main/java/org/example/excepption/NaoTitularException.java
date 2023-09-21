package org.example.excepption;

public class NaoTitularException extends Exception{
    public NaoTitularException() {
        super("Você precisa ser o titular da conta pra poder realizar ações dentro dela.");
    }
}
