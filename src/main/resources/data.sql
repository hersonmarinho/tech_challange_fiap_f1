-- ====================================================================
-- 1. ESTRUTURA DA TABELA (usuarios)
-- ====================================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL,
    -- Campos embutidos de Endereco (@Embeddable)
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(50) NOT NULL,
    complemento VARCHAR(255),
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    cep VARCHAR(20) NOT NULL,
    data_ultima_alteracao TIMESTAMP
    );

-- Inserção de Dono de Restaurante
INSERT INTO usuarios ( id, login, nome, email, senha, tipo_usuario,
                       logradouro, numero, complemento, cidade, estado, cep,
                       data_ultima_alteracao)
VALUES ('15f8cd91-93d2-457d-9d25-855ae4fc6433',
        'carlos_dono',
        'Carlos Oliveira',
        'carlos.dono@restaurante.com',
        'fJyz9DyrXZ3AJmE0fBe4dQ==',
        'DONO_RESTAURANTE',
        'Rua das Flores',
        '100',
        'Bloco A, Sala 12',
        'São Paulo',
        'SP',
        '01234-567',
        '2026-08-04 21:17:25');

-- Inserção de Cliente
INSERT INTO usuarios ( id, login, nome, email, senha, tipo_usuario,
                       logradouro, numero, complemento, cidade, estado, cep,
                       data_ultima_alteracao)
VALUES ('83628f10-9202-4d09-ae65-732d5202784e',
        'mari_silva',
        'Mariana Silva',
        'mariana.cliente@email.com',
        'mvCS8Lunshv4pw3SLUU2cw==',
        'CLIENTE',
        'Avenida Central',
        '450',
        'Apto 32',
        'Campinas',
        'SP',
        '13010-000',
        '2026-08-04 21:17:25');