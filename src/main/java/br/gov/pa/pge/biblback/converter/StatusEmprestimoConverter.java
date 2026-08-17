package br.gov.pa.pge.biblback.converter;

import br.gov.pa.pge.biblback.enums.StatusEmprestimo;
import br.gov.pa.pge.biblback.exception.CodigoInvalidoException;
import jakarta.persistence.AttributeConverter;

import java.util.Arrays;

public class StatusEmprestimoConverter implements AttributeConverter<StatusEmprestimo, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusEmprestimo status) {
        return status == null ? null : status.getValor();
    }

    @Override
    public StatusEmprestimo convertToEntityAttribute(Integer codigo) {
        return Arrays.stream(StatusEmprestimo.values()).
                filter(s -> s.getValor().equals(codigo)).
                findFirst().orElseThrow(CodigoInvalidoException::new);
    }

}
