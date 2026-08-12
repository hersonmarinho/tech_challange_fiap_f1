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
VALUES ('1234',
        'carlos_dono',
        'Carlos Oliveira',
        'carlos.dono@restaurante.com',
        'senha123',
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
VALUES ('5678',
        'mari_silva',
        'Mariana Silva',
        'mariana.cliente@email.com',
        'cliente123',
        'CLIENTE',
        'Avenida Central',
        '450',
        'Apto 32',
        'Campinas',
        'SP',
        '13010-000',
        '2026-08-04 21:17:25');