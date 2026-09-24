package br.gov.pa.pge.biblback.dto;

import br.gov.pa.pge.biblback.model.Livro;

public record LivroResponse(
        Long id,
        String titulo,
        String autor,
        String isbn,
        Integer anoLancamento,
        Boolean disponivel,
        String registro,
        String classificacao,
        String tipoDocumental,
        String localPublicacao,
        String editora,
        Integer edicao,
        String idioma,
        Integer paginas,
        String descritores
) {
    public static LivroResponse from(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getAnoLancamento(),
                livro.getDisponivel(),
                livro.getRegistro(),
                livro.getClassificacao(),
                livro.getTipoDocumental(),
                livro.getLocalPublicacao(),
                livro.getEditora(),
                livro.getEdicao(),
                livro.getIdioma(),
                livro.getPaginas(),
                livro.getDescritores()
        );
    }
}
