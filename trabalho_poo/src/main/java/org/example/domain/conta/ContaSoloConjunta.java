package org.example.domain.conta;

public enum ContaSoloConjunta {

    SOLO(0, "Individual"),
    CONJUNTA(1, "Conjunta");

    private Integer idContaSoloConjunta;
    private String tipoContaSoloConjunta;

    ContaSoloConjunta(Integer idContaSoloConjunta, String tipoContaSoloConjunta) {
        this.idContaSoloConjunta = idContaSoloConjunta;
        this.tipoContaSoloConjunta = tipoContaSoloConjunta;
    }
}
