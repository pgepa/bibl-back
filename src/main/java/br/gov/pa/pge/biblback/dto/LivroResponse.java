package br.gov.pa.pge.biblback.dto;

import br.gov.pa.pge.biblback.model.Livro;

public record LivroResponse(
        Long id,
        String titulo,
        String autor,
        String isbn,
        Integer anoLancamento,
        Boolean disponivel
) {
    public static LivroResponse from(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getAnoLancamento(),
                livro.getDisponivel()
        );
    }
}
