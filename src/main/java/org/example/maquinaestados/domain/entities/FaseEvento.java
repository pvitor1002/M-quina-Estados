package org.example.maquinaestados.domain.entities;

public enum FaseEvento {
    TRANSFERENCIA_SOLICITADA("TRANSFERENCIA_SOLICITADA"),
    TRANSFERENCIA_REALIZADA("TRANSFERENCIA_REALIZADA");

    private String fase;

    private FaseEvento(String fase) {this.fase = fase;}

    @Override
    public String toString() {return this.fase;}
}
