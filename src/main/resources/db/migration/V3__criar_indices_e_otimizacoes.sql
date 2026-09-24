-- Índices para otimização de busca rápida em acervo e circulação
CREATE INDEX IF NOT EXISTS idx_livros_registro ON livros(registro);
CREATE INDEX IF NOT EXISTS idx_livros_classificacao ON livros(classificacao);
CREATE INDEX IF NOT EXISTS idx_usuarios_matricula ON usuarios(matricula);
CREATE INDEX IF NOT EXISTS idx_emprestimos_id_transacao ON emprestimos(id_transacao);
CREATE INDEX IF NOT EXISTS idx_emprestimos_status ON emprestimos(status_emprestimo);
CREATE INDEX IF NOT EXISTS idx_reservas_status ON reservas(status_reserva);
