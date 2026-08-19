package br.gov.pa.pge.biblback.enums;


import lombok.Getter;

@Getter
public enum StatusEmprestimo {
    ATIVO  (1),
    CONCLUIDO(2),
    ATRASADO(3);


    private Integer valor;
    StatusEmprestimo(Integer valor){
        this.valor = valor;
    }


}
