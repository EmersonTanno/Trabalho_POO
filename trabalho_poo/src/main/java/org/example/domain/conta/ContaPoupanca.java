package org.example.domain.conta;

import org.example.domain.Empresa;
import org.example.domain.Pessoa;
import org.example.domain.TipoConta;
import org.example.domain.TipoVinculo;
import org.example.excepption.AplicacaoException;
import org.example.excepption.ContaSaldoInicialException;
import org.example.excepption.ContaTitularesException;
import org.example.excepption.PessoaVinculoException;
import org.example.service.AplicacaoService;
import org.example.service.SaldoService;

public class ContaPoupanca extends Conta implements AplicacaoService, SaldoService {

    private Double aplicacao = 0.0;

    public ContaPoupanca(Integer numeroConta, Pessoa titular1, Double saldo) throws ContaTitularesException, ContaSaldoInicialException, AplicacaoException {
        super(numeroConta, titular1);
        setTipoConta(TipoConta.POUPANCA);
        if(saldo < 50.00){
            throw new ContaSaldoInicialException("O saldo inicial mínimo deve ser de ao menos R$50.00");
        }else{
            setSaldo(saldo);
            this.aplicacao = getSaldo();
        }
    }

    public ContaPoupanca(Integer numeroConta, Pessoa titular1, Pessoa titular2, TipoVinculo tipoVinculo, Double saldo) throws ContaTitularesException, PessoaVinculoException, ContaSaldoInicialException, AplicacaoException {
        super(numeroConta, titular1, titular2, tipoVinculo);
        setTipoConta(TipoConta.POUPANCA);
        if(saldo < 50.00){
            throw new ContaSaldoInicialException("O saldo inicial mínimo deve ser de ao menos R$50.00");
        }else{
            setSaldo(saldo);
            this.aplicacao = getSaldo();
        }
    }

    public ContaPoupanca(Integer numeroConta, Empresa empresa, Double saldo) throws ContaSaldoInicialException, AplicacaoException {
        super(numeroConta, empresa, saldo);
        setTipoConta(TipoConta.POUPANCA);
        if(saldo < 50.00){
            throw new ContaSaldoInicialException("O saldo inicial mínimo deve ser de ao menos R$50.00");
        }else{
            setSaldo(saldo);
            this.aplicacao = getSaldo();
        }
    }

    @Override
    public void fazerAplicacao(Double valor) throws AplicacaoException{
        if(valor <= 0){
            throw new AplicacaoException("O valor da Aplicação deve ser maior que R$0.00");
        }


        adicionarExtrato(str.append("Aplicação: +R$").append(valor).append("\n"));
        adicionarSaldo(valor);
        this.aplicacao += valor;
        if(aplicacao > getSaldo()){
            aplicacao = getSaldo();
        }
    }

    @Override
    public void receberAplicacao() {
        if(aplicacao > getSaldo()){
            aplicacao = getSaldo();
        }
        adicionarExtrato(str.append("Lucro aplicação: +R$").append(aplicacao*0.3).append("\n"));
        adicionarSaldo(aplicacao*0.3);
    }

    @Override
    public String verSaldo() {
        StringBuilder saldo = new StringBuilder();

        saldo.append("Saldo Atual: R$").append(getSaldo()).append("\n")
                .append("Rendimento mensal: R$").append(aplicacao*0.3).append("\n")
                .append("Rendimento anual: R$").append((aplicacao*0.3)*12).append(("\n"));

        return saldo.toString();
    }
}