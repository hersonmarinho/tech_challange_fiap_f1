CREATE TABLE usuarios (
    nome VARCHAR2(255),
    login VARCHAR2(255),
    senha VARCHAR2(255),
    email VARCHAR2(255),
    tipo_usuario VARCHAR2(1),
    endereco VARCHAR2(255),
    data_ultima_alteracao DATE,
    PRIMARY KEY (nome, login)
);