-- Campos adicionais para o acervo bibliográfico completo (legado SISBIB)
ALTER TABLE livros ADD COLUMN IF NOT EXISTS registro VARCHAR(255);
ALTER TABLE livros ADD COLUMN IF NOT EXISTS classificacao VARCHAR(255);
ALTER TABLE livros ADD COLUMN IF NOT EXISTS tipo_documental VARCHAR(255);
ALTER TABLE livros ADD COLUMN IF NOT EXISTS local_publicacao VARCHAR(255);
ALTER TABLE livros ADD COLUMN IF NOT EXISTS editora VARCHAR(255);
ALTER TABLE livros ADD COLUMN IF NOT EXISTS edicao INTEGER;
ALTER TABLE livros ADD COLUMN IF NOT EXISTS idioma VARCHAR(255);
ALTER TABLE livros ADD COLUMN IF NOT EXISTS paginas INTEGER;
ALTER TABLE livros ADD COLUMN IF NOT EXISTS descritores TEXT;

-- Campos funcionais para os leitores e servidores
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS matricula VARCHAR(255);
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS setor VARCHAR(255);

-- Campos de rastreamento para circulação e empréstimos
ALTER TABLE emprestimos ADD COLUMN IF NOT EXISTS id_transacao VARCHAR(255);
ALTER TABLE emprestimos ADD COLUMN IF NOT EXISTS nome_funcionario VARCHAR(255);
