package org.example.domain;

import org.example.domain.conta.ContaCorrente;
import org.example.domain.conta.ContaPoupanca;
import org.example.excepption.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PessoaTest {

    @Test
    void deveCriarPessoaFisica() throws CpfException {

        Pessoa pessoa = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);

        assertEquals("Pessoa1", pessoa.getNome());
        assertEquals("111.111.111-11", pessoa.getCpf());
        assertEquals(TipoPessoa.FISICA, pessoa.getTipoPessoa());
    }

    @Test
    void deveCriarPessoaJuridica() throws CpfException {

        Pessoa pessoa = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);

        assertEquals("Pessoa1", pessoa.getNome());
        assertEquals("111.111.111-11", pessoa.getCpf());
        assertEquals(TipoPessoa.JURIDICA, pessoa.getTipoPessoa());
    }

    @Test
    void deveAdicionarVinculosParceiroNegocioEntreDuasPessoas() throws CpfException, PessoaVinculoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);

        pessoa1.adicionarVinculo(pessoa2, TipoVinculo.PARCEIRO_NEGOCIO);

        assertEquals("Pessoa2 Parceiros de Negócio", pessoa1.getVinculos());
        assertEquals("Pessoa1 Parceiros de Negócio", pessoa2.getVinculos());
    }

    @Test
    void deveAdicionarVinculosPaiFilhoEntreDuasPessoas() throws CpfException, PessoaVinculoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);

        pessoa1.adicionarVinculo(pessoa2, TipoVinculo.PAI_FILHO);

        assertEquals("Pessoa2 Pais e Filhos", pessoa1.getVinculos());
        assertEquals("Pessoa1 Pais e Filhos", pessoa2.getVinculos());
    }

    @Test
    void deveAdicionarVinculosUniaoCivilEntreDuasPessoas() throws CpfException, PessoaVinculoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);

        pessoa1.adicionarVinculo(pessoa2, TipoVinculo.UNIAO_CIVIL);

        assertEquals("Pessoa2 União Civil", pessoa1.getVinculos());
        assertEquals("Pessoa1 União Civil", pessoa2.getVinculos());
    }

    @Test
    void deveAdicionarVinculosRepresentacaoLegalEntreDuasPessoas() throws CpfException, PessoaVinculoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);

        pessoa1.adicionarVinculo(pessoa2, TipoVinculo.REPRESENTACAO_LEGAL);

        assertEquals("Pessoa2 Representante Legal", pessoa1.getVinculos());
        assertEquals("Pessoa1 Representante Legal", pessoa2.getVinculos());
    }


    @Test
    void deveRetornarAsContasRegistradasParaOTitular() throws CpfException, ContaTitularesException, ContaSaldoInicialException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        ContaCorrente conta = new ContaCorrente(123, pessoa1, 50.0);

        String resultado = pessoa1.getListaContas();

        assertEquals("123 Corrente", resultado);
    }

    @Test
    void deveRetornarAsContasRegistradasParaOsTitulares() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PessoaVinculoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);
        ContaCorrente conta = new ContaCorrente(123, pessoa1, pessoa2, TipoVinculo.PARCEIRO_NEGOCIO,  50.0);

        String resultado1 = pessoa1.getListaContas();
        String resultado2 = pessoa2.getListaContas();

        assertEquals("123 Corrente", resultado1);
        assertEquals("123 Corrente", resultado2);
    }

    @Test
    void deveRetornarAsDuasContasRegistradasParaOTitular() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        ContaCorrente conta1 = new ContaCorrente(123, pessoa1, 50.0);
        ContaPoupanca conta2 = new ContaPoupanca(321, pessoa1, 50.0);

        String resultado = pessoa1.getListaContas();
        String esperado = "123 Corrente\n321 Poupança";

        assertEquals(esperado, resultado);
    }

    @Test
    void deveRealizarDeposito() throws ContaTitularesException, ContaSaldoInicialException, CpfException, DepositoException, NaoTitularException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaCorrente conta = new ContaCorrente(123, pessoa, 50.0);

        pessoa.realizarDeposito(conta, 50.0);
        Double resultado = conta.getSaldo();
        Double resultadoEsperado = 100.0;

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void deveRealizarPagamento() throws ContaTitularesException, ContaSaldoInicialException, CpfException, DepositoException, PagamentoSemSaldoException, PagamentoException, NaoTitularException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        ContaCorrente conta1 = new ContaCorrente(123, pessoa1, 100.0);
        ContaCorrente conta2 = new ContaCorrente(321, pessoa2, 0.0);

        pessoa1.realizarPagamento(conta1, 50.0, conta2);
        Double resultadoEsperado = 50.0;

        assertEquals(resultadoEsperado, conta1.getSaldo());
        assertEquals(resultadoEsperado, conta2.getSaldo());
    }

    @Test
    void deveVerSaldo() throws CpfException, ContaTitularesException, ContaSaldoInicialException, NaoTitularException {
        Pessoa pessoa = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        ContaCorrente conta = new ContaCorrente(123, pessoa, 100.0);

        String resultado = pessoa.verSaldo(conta);
        String resultadoEsperado = "Saldo Atual: R$100.0\n";

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void deveRealizarFinanciamento() throws CpfException, ContaTitularesException, ContaSaldoInicialException, FinanciamentoException, NaoTitularException {
        Pessoa pessoa = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        ContaCorrente conta = new ContaCorrente(123, pessoa, 100.0);

        pessoa.realizarFinanciamento(conta, 50.0);

        assertEquals(150.0, conta.getSaldo());
        assertEquals(60.0, conta.getValorFinanciamento());

        pessoa.realizarPagamentoFinanceamento(conta);

        assertEquals(90.0, conta.getSaldo());
        assertEquals(0.0, conta.getValorFinanciamento());
    }

    @Test
    void deveTirarOExtratoDoMes() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PessoaVinculoException, DepositoException, PagamentoSemSaldoException, PagamentoException, FinanciamentoException, NaoTitularException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa3 = new Pessoa("Pessoa3", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaCorrente conta1 = new ContaCorrente(123, pessoa1, saldoInicial);
        ContaCorrente conta2 = new ContaCorrente(321, pessoa2, pessoa3, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);
        ContaCorrente conta3 = new ContaCorrente(432, pessoa3, saldoInicial);
        pessoa1.realizarPagamento(conta1, 20.0, conta2);
        pessoa1.realizarFinanciamento(conta1, 50.0);
        pessoa1.realizarDeposito(conta1, 50.0);
        pessoa1.realizarPagamentoFinanceamento(conta1);
        pessoa1.realizarPagamento(conta1, 30.0, conta3);

        String resultadoEsperado = "Extrato da Conta: 123 Mês: 9\n" +
                "Tipo de Conta: CORRENTE\n" +
                "Saldo Atual: 40.0\n" +
                "Financiamento a Pagar: 0.0\n" +
                "Titulares:\n" +
                " - Nome: Pessoa1\n" +
                "   CPF: 111.111.111-11\n" +
                "\n" +
                "Extrato: \n" +
                "Pagamento: -R$20.0\n" +
                "Financiamento: +R$50.0\n" +
                "Deposito: +R$50.0\n" +
                "Pagou Financiamento: -R$60.0\n" +
                "Pagamento: -R$30.0\n";

        String resultado = pessoa1.tirarExtratoDoMes(conta1);

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void deveRealizarAplicacao() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException, NaoTitularException {
        Pessoa pessoa = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 100.0);

        pessoa.realizarAplicacao(conta, 100.0);

        assertEquals(200.0, conta.getSaldo());

        pessoa.receberAplicacao(conta);

        assertEquals(260.0, conta.getSaldo());
    }

    @Test
    void deveConferirOTitular() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException, NaoTitularException {
        Pessoa pessoa = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 100.0);

        assertThrows(NaoTitularException.class, () -> {
            pessoa2.realizarFinanciamento(conta, 50.0);
        });
    }
}
