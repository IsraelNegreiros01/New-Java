CREATE DATABASE IF NOT EXISTS db_passagens;
USE db_passagens;

CREATE TABLE IF NOT EXISTS voos (
    numero_voo VARCHAR(20) PRIMARY KEY,
    origem VARCHAR(50) NOT NULL,
    destino VARCHAR(50) NOT NULL,
    preco_base DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS passageiros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS passagens_vendidas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_passageiro INT NOT NULL,
    numero_voo VARCHAR(20) NOT NULL,
    tipo_classe VARCHAR(20) NOT NULL, -- 'Economica' ou 'Executiva'
    preco_final DECIMAL(10,2) NOT NULL,
    detalhes_bagagem VARCHAR(150),
    servicos_inclusos VARCHAR(150),
    FOREIGN KEY (id_passageiro) REFERENCES passageiros(id) ON DELETE CASCADE,
    FOREIGN KEY (numero_voo) REFERENCES voos(numero_voo) ON DELETE CASCADE
);

INSERT IGNORE INTO voos (numero_voo, origem, destino, preco_base) VALUES 
('AD2026', 'Belo Horizonte', 'São Paulo', 300.00),
('G31542', 'Rio de Janeiro', 'Salvador', 550.00),
('LA3312', 'Brasília', 'Miami', 2500.00);