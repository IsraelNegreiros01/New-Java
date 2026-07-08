CREATE DATABASE IF not exists db_hotel;
USE db_hotel;

CREATE TABLE IF not exists hoteis_quartos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cidade VARCHAR(100) NOT NULL,
    numero_quarto VARCHAR(10) NOT NULL,
    preco_diaria DECIMAL(10,2) NOT NULL,
    disponivel VARCHAR(3) DEFAULT 'SIM'
);

CREATE TABLE IF not exists clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF not exists reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    id_quarto INT,
    data_checkin VARCHAR(10) NOT NULL,
    data_checkout VARCHAR(10) NOT NULL,
    qtd_hospedes INT NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    forma_pagamento VARCHAR(50),
    status_pagamento VARCHAR(20)
);

INSERT INTO hoteis_quartos (cidade, numero_quarto, preco_diaria, disponivel) VALUES 
('Belo Horizonte', '101', 150.00, 'SIM'),
('Belo Horizonte', '102', 250.00, 'SIM'),
('Sao Paulo', '305', 350.00, 'SIM');