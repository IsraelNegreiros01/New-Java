CREATE DATABASE IF NOT EXISTS db_streaming;
USE db_streaming;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS planos_detalhes (
    tipo_plano VARCHAR(20) PRIMARY KEY, -- 'Padrao' ou 'Premium'
    preco_base DECIMAL(10,2) NOT NULL,
    qualidade_video VARCHAR(30) NOT NULL,
    limite_telas INT NOT NULL,
    permite_download VARCHAR(3) NOT NULL -- 'SIM' ou 'NAO'
);

CREATE TABLE IF NOT EXISTS assinaturas_ativas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo_plano VARCHAR(20) NOT NULL,
    data_assinatura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (tipo_plano) REFERENCES planos_detalhes(tipo_plano) ON DELETE CASCADE
);

INSERT IGNORE INTO planos_detalhes (tipo_plano, preco_base, qualidade_video, limite_telas, permite_download) VALUES
('Padrao', 30.00, 'Full HD', 2, 'NAO'),
('Premium', 50.00, '4K Ultra HD', 4, 'SIM');