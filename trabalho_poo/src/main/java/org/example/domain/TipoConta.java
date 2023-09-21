package org.example.domain;

public enum TipoConta {
    POUPANCA(1, "Poupança"),
    SALARIO(2, "Salário"),
    CORRENTE(3, "Corrente");

    private Integer idTipoConta;
    private String nomeTipoConta;

    TipoConta(Integer idTipoConta, String nomeTipoConta) {
        this.idTipoConta = idTipoConta;
        this.nomeTipoConta = nomeTipoConta;
    }

    public Integer getIdTipoConta() {
        return idTipoConta;
    }

    public String getNomeTipoConta() {
        return nomeTipoConta;
    }
}
