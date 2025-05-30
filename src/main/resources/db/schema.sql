-- Sequências para IDs
CREATE SEQUENCE IF NOT EXISTS tipo_usuarios_id_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS usuarios_id_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS condominios_id_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS armarios_id_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS compartimentos_id_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS entregas_id_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS retiradas_id_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS mensagens_id_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS relatorios_id_seq START WITH 100;

-- Tabela de tipos de usuário
CREATE TABLE IF NOT EXISTS tipo_usuarios (
    id BIGINT PRIMARY KEY DEFAULT nextval('tipo_usuarios_id_seq'),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(500)
);

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT PRIMARY KEY DEFAULT nextval('usuarios_id_seq'),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    tipo_usuario_id BIGINT REFERENCES tipo_usuarios(id)
);

-- Tabela de condomínios
CREATE TABLE IF NOT EXISTS condominios (
    id BIGINT PRIMARY KEY DEFAULT nextval('condominios_id_seq'),
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);

-- Tabela de armários
CREATE TABLE IF NOT EXISTS armarios (
    id BIGINT PRIMARY KEY DEFAULT nextval('armarios_id_seq'),
    localizacao TEXT,
    id_condominio BIGINT REFERENCES condominios(id) NOT NULL
);

-- Tabela de compartimentos
CREATE TABLE IF NOT EXISTS compartimentos (
    id BIGINT PRIMARY KEY DEFAULT nextval('compartimentos_id_seq'),
    numero INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL,
    id_armario BIGINT REFERENCES armarios(id) NOT NULL
);

-- Tabela de entregas
CREATE TABLE IF NOT EXISTS entregas (
    id BIGINT PRIMARY KEY DEFAULT nextval('entregas_id_seq'),
    codigo_rastreio VARCHAR(50) NOT NULL,
    data_entrega TIMESTAMP NOT NULL,
    id_compartimento BIGINT REFERENCES compartimentos(id) NOT NULL,
    id_usuario BIGINT REFERENCES usuarios(id) NOT NULL
);

-- Tabela de retiradas
CREATE TABLE IF NOT EXISTS retiradas (
    id BIGINT PRIMARY KEY DEFAULT nextval('retiradas_id_seq'),
    data_retirada TIMESTAMP NOT NULL,
    observacao TEXT,
    entrega_id BIGINT REFERENCES entregas(id) NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id) NOT NULL
);

-- Tabela de mensagens
CREATE TABLE IF NOT EXISTS mensagens (
    id BIGINT PRIMARY KEY DEFAULT nextval('mensagens_id_seq'),
    conteudo TEXT NOT NULL,
    data_envio TIMESTAMP NOT NULL,
    id_usuario BIGINT REFERENCES usuarios(id) NOT NULL,
    id_entrega BIGINT REFERENCES entregas(id) NOT NULL
);

-- Tabela de relatórios
CREATE TABLE IF NOT EXISTS relatorios (
    id BIGINT PRIMARY KEY DEFAULT nextval('relatorios_id_seq'),
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    data_geracao TIMESTAMP NOT NULL,
    observacao TEXT,
    usuario_id BIGINT REFERENCES usuarios(id) NOT NULL
);