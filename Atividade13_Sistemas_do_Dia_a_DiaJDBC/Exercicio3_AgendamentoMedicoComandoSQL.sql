CREATE DATABASE agendamentomedico;
USE agendamentomedico;

CREATE TABLE pessoas (
    codigo INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE contatos (
    codigo INT NOT NULL AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL,
    valor VARCHAR(100) NOT NULL,
    pessoa_codigo INT NOT NULL,
    PRIMARY KEY (codigo),
    KEY fk_contatos_pessoas (pessoa_codigo)
);