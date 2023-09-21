package org.example.domain;

import org.example.domain.conta.Conta;
import org.example.domain.conta.ContaPoupanca;
import org.example.excepption.*;
import org.example.service.AcoesBancariasService;
import org.example.service.ValidadorCpfService;

import java.util.ArrayList;

public class Pessoa implements ValidadorCpfService, AcoesBancariasService {
    private String nome;
    private String cpf;
    private TipoPessoa tipoPessoa;
    private ArrayList<Conta> contas = new ArrayList<Conta>();
    private ArrayList<TipoVinculo> vinculosTipo = new ArrayList<TipoVinculo>();
    private ArrayList<Pessoa> vinculosPessoa = new ArrayList<Pessoa>();

    public Pessoa(String nome, String cpf, TipoPessoa tipoPessoa) throws CpfException {
        this.nome = nome;
        this.cpf = cpf;
        this.tipoPessoa = tipoPessoa;

        if(!validaCpf()){
            throw new CpfException();
        }
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public String getListaContas(){
        String lista = "";
        for(int x = 0; x < contas.size(); x++){
            lista += contas.get(x).getNumeroConta() + " " + contas.get(x).getTipoConta().getNomeTipoConta();
            if(x+1 < contas.size()){
               lista += "\n";
            }
        }
        return lista;
    }

    public void adicionarVinculo(Pessoa pessoa, TipoVinculo vinculo) throws PessoaVinculoException{

        if(this == pessoa){
            throw new PessoaVinculoException();
        }

        vinculosPessoa.add(pessoa);
        vinculosTipo.add(vinculo);

        pessoa.vinculosPessoa.add(this);
        pessoa.vinculosTipo.add(vinculo);

    }

    public ArrayList<TipoVinculo> getVinculosTipo() {
        return vinculosTipo;
    }

    public ArrayList<Pessoa> getVinculosPessoa() {
        return vinculosPessoa;
    }

    public String getVinculos(){
        String vinculos = "";
        for (int x = 0; x < this.vinculosPessoa.size(); x++){
          vinculos += this.getVinculosPessoa().get(x).nome + " " + this.getVinculosTipo().get(x).getNomeTipoVInculo();
          if(x + 1 < this.vinculosPessoa.size()){
              vinculos += "\n";
          }
        }
        return vinculos;
    }

    @Override
    public boolean validaCpf() {
        String cpfFormatado = this.cpf.replaceAll("\\D", "");
        if(cpfFormatado.length() != 11){
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpfFormatado.charAt(i));
            soma += digito * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);

        if (primeiroDigito == 10 || primeiroDigito == 11) {
            primeiroDigito = 0;
        }
        if (primeiroDigito != Character.getNumericValue(cpfFormatado.charAt(9))) {
            return false;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            int digito = Character.getNumericValue(cpfFormatado.charAt(i));
            soma += digito * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);

        if (segundoDigito == 10 || segundoDigito == 11) {
            segundoDigito = 0;
        }
        if (segundoDigito != Character.getNumericValue(cpfFormatado.charAt(10))) {
            return false;
        }

        return true;
    }

    @Override
    public void realizarDeposito(Conta minhaConta, Double valor) throws DepositoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.deposito(valor);
    }

    @Override
    public void realizarPagamento(Conta minhaConta, Double valor, Conta conta) throws PagamentoException, PagamentoSemSaldoException, DepositoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.pagamento(valor, conta);
    }

    @Override
    public String verSaldo(Conta minhaConta) throws NaoTitularException {
        verificaTitular(minhaConta);
        return minhaConta.verSaldo();
    }

    @Override
    public void realizarFinanciamento(Conta minhaConta, Double valor) throws FinanciamentoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.fazerFinanciamento(valor);
    }

    @Override
    public void realizarPagamentoFinanceamento(Conta minhaConta) throws FinanciamentoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.pagarFinanceamento();
    }

    @Override
    public String tirarExtratoDoMes(Conta minhaConta) throws NaoTitularException {
        verificaTitular(minhaConta);
        return minhaConta.tirarExtrato();
    }

    @Override
    public void realizarAplicacao(ContaPoupanca minhaConta, Double valor) throws AplicacaoException, NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.fazerAplicacao(valor);
    }

    @Override
    public void receberAplicacao(ContaPoupanca minhaConta) throws NaoTitularException {
        verificaTitular(minhaConta);
        minhaConta.receberAplicacao();
    }

    public void verificaTitular(Conta conta) throws NaoTitularException {
        if(!conta.getTitulares().contains(this)) {
            throw new NaoTitularException();
        }
    }

}
