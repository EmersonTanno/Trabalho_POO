package org.example.domain;

import org.example.domain.conta.ContaCorrente;
import org.example.domain.conta.ContaPoupanca;
import org.example.domain.conta.ContaSalario;
import org.example.excepption.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmpresaTest {

    @Test

    void deveCriarUmaEmpresa() {

        Empresa empresa = new Empresa("Empresa", "11.111.111/0001-11");

        assertEquals("Empresa", empresa.getNomeEmpresa());
        assertEquals("11.111.111/0001-11", empresa.getCnpj());
    }

    @Test
    void deveRetornarAsContasRegistradasParaEmpresa() throws ContaSaldoInicialException {

        Empresa empresa = new Empresa("Empresa", "11.111.111/0001-11");
        ContaCorrente conta1 = new ContaCorrente(123, empresa, 50.0);
        ContaSalario conta2 = new ContaSalario(321, empresa, 50.0);

        String resultado = empresa.getListaContas();
        String esperado = "123 Corrente\n321 Salário";

        assertEquals(esperado, resultado);
    }

    //AQUI==========================

    @Test
    void deveRealizarDeposito() throws ContaSaldoInicialException, DepositoException, NaoTitularException {
        Empresa empresa = new Empresa("Empresa", "11.111.111/0001-11");
        ContaCorrente conta = new ContaCorrente(123, empresa, 50.0);

        System.out.println(conta.getListaTitulares());

        empresa.realizarDeposito(conta, 50.0);
        Double resultado = conta.getSaldo();
        Double resultadoEsperado = 100.0;

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void deveRealizarPagamento() throws ContaSaldoInicialException, DepositoException, PagamentoSemSaldoException, PagamentoException, NaoTitularException {
        Empresa empresa1 = new Empresa("Empresa1", "11.111.111/0001-11");
        Empresa empresa2 = new Empresa("Empresa", "11.111.111/0001-11");
        ContaCorrente conta1 = new ContaCorrente(123, empresa1, 100.0);
        ContaCorrente conta2 = new ContaCorrente(321, empresa2, 0.0);

        empresa1.realizarPagamento(conta1, 50.0, conta2);
        Double resultadoEsperado = 50.0;

        assertEquals(resultadoEsperado, conta1.getSaldo());
        assertEquals(resultadoEsperado, conta2.getSaldo());
    }

    @Test
    void deveVerSaldo() throws ContaSaldoInicialException, NaoTitularException {
        Empresa empresa = new Empresa("Empresa", "11.111.111/0001-11");
        ContaCorrente conta = new ContaCorrente(123, empresa, 50.0);

        String resultado = empresa.verSaldo(conta);
        String resultadoEsperado = "Saldo Atual: R$50.0\n";

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void deveRealizarFinanciamento() throws ContaSaldoInicialException, FinanciamentoException, NaoTitularException {
        Empresa empresa = new Empresa("Empresa", "11.111.111/0001-11");
        ContaCorrente conta = new ContaCorrente(123, empresa, 100.0);

        empresa.realizarFinanciamento(conta, 50.0);

        assertEquals(150.0, conta.getSaldo());
        assertEquals(60.0, conta.getValorFinanciamento());

        empresa.realizarPagamentoFinanceamento(conta);

        assertEquals(90.0, conta.getSaldo());
        assertEquals(0.0, conta.getValorFinanciamento());
    }

    @Test
    void deveTirarOExtratoDoMes() throws ContaSaldoInicialException, DepositoException, PagamentoSemSaldoException, PagamentoException, FinanciamentoException, NaoTitularException {
        Empresa empresa1 = new Empresa("Empresa1", "11.111.111/0001-11");
        Empresa empresa2 = new Empresa("Empresa2", "11.111.111/0001-11");
        Empresa empresa3 = new Empresa("Empresa3", "11.111.111/0001-11");
        double saldoInicial = 50.0;
        ContaCorrente conta1 = new ContaCorrente(123, empresa1, saldoInicial);
        ContaCorrente conta2 = new ContaCorrente(321, empresa2, saldoInicial);
        ContaCorrente conta3 = new ContaCorrente(432, empresa3, saldoInicial);
        empresa1.realizarPagamento(conta1, 20.0, conta2);
        empresa1.realizarFinanciamento(conta1, 50.0);
        empresa1.realizarDeposito(conta1, 50.0);
        empresa1.realizarPagamentoFinanceamento(conta1);
        empresa1.realizarPagamento(conta1, 30.0, conta3);

        String resultadoEsperado = "Extrato da Conta: 123 Mês: 9\n" +
                "Tipo de Conta: CORRENTE\n" +
                "Saldo Atual: 40.0\n" +
                "Financiamento a Pagar: 0.0\n" +
                "Titulares:\n" +
                " - Empresa: Empresa1\n" +
                " - CNPJ: 11.111.111/0001-11\n" +
                "\n" +
                "Extrato: \n" +
                "Pagamento: -R$20.0\n" +
                "Financiamento: +R$50.0\n" +
                "Deposito: +R$50.0\n" +
                "Pagou Financiamento: -R$60.0\n" +
                "Pagamento: -R$30.0\n";

        String resultado = empresa1.tirarExtratoDoMes(conta1);

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void deveRealizarAplicacao() throws ContaSaldoInicialException, AplicacaoException, NaoTitularException {
        Empresa empresa = new Empresa("Empresa", "11.111.111/0001-11");
        ContaPoupanca conta = new ContaPoupanca(123, empresa, 100.0);

        empresa.realizarAplicacao(conta, 100.0);

        assertEquals(200.0, conta.getSaldo());

        empresa.receberAplicacao(conta);

        assertEquals(260.0, conta.getSaldo());
    }

    @Test
    void deveConferirOTitular() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException, NaoTitularException {
        Empresa empresa = new Empresa("Empresa", "11.111.111/0001-11");
        Empresa empresa2 = new Empresa("Empresa", "11.111.111/0001-11");
        ContaPoupanca conta = new ContaPoupanca(123, empresa, 100.0);

        assertThrows(NaoTitularException.class, () -> {
            empresa2.realizarFinanciamento(conta, 50.0);
        });
    }

}
