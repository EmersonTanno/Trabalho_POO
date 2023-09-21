package org.example.excepption;

public class ContaTitularesException extends Exception{
    public ContaTitularesException() {
        super("Cada conta pode ter no máximo dois titulares, com eles possuindo algum vínculo.");
    }
}
