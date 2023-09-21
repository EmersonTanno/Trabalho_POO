package org.example.domain.conta;

import org.example.domain.Empresa;
import org.example.domain.Pessoa;
import org.example.domain.TipoConta;
import org.example.domain.TipoVinculo;
import org.example.excepption.ContaSaldoInicialException;
import org.example.excepption.ContaTitularesException;
import org.example.excepption.PessoaVinculoException;
import org.example.service.SaldoService;

public class ContaCorrente extends Conta implements SaldoService {

    public ContaCorrente(Integer numeroConta, Pessoa titular1, Double saldo) throws ContaTitularesException, ContaSaldoInicialException {
        super(numeroConta, titular1);
        setTipoConta(TipoConta.CORRENTE);
        if(saldo < 0.00){
            throw new ContaSaldoInicialException("O saldo inicial não pode ser negativo");
        }else{
            setSaldo(saldo);
        }
    }

    public ContaCorrente(Integer numeroConta, Pessoa titular1, Pessoa titular2, TipoVinculo tipoVinculo, Double saldo) throws ContaTitularesException, PessoaVinculoException, ContaSaldoInicialException {
        super(numeroConta, titular1, titular2, tipoVinculo);
        setTipoConta(TipoConta.CORRENTE);
        if(saldo < 0.00){
            throw new ContaSaldoInicialException("O saldo inicial não pode ser negativo");
        }else{
            setSaldo(saldo);
        }

    }

    public ContaCorrente(Integer numeroConta, Empresa empresa, Double saldo) throws ContaSaldoInicialException {
        super(numeroConta, empresa, saldo);
        setTipoConta(TipoConta.CORRENTE);
        if(saldo < 0.00){
            throw new ContaSaldoInicialException("O saldo inicial não pode ser negativo");
        }else{
            setSaldo(saldo);
        }
    }

    @Override
    public String verSaldo() {
        StringBuilder saldo = new StringBuilder();

        saldo.append("Saldo Atual: R$").append(getSaldo()).append("\n");

        return saldo.toString();
    }
}
