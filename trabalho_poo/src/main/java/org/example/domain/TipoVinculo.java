package org.example.domain;

public enum TipoVinculo {
    UNIAO_CIVIL(0, "União Civil"),
    PARCEIRO_NEGOCIO(1, "Parceiros de Negócio"),
    PAI_FILHO(2, "Pais e Filhos"),
    REPRESENTACAO_LEGAL(3, "Representante Legal");

    private int idTipoVinculo;
    private String nomeTipoVInculo;

    TipoVinculo(int idTipoVinculo, String nomeTipoVInculo) {
        this.idTipoVinculo = idTipoVinculo;
        this.nomeTipoVInculo = nomeTipoVInculo;
    }

    public int getIdTipoVinculo() {
        return idTipoVinculo;
    }

    public String getNomeTipoVInculo() {
        return nomeTipoVInculo;
    }
}
