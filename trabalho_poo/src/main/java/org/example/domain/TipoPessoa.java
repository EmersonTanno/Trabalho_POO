package org.example.domain;

public enum TipoPessoa {
    FISICA(0, "Física"),
    JURIDICA(1, "Jurídica");

    private Integer idTipoPessoa;
    private String tipoTipoPessoa;

    TipoPessoa(Integer idTipoPessoa, String tipoTipoPessoa) {
        this.idTipoPessoa = idTipoPessoa;
        this.tipoTipoPessoa = tipoTipoPessoa;
    }

    public Integer getIdTipoPessoa() {
        return idTipoPessoa;
    }

    public String getTipoTipoPessoa() {
        return tipoTipoPessoa;
    }

}
