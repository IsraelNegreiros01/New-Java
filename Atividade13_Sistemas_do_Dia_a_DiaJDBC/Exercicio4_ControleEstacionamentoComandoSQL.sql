CREATE DATABASE IF NOT EXISTS db_estacionamento;
USE db_estacionamento;

CREATE TABLE IF NOT EXISTS controle_vagas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_vagas INT NOT NULL,
    vagas_ocupadas INT NOT NULL
);

CREATE TABLE IF NOT EXISTS veiculos_estacionados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    tipo_veiculo VARCHAR(10) NOT NULL, -- 'Carro' ou 'Moto'
    horario_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status_veiculo VARCHAR(15) DEFAULT 'Estacionado' -- 'Estacionado' ou 'Liberado'
);

CREATE TABLE IF NOT EXISTS pagamentos_estacionamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_veiculo INT NOT NULL,
    tempo_horas INT NOT NULL,
    valor_pago DECIMAL(10,2) NOT NULL,
    forma_pagamento VARCHAR(20) NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_veiculo) REFERENCES veiculos_estacionados(id) ON DELETE CASCADE
);

INSERT IGNORE INTO controle_vagas (id, total_vagas, vagas_ocupadas) VALUES (1, 10, 0);
