CREATE DATABASE IF NOT EXISTS db_delivery;
USE db_delivery;

CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS cardapio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_produto VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    valor_itens DECIMAL(10,2) NOT NULL,
    taxa_entrega DECIMAL(10,2) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    forma_pagamento VARCHAR(20) NOT NULL,
    status_pedido VARCHAR(30) DEFAULT 'Enviado para a Cozinha',
    FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE CASCADE
);

INSERT IGNORE INTO clientes (id, nome, email, senha) VALUES 
(1, 'João Silva', 'joao@email.com', '1234');

INSERT IGNORE INTO cardapio (nome_produto, preco) VALUES 
('Hambúrguer Artesanal', 35.00),
('Batata Frita Especial', 20.00),
('Refrigerante Lata', 6.00),
('Pizza Grande', 55.00);