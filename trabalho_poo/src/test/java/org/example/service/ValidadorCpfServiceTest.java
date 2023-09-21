package org.example.service;

import org.example.domain.Pessoa;
import org.example.domain.TipoPessoa;
import org.example.domain.conta.ContaPoupanca;
import org.example.excepption.ContaSaldoInicialException;
import org.example.excepption.CpfException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidadorCpfServiceTest {

    @Test
    void deveValidarSeOCpfTemOTamanhoCorreto() {

        assertThrows(CpfException.class, () -> {
            Pessoa pessoa = new Pessoa("Pessoa", "111.111.111", TipoPessoa.FISICA);
        });

    }

    @Test
    void deveValidarSeOCpfEValido() {

        assertThrows(CpfException.class, () -> {
            Pessoa pessoa = new Pessoa("Pessoa", "085.777.159-05", TipoPessoa.FISICA);
        });

    }
}
