# 🏛️ SISBIB PGE-PA — Backend (`bibl-back`)

API REST para o **Sistema Integrado de Bibliotecas e Gestão de Acervo Documental (SISBIB)** da **Procuradoria Geral do Estado do Pará (PGE-PA)**.

---

## 📌 Visão Geral

O `bibl-back` é responsável pelas regras de negócio, persistência de dados, controle de circulação de exemplares, autenticação e autorização, migrações automatizadas de banco de dados e geração de dados analíticos para relatórios institucionais.

### Principais Funcionalidades:
- **Catálogo Bibliográfico SISBIB:** Gestão completa de obras com número de registro/tombo, classificação (CDU/CDD), tipo documental, dados editoriais, paginação, descritores temáticos e ISBN.
- **Gestão de Leitores:** Cadastro de servidores e leitores com matrícula funcional, lotação/setor e perfil de acesso (`ROLE_ADMIN` e `ROLE_USUARIO`).
- **Controle de Circulação:** Empréstimos individuais e em lote com código identificador de transação (`TRX`), vinculação do atendente/bibliotecário, controle de renovações com limite e agendamento automático de atrasos.
- **Reservas de Exemplares:** Fila de espera e agendamento para livros emprestados.
- **Relatórios Analíticos:** Consolidação de métricas por período, leitor e status, com rankings dos servidores mais ativos e obras mais solicitadas.
- **Segurança:** Autenticação stateless via JSON Web Token (JWT) e senhas criptografadas com BCrypt.
- **Migrações Automáticas:** Versionamento e evolução do banco de dados PostgreSQL com **Flyway**.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17 (LTS)
- **Framework:** Spring Boot (Spring MVC, Spring Data JPA, Spring Security)
- **Banco de Dados:** PostgreSQL
- **Migrações de Banco:** Flyway (com suporte a PostgreSQL)
- **Autenticação:** JWT (Java-JWT da Auth0) + Spring Security
- **Utilitários:** Lombok, Jakarta Validation
- **Gerenciador de Dependências:** Apache Maven (com Maven Wrapper `./mvnw`)

---

## ⚙️ Pré-requisitos

Antes de iniciar a aplicação, certifique-se de possuir instalado em sua máquina:

1. **Java JDK 17** ou superior:
   ```bash
   java -version
   ```
2. **PostgreSQL 14** ou superior:
   - Certifique-se de que o serviço do PostgreSQL está ativo na porta `5432`.
   - Crie o banco de dados da aplicação caso ainda não exista:
     ```sql
     CREATE DATABASE bibl;
     ```

---

## 🔐 Configuração de Ambiente (`.env`)

A aplicação suporta configuração por variáveis de ambiente ou arquivo `.env` local.

### 1. Criar o arquivo `.env`:
Copie o modelo de exemplo:
```bash
cp .env.example .env
```

### 2. Parâmetros disponíveis:

| Variável | Descrição | Exemplo Ilustrativo (`.env.example`) |
|---|---|---|
| `SERVER_PORT` | Porta HTTP da API REST | `8080` |
| `DB_URL` | URL de conexão JDBC PostgreSQL | `jdbc:postgresql://localhost:5432/nome_do_seu_banco` |
| `DB_USERNAME` | Usuário do banco PostgreSQL | `seu_usuario_postgres` |
| `DB_PASSWORD` | Senha do banco PostgreSQL | `sua_senha_segura` |
| `JWT_SECRET` | Chave secreta de assinatura JWT | `sua_chave_secreta_jwt_minimo_32_chars` |

> [!NOTE]
> O arquivo `.env` real contém suas credenciais locais e **está ignorado pelo Git** (`.gitignore`), garantindo a segurança de segredos e senhas.

---

## 🚀 Como Executar o Backend

### Passo 1: Entrar na pasta do backend
```bash
cd bibl-back
```

### Passo 2: Executar com o Maven Wrapper
```bash
./mvnw spring-boot:run
```

Na primeira inicialização:
1. O **Flyway** aplicará automaticamente todas as migrações pendentes em `src/main/resources/db/migration/`:
   - `V1`: Tabelas estruturais base (`usuarios`, `livros`, `emprestimos`, `reservas`).
   - `V2`: Campos do acervo SISBIB e dados funcionais de servidores.
   - `V3`: Índices de alta performance para busca e relatórios.
2. O **DataInitializer** criará os usuários administradores iniciais para acesso imediato.

A API estará disponível em: **`http://localhost:8080`**

---

## 👥 Credenciais Iniciais de Administrador

| E-mail | Senha Padrão | Perfil | Matrícula | Setor |
|---|---|---|---|---|
| `victor@gmail.com` | `123456` | Administrador (`ROLE_ADMIN`) | `PGE-1001` | ESAP |
| `admin@bibl.gov.br` | `123456` | Administrador (`ROLE_ADMIN`) | `ADM-001` | Biblioteca Central |

---

## 📋 Principais Endpoints da API

### Autenticação:
- `POST /api/login` ou `POST /login`: Efetua autenticação e retorna Token JWT com dados do usuário.

### Acervo (Livros):
- `GET /api/livros`: Lista obras cadastradas (suporta `?termo=busca`).
- `GET /api/livros/{id}`: Detalhes de um exemplar.
- `POST /api/livros`: Cadastra nova obra no acervo *(requer ADMIN)*.
- `PUT /api/livros/{id}`: Atualiza dados da obra *(requer ADMIN)*.
- `DELETE /api/livros/{id}`: Remove exemplar do acervo *(requer ADMIN)*.

### Leitores (Usuários):
- `GET /api/usuarios`: Lista leitores e operadores.
- `POST /api/usuarios`: Cadastra novo leitor ou atendente.
- `PUT /api/usuarios/{id}`: Atualiza dados cadastrais.
- `PATCH /api/usuarios/{id}/desativar`: Inativa usuário.
- `PATCH /api/usuarios/{id}/ativar`: Reativa usuário.

### Circulação (Empréstimos):
- `GET /api/emprestimos`: Histórico de saídas.
- `POST /api/emprestimos`: Realiza empréstimo individual com atendente.
- `POST /api/emprestimos/lote`: Realiza empréstimo em lote de múltiplos livros.
- `PATCH /api/emprestimos/{id}/renovar`: Renova empréstimo por +14 dias.
- `PATCH /api/emprestimos/{id}/devolver`: Registra devolução no acervo.

### Reservas:
- `GET /api/reservas`: Lista de reservas.
- `POST /api/reservas`: Solicita reserva de livro indisponível.
- `PATCH /api/reservas/{id}/cancelar`: Cancela reserva.

### Relatórios:
- `GET /api/relatorios/gerarRelatorio`: Emite dados estatísticos e analíticos consolidados com filtros por:
  - `usuarioId`: ID do servidor/leitor.
  - `dataInicio` / `dataFim`: Intervalo de datas.
  - `status`: `ATIVO`, `CONCLUIDO` ou `ATRASADO`.

---

## 🧪 Testes e Compilação

Para compilar o projeto e validar as classes sem iniciar o servidor:
```bash
./mvnw clean test-compile
```
