package org.example.excepption;

public class PessoaVinculoException extends Exception{
    public PessoaVinculoException() {
        super("Você não pode ter vínculo com você mesmo.");
    }
}
