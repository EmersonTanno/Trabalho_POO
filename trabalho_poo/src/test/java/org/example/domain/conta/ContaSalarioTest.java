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

public class ContaSalarioTest {

    @Test
    void deveCriarUmaContaSalarioParaUmaPessoaFisica() throws CpfException, ContaTitularesException, ContaSaldoInicialException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario contaSalario = new ContaSalario(123, pessoa, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa));

        assertEquals(pessoas, contaSalario.getTitulares());
        assertEquals("Física", pessoa.getTipoPessoa().getTipoTipoPessoa());
        assertEquals(saldoInicial, contaSalario.getSaldo());
    }

    @Test
    void deveCriarUmaContaSalarioParaUmaPessoaJuridica() throws CpfException, ContaTitularesException, ContaSaldoInicialException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.JURIDICA);
        double saldoInicial = 50.0;
        ContaSalario contaSalario = new ContaSalario(123, pessoa, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa));

        assertEquals(pessoas, contaSalario.getTitulares());
        assertEquals("Jurídica", pessoa.getTipoPessoa().getTipoTipoPessoa());
        assertEquals(saldoInicial, contaSalario.getSaldo());
    }

    @Test
    void deveCriarUmaContaSalarioParaDuasPessoasFisicas() throws CpfException, ContaTitularesException, PessoaVinculoException, ContaSaldoInicialException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario contaSalario = new ContaSalario(123, pessoa1, pessoa2, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa1, pessoa2));
        String vinculo = contaSalario.getTitulares().get(0).getVinculos();

        assertEquals("Pessoa2 Parceiros de Negócio", vinculo);
        assertEquals("Física", contaSalario.getTitulares().get(0).getTipoPessoa().getTipoTipoPessoa());
        assertEquals("Física", contaSalario.getTitulares().get(1).getTipoPessoa().getTipoTipoPessoa());
        assertEquals(pessoas, contaSalario.getTitulares());
        assertEquals(saldoInicial, contaSalario.getSaldo());
    }

    @Test
    void deveCriarUmaContaSalarioParaUmaPessoaFisicaEUmaPessoaJuridica() throws CpfException, ContaTitularesException, PessoaVinculoException, ContaSaldoInicialException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);
        double saldoInicial = 50.0;
        ContaSalario contaSalario = new ContaSalario(123, pessoa1, pessoa2, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa1, pessoa2));
        String vinculo = contaSalario.getTitulares().get(0).getVinculos();

        assertEquals("Pessoa2 Parceiros de Negócio", vinculo);
        assertEquals("Física", contaSalario.getTitulares().get(0).getTipoPessoa().getTipoTipoPessoa());
        assertEquals("Jurídica", contaSalario.getTitulares().get(1).getTipoPessoa().getTipoTipoPessoa());
        assertEquals(pessoas, contaSalario.getTitulares());
        assertEquals(saldoInicial, contaSalario.getSaldo());
    }

    @Test
    void deveCriarUmaContaSalarioParaDuasPessoasJurídicas() throws CpfException, ContaTitularesException, PessoaVinculoException, ContaSaldoInicialException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.JURIDICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.JURIDICA);
        double saldoInicial = 50.0;
        ContaSalario contaSalario = new ContaSalario(123, pessoa1, pessoa2, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);

        List<Pessoa> pessoas = new ArrayList<>(List.of(pessoa1, pessoa2));
        String vinculo = contaSalario.getTitulares().get(0).getVinculos();

        assertEquals("Pessoa2 Parceiros de Negócio", vinculo);
        assertEquals("Jurídica", contaSalario.getTitulares().get(0).getTipoPessoa().getTipoTipoPessoa());
        assertEquals("Jurídica", contaSalario.getTitulares().get(1).getTipoPessoa().getTipoTipoPessoa());
        assertEquals(pessoas, contaSalario.getTitulares());
        assertEquals(saldoInicial, contaSalario.getSaldo());
    }

    @Test
    void deveCriarUmaContaSalarioParaUmaEmpresa() throws ContaSaldoInicialException {
        Empresa empresa = new Empresa("Empresa1", "11.111.111/0001-11");
        double saldoInicial = 50.0;
        ContaSalario contaSalario = new ContaSalario(123, empresa, saldoInicial);


        assertEquals(empresa, contaSalario.getEmpresa());
        assertEquals(saldoInicial, contaSalario.getSaldo());
    }

    @Test
    void naoDevePermitirACriacaoDeContasSalarioComSaldoNegativo() throws CpfException, ContaTitularesException, ContaSaldoInicialException {

        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = -25.0;

        assertThrows(ContaSaldoInicialException.class, () -> {
            ContaSalario contaSalario = new ContaSalario(123, pessoa, saldoInicial);
        });
    }

    @Test
    void deveReceberDepositoComValorMaiorQueZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, DepositoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario conta = new ContaSalario(123, pessoa, saldoInicial);

        conta.deposito(20.0);

        assertEquals(70.0, conta.getSaldo());
    }


    @Test
    void deveRecusarDepositoComValorMenorQueZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, DepositoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario conta = new ContaSalario(123, pessoa, saldoInicial);

        assertThrows(DepositoException.class, () -> {
            conta.deposito(-20.0);
        });
    }

    @Test
    void deveRecusarDepositoComValorIgualAZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, DepositoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario conta = new ContaSalario(123, pessoa, saldoInicial);

        assertThrows(DepositoException.class, () -> {
            conta.deposito(0.0);
        });
    }

    @Test
    void deveRealizarPagamentoComValorMaiorQueZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PagamentoSemSaldoException, PagamentoException, DepositoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario conta1 = new ContaSalario(123, pessoa1, saldoInicial);
        ContaSalario conta2 = new ContaSalario(321, pessoa2, saldoInicial);

        conta1.pagamento(20.0, conta2);

        assertEquals(30.0, conta1.getSaldo());
        assertEquals(70.0, conta2.getSaldo());
    }

    @Test
    void deveRecusarPagamentoComValorMenorQueZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PagamentoSemSaldoException, PagamentoException, DepositoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario conta1 = new ContaSalario(123, pessoa1, saldoInicial);
        ContaSalario conta2 = new ContaSalario(321, pessoa2, saldoInicial);


        assertThrows(PagamentoException.class, () -> {
            conta1.pagamento(-20.0, conta2);
        });
    }

    @Test
    void deveRecusarPagamentoComValorIgualAZero() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PagamentoSemSaldoException, PagamentoException, DepositoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario conta1 = new ContaSalario(123, pessoa1, saldoInicial);
        ContaSalario conta2 = new ContaSalario(321, pessoa2, saldoInicial);


        assertThrows(PagamentoException.class, () -> {
            conta1.pagamento(0.0, conta2);
        });
    }

    @Test
    void deveRecusarPagamentoComValorMaiorQueOSaldo() throws CpfException, ContaTitularesException, ContaSaldoInicialException, PagamentoSemSaldoException, PagamentoException, DepositoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario conta1 = new ContaSalario(123, pessoa1, saldoInicial);
        ContaSalario conta2 = new ContaSalario(321, pessoa2, saldoInicial);


        assertThrows(PagamentoSemSaldoException.class, () -> {
            conta1.pagamento(60.0, conta2);
        });
    }



    @Test
    void deveRealizarOFinanciamento() throws ContaTitularesException, ContaSaldoInicialException, CpfException, FinanciamentoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaSalario conta = new ContaSalario(123, pessoa, 50.0);

        conta.fazerFinanciamento(50.0);

        assertEquals(100.0, conta.getSaldo());
        assertEquals(60.0, conta.getValorFinanciamento());
    }

    @Test
    void deveRealizarOPagamentoDoFinanciamento() throws ContaTitularesException, ContaSaldoInicialException, CpfException, FinanciamentoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaSalario conta = new ContaSalario(123, pessoa, 150.0);

        conta.fazerFinanciamento(50.0);
        conta.pagarFinanceamento();

        assertEquals(0.0, conta.getValorFinanciamento());
        assertEquals(140.0, conta.getSaldo());
    }

    @Test
    void naoDeveRealizarFinanciamentosComValoresMenoresOuIguaisAZero() throws ContaTitularesException, ContaSaldoInicialException, CpfException, FinanciamentoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaSalario conta = new ContaSalario(123, pessoa, 50.0);

        assertThrows(FinanciamentoException.class, () -> {
            conta.fazerFinanciamento(0.0);
        });
    }

    @Test
    void naoDeveRealizarOPagamentoDoFinanciamentosComSaldoMenorQueOValorDoFinanciamento() throws ContaTitularesException, ContaSaldoInicialException, CpfException, FinanciamentoException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaSalario conta = new ContaSalario(123, pessoa, 0.0);

        conta.fazerFinanciamento(50.0);

        assertThrows(FinanciamentoException.class, () -> {
            conta.pagarFinanceamento();
        });
    }


    @Test
    void deveExibirOExtratoDoMesAtual() throws CpfException, ContaTitularesException, ContaSaldoInicialException, DepositoException, PagamentoSemSaldoException, PagamentoException, FinanciamentoException, PessoaVinculoException {
        Pessoa pessoa1 = new Pessoa("Pessoa1", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa2 = new Pessoa("Pessoa2", "111.111.111-11", TipoPessoa.FISICA);
        Pessoa pessoa3 = new Pessoa("Pessoa3", "111.111.111-11", TipoPessoa.FISICA);
        double saldoInicial = 50.0;
        ContaSalario conta1 = new ContaSalario(123, pessoa1, saldoInicial);
        ContaSalario conta2 = new ContaSalario(321, pessoa2, pessoa3, TipoVinculo.PARCEIRO_NEGOCIO, saldoInicial);
        ContaSalario conta3 = new ContaSalario(432, pessoa3, saldoInicial);
        conta1.pagamento(20.0, conta2);
        conta1.fazerFinanciamento(50.0);
        conta1.deposito(50.0);
        conta1.pagarFinanceamento();
        conta1.pagamento(30.0, conta3);

        String resultadoEsperado = "Extrato da Conta: 123 Mês: 9\n" +
                "Tipo de Conta: SALARIO\n" +
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

        String resultado = conta1.tirarExtrato().toString();

        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void deveRetornarOSaldoAtualDaConta() throws ContaTitularesException, ContaSaldoInicialException, CpfException {
        Pessoa pessoa = new Pessoa("Pessoa", "111.111.111-11", TipoPessoa.FISICA);
        ContaSalario conta = new ContaSalario(123, pessoa, 50.0);

        String resultado = conta.verSaldo();
        String resultadoEsperado = "Saldo Atual: R$50.0\n";

        assertEquals(resultadoEsperado, resultado);
    }
}
