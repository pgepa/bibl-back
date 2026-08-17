package br.gov.pa.pge.biblback.converter;

import br.gov.pa.pge.biblback.enums.StatusUsuario;
import br.gov.pa.pge.biblback.exception.CodigoInvalidoException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class StatusUsuarioConverter implements AttributeConverter<StatusUsuario, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusUsuario statusUsuario) {
        return statusUsuario == null ? null : statusUsuario.getCODIGO();
    }

    @Override
    public StatusUsuario convertToEntityAttribute(Integer codigo) {
        return Arrays
                .stream(StatusUsuario.values())
                .filter(s -> s.getCODIGO().equals(codigo))
                .findFirst().orElseThrow(CodigoInvalidoException::new);
    }
}
