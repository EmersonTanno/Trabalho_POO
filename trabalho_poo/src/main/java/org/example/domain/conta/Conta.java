package org.example.domain.conta;

import org.example.domain.Empresa;
import org.example.domain.Pessoa;
import org.example.domain.TipoConta;
import org.example.domain.TipoVinculo;
import org.example.excepption.*;
import org.example.service.ExtratoService;
import org.example.service.FinanciamentoService;
import org.example.service.SaldoService;
import org.example.service.TransacoesBancariasService;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Conta implements TransacoesBancariasService, ExtratoService, FinanciamentoService, SaldoService {

    private Integer NumeroConta;
    private TipoConta tipoConta;
    private Double saldo;
    private Empresa empresa;
    private ArrayList<Pessoa> titulares = new ArrayList<Pessoa>();
    private Double valorFinanciamento = 0.0;
    private StringBuilder extrato = new StringBuilder();
    private Integer mes = LocalDate.now().getMonthValue();
    public StringBuilder str = new StringBuilder();

    //caso de um titular
    public Conta(Integer numeroConta, Pessoa titular1) throws ContaTitularesException {
        this.NumeroConta = numeroConta;
        adicionarTitular(titular1);
    }

    //caso de dois titulares
    public Conta(Integer numeroConta, Pessoa titular1, Pessoa titular2, TipoVinculo tipoVinculo) throws ContaTitularesException, PessoaVinculoException {
        titular1.adicionarVinculo(titular2, tipoVinculo);
        this.NumeroConta = numeroConta;
        adicionarDoisTitulares(titular1, titular2);
    }

    //conta empresarial
    public Conta(Integer numeroConta,Empresa empresa, Double saldo) {
        this.NumeroConta = numeroConta;
        this.empresa = empresa;
        this.saldo = saldo;
        empresa.adicionarConta(this);
    }

    public Integer getNumeroConta() {
        return NumeroConta;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public ArrayList<Pessoa> getTitulares() {
        return titulares;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Double getValorFinanciamento() {
        return valorFinanciamento;
    }

    public String getListaTitulares(){
        String lista = "";
        for(int x = 0; x < titulares.size(); x++){
            lista += "Titular: " + titulares.get(x).getNome() + " CPF: " + titulares.get(x).getCpf() + "\n";
        }
        return  lista;
    }

    public void adicionarSaldo(Double valor){
        this.saldo += valor;
    }

    private void setValorFinanciamento(Double valorFinanciamento) {
        this.valorFinanciamento = valorFinanciamento;
    }

    private void passaMes(){
        this.mes = LocalDate.now().getMonthValue();
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    private void adicionarTitular(Pessoa pessoa) throws ContaTitularesException{
        int numTitulares = 0;
        for(Pessoa p : titulares){
            numTitulares++;
        }
        if(numTitulares != 0){
            throw new ContaTitularesException();
        }else{
            titulares.add(pessoa);
            pessoa.adicionarConta(this);
        }
    }

    private void adicionarDoisTitulares(Pessoa pessoa1, Pessoa pessoa2) throws ContaTitularesException{
        int numTitulares = 0;
        for(Pessoa p : titulares){
            numTitulares++;
        }
        if(numTitulares != 0 || !pessoa1.getVinculosPessoa().contains(pessoa2)){
            throw new ContaTitularesException();
        }else {
            titulares.add(pessoa1);
            pessoa1.adicionarConta(this);
            titulares.add(pessoa2);
            pessoa2.adicionarConta(this);

        }
    }

    private void zerarExtrato(){
        if (this.mes != LocalDate.now().getMonthValue()) {
            extrato.setLength(0);
            passaMes();
        }
    }

    public void adicionarExtrato(StringBuilder extrato){
        zerarExtrato();
        this.extrato = extrato;
    }

    @Override
    public void deposito(Double valor) throws DepositoException {

        if(valor <= 0){
            throw new DepositoException();
        }

        adicionarExtrato(str.append("Deposito: +R$").append(valor).append("\n"));
        adicionarSaldo(valor);

    }

    @Override
    public void pagamento(Double valor, Conta conta) throws PagamentoException, PagamentoSemSaldoException, DepositoException {

        if(valor <= 0){
            throw new PagamentoException();
        }
        if(valor > this.getSaldo()){
            throw new PagamentoSemSaldoException();
        }

        adicionarExtrato(str.append("Pagamento: -R$").append(valor));
        adicionarExtrato(str.append("\n"));
        this.saldo -= valor;
        conta.deposito(valor);
    }

    @Override
    public String tirarExtrato() {

        zerarExtrato();
        StringBuilder extrato = new StringBuilder();
        extrato.append("Extrato da Conta: ").append(this.NumeroConta).append(" Mês: ").append(mes).append("\n");
        extrato.append("Tipo de Conta: ").append(this.tipoConta).append("\n");
        extrato.append("Saldo Atual: ").append(this.saldo).append("\n");
        extrato.append("Financiamento a Pagar: ").append(this.valorFinanciamento).append("\n");
        extrato.append("Titulares:\n");
        if(titulares.size() == 0){
            extrato.append(" - Empresa: ").append(this.getEmpresa().getNomeEmpresa()).append("\n");
            extrato.append(" - CNPJ: ").append((this.getEmpresa().getCnpj())).append("\n\n");
        }else {
            for (Pessoa titular : titulares) {
                extrato.append(" - Nome: ").append(titular.getNome()).append("\n");
                extrato.append("   CPF: ").append(titular.getCpf()).append("\n\n");
            }
        }

        extrato.append("Extrato: \n");
        extrato.append(this.extrato);

        return extrato.toString();

    }

    @Override
    public void fazerFinanciamento(Double valor) throws FinanciamentoException {
        if(valor <= 0 ){
            throw new FinanciamentoException("O valor do financiamento deve ser maior ou igual a 0.");
        }
        adicionarExtrato(str.append("Financiamento: +R$").append(valor).append("\n"));
        setValorFinanciamento(valor*1.2);
        adicionarSaldo(valor);
    }

    @Override
    public void pagarFinanceamento() throws FinanciamentoException {

        if(this.saldo < valorFinanciamento ){
            throw new FinanciamentoException("O saldo atual não é capaz de pagar o financiamento. Valor: " + valorFinanciamento);
        }

        adicionarExtrato(str.append("Pagou Financiamento: -R$").append(valorFinanciamento).append("\n"));
        this.saldo -= valorFinanciamento;
        setValorFinanciamento(0.0);
    }


}
