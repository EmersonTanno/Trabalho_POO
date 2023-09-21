package org.example.domain.conta;

import org.example.domain.Empresa;
import org.example.domain.Pessoa;
import org.example.domain.TipoPessoa;
import org.example.domain.TipoVinculo;
import org.example.excepption.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContaPoupancaTest {

    @Test
    void deveCriarUmaContaPoupancaParaUmaPessoaFisica() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca contaPoupanca = new ContaPoupanca(123, pessoa, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa));

        assertEquals(pessoas, contaPoupanca.getTitulares());
        assertEquals("Física", contaPoupanca.getTitulares().get(0).getTipoPessoa().getTipoTipoPessoa());
        assertEquals(saldoInicial, contaPoupanca.getSaldo());
    }

    @Test
    void deveCriarUmaContaPoupancaParaUmaPessoaJurídica() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.JURIDICA);
        double saldoInicial = 50.0;
        ContaPoupanca contaPoupanca = new ContaPoupanca(123, pessoa, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa));

        assertEquals(pessoas, contaPoupanca.getTitulares());
        assertEquals("Jurídica", contaPoupanca.getTitulares().get(0).getTipoPessoa().getTipoTipoPessoa());
        assertEquals(saldoInicial, contaPoupanca.getSaldo());
    }

    @Test
    void deveCriarUmaContaPoupancaParaDuasPessoasFisicas() throws CpfException, ContaTitularesException, PessoaVinculoException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca contaPoupanca = new ContaPoupanca(123, pessoa1, pessoa2, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa1, pessoa2));
        String vinculo = contaPoupanca.getTitulares().get(0).getVinculos();

        assertEquals("Pessoa2 Parceiros de Negócio", vinculo);
        assertEquals("Física", contaPoupanca.getTitulares().get(0).getTipoPessoa().getTipoTipoPessoa());
        assertEquals("Física", contaPoupanca.getTitulares().get(1).getTipoPessoa().getTipoTipoPessoa());
        assertEquals(pessoas, contaPoupanca.getTitulares());
        assertEquals(saldoInicial, contaPoupanca.getSaldo());
    }

    @Test
    void deveCriarUmaContaPoupancaParaDuasPessoasJuridicas() throws CpfException, ContaTitularesException, PessoaVinculoException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);
        double saldoInicial = 50.0;
        ContaPoupanca contaPoupanca = new ContaPoupanca(123, pessoa1, pessoa2, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa1, pessoa2));
        String vinculo = contaPoupanca.getTitulares().get(0).getVinculos();

        assertEquals("Pessoa2 Parceiros de Negócio", vinculo);
        assertEquals("Jurídica", contaPoupanca.getTitulares().get(0).getTipoPessoa().getTipoTipoPessoa());
        assertEquals("Jurídica", contaPoupanca.getTitulares().get(1).getTipoPessoa().getTipoTipoPessoa());
        assertEquals(pessoas, contaPoupanca.getTitulares());
        assertEquals(saldoInicial, contaPoupanca.getSaldo());
    }

    @Test
    void deveCriarUmaContaPoupancaParaUmaPessoasFisicaEUmaPessoaJuridica() throws CpfException, ContaTitularesException, PessoaVinculoException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);
        double saldoInicial = 50.0;
        ContaPoupanca contaPoupanca = new ContaPoupanca(123, pessoa1, pessoa2, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa1, pessoa2));
        String vinculo = contaPoupanca.getTitulares().get(0).getVinculos();

        assertEquals("Pessoa2 Parceiros de Negócio", vinculo);
        assertEquals("Física", contaPoupanca.getTitulares().get(0).getTipoPessoa().getTipoTipoPessoa());
        assertEquals("Jurídica", contaPoupanca.getTitulares().get(1).getTipoPessoa().getTipoTipoPessoa());
        assertEquals(pessoas, contaPoupanca.getTitulares());
        assertEquals(saldoInicial, contaPoupanca.getSaldo());
    }

    @Test
    void deveCriarUmaContaPoupancaParaUmaEmpresa() throws ContaSaldoInicialException, AplicacaoException {
        Empresa empresa = new Empresa("Empresa1", "11.111.111/0001-11");
        double saldoInicial = 50.0;
        ContaPoupanca contaPoupanca = new ContaPoupanca(123, empresa, saldoInicial);


        assertEquals(empresa, contaPoupanca.getEmpresa());
        assertEquals(saldoInicial, contaPoupanca.getSaldo());
    }

    @Test
    void naoDevePermitirACriacaoDeContasPoupancaComSaldoMenorQueCinquanta() throws CpfException{

        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 25.0;

        assertThrows(ContaSaldoInicialException.class, () -> {
            ContaPoupanca contaPoupanca = new ContaPoupanca(123, pessoa, saldoInicial);
        });
    }

    @Test
    void naoDevePermitirACriacaoDeContasPoupancaComSaldoNegativo() throws CpfException{

        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = -25.0;

        assertThrows(ContaSaldoInicialException.class, () -> {
            ContaPoupanca contaPoupanca = new ContaPoupanca(123, pessoa, saldoInicial);
        });
    }


    @Test
    void deveReceberDepositoComValorMaiorQueZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, DepositoException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, saldoInicial);

        conta.deposito(20.0);

        assertEquals(70.0, conta.getSaldo());
    }


    @Test
    void deveRecusarDepositoComValorMenorQueZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, DepositoException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, saldoInicial);

        assertThrows(DepositoException.class, () -> {
            conta.deposito(-20.0);
        });
    }

    @Test
    void deveRecusarDepositoComValorIgualAZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, DepositoException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, saldoInicial);

        assertThrows(DepositoException.class, () -> {
            conta.deposito(0.0);
        });
    }

    @Test
    void deveRealizarPagamentoComValorMaiorQueZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PagamentoSemSaldoException, PagamentoException, DepositoException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca conta1 = new ContaPoupanca(123, pessoa1, saldoInicial);
        ContaPoupanca conta2 = new ContaPoupanca(321, pessoa2, saldoInicial);

        conta1.pagamento(20.0, conta2);

        assertEquals(30.0, conta1.getSaldo());
        assertEquals(70.0, conta2.getSaldo());
    }

    @Test
    void deveRecusarPagamentoComValorMenorQueZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PagamentoSemSaldoException, PagamentoException, DepositoException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca conta1 = new ContaPoupanca(123, pessoa1, saldoInicial);
        ContaPoupanca conta2 = new ContaPoupanca(321, pessoa2, saldoInicial);


        assertThrows(PagamentoException.class, () -> {
            conta1.pagamento(-20.0, conta2);
        });
    }

    @Test
    void deveRecusarPagamentoComValorIgualAZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PagamentoSemSaldoException, PagamentoException, DepositoException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca conta1 = new ContaPoupanca(123, pessoa1, saldoInicial);
        ContaPoupanca conta2 = new ContaPoupanca(321, pessoa2, saldoInicial);


        assertThrows(PagamentoException.class, () -> {
            conta1.pagamento(0.0, conta2);
        });
    }

    @Test
    void deveRecusarPagamentoComValorMaiorQueOSaldo() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PagamentoSemSaldoException, PagamentoException, DepositoException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca conta1 = new ContaPoupanca(123, pessoa1, saldoInicial);
        ContaPoupanca conta2 = new ContaPoupanca(321, pessoa2, saldoInicial);


        assertThrows(PagamentoSemSaldoException.class, () -> {
            conta1.pagamento(60.0, conta2);
        });
    }


    @Test
    void deveRealizarOFinanciamento() throws ContaTitularesException, ContaSaldoInicialException, CpfException, FinanciamentoException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 50.0);

        conta.fazerFinanciamento(50.0);

        assertEquals(100.0, conta.getSaldo());
        assertEquals(60.0, conta.getValorFinanciamento());
    }

    @Test
    void deveRealizarOPagamentoDoFinanciamento() throws ContaTitularesException, ContaSaldoInicialException, CpfException, FinanciamentoException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 150.0);

        conta.fazerFinanciamento(50.0);
        conta.pagarFinanceamento();

        assertEquals(0.0, conta.getValorFinanciamento());
        assertEquals(140.0, conta.getSaldo());
    }

    @Test
    void naoDeveRealizarFinanciamentosComValoresMenoresOuIguaisAZero() throws ContaTitularesException, ContaSaldoInicialException, CpfException, FinanciamentoException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 50.0);

        assertThrows(FinanciamentoException.class, () -> {
            conta.fazerFinanciamento(0.0);
        });
    }

    @Test
    void naoDeveRealizarOPagamentoDoFinanciamentosComSaldoMenorQueOValorDoFinanciamento() throws ContaTitularesException, ContaSaldoInicialException, CpfException, FinanciamentoException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 50.0);

        conta.fazerFinanciamento(1050.0);

        assertThrows(FinanciamentoException.class, () -> {
            conta.pagarFinanceamento();
        });
    }

    @Test
    void deveExibirOExtratoDoMesAtual() throws CpfException, ContaTitularesException, ContaSaldoInicialException, DepositoException, PagamentoSemSaldoException, PagamentoException, FinanciamentoException, PessoaVinculoException, AplicacaoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa3 = new Pessoa("Pessoa3", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaPoupanca conta1 = new ContaPoupanca(123, pessoa1, saldoInicial);
        ContaPoupanca conta2 = new ContaPoupanca(321, pessoa2, pessoa3, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);
        ContaCorrente conta3 = new ContaCorrente(432, pessoa3, saldoInicial);
        conta1.pagamento(20.0, conta2);
        conta1.fazerFinanciamento(50.0);
        conta1.deposito(50.0);
        conta1.pagarFinanceamento();
        conta1.pagamento(30.0, conta3);
        conta1.receberAplicacao();

        String resultadoEsperado = "Extrato da Conta: 123 Mês: 9\n" +
                "Tipo de Conta: POUPANCA\n" +
                "Saldo Atual: 52.0\n" +
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
                "Pagamento: -R$30.0\n" +
                "Lucro aplicação: +R$12.0\n";

        String resultado = conta1.tirarExtrato().toString();

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void deveRealizarUmaAplicacao() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 50.0);
        conta.fazerAplicacao(50.0);

        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void deveReceberOValorDeUmaAplicacao() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 50.0);
        conta.fazerAplicacao(50.0);
        conta.receberAplicacao();

        assertEquals(130.0, conta.getSaldo());
    }

    @Test
    void naoDeveReceberUmValorMenorOuIgualAZeroParaAplicacao() throws CpfException, ContaTitularesException, ContaSaldoInicialException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 50.0);

        assertThrows(AplicacaoException.class, () -> {
            conta.fazerAplicacao(0.00);
        });
    }

    @Test
    void deveRetornarOSaldoAtualDaConta() throws ContaTitularesException, ContaSaldoInicialException, CpfException, AplicacaoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaPoupanca conta = new ContaPoupanca(123, pessoa, 50.0);

        String resultado = conta.verSaldo();
        String resultadoEsperado = "Saldo Atual: R$50.0\nRendimento mensal: R$15.0\nRendimento anual: R$180.0\n";

        assertEquals(resultadoEsperado, resultado);
    }


}
