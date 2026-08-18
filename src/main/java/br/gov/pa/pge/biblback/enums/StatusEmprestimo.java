package br.gov.pa.pge.biblback.enums;

public enum StatusEmprestimo {
    ATIVO  (1),
    CONCLUIDO(2),
    ATRASADO(3);


    private Integer valor;
    StatusEmprestimo(Integer valor){
        this.valor = valor;
    }

    public Integer getValor(){
        return valor;
    }
}
