package br.gov.pa.pge.biblback.enums;

public enum StatusUsuario {
    ATIVO(1),
    INATIVO(0);

    private final Integer CODIGO;

    StatusUsuario(Integer codigo){
        this.CODIGO = codigo;
    }

    public Integer getCODIGO(){
        return this.CODIGO;
    }
}
