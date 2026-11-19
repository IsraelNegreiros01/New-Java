CREATE DATABASE IF NOT EXISTS db_chamados_varejo
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE db_chamados_varejo;


-- 1. TABELA: unidades
CREATE TABLE unidades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_loja VARCHAR(20) NOT NULL UNIQUE,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    tipo_unidade ENUM('MATRIZ', 'LOJA') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;


-- 2. TABELA: categorias
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT
) ENGINE=InnoDB;


-- 3. TABELA: usuarios
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil ENUM('SOLICITANTE', 'TECNICO_N1', 'TECNICO_N2', 'TECNICO_N3', 'ADMINISTRADOR') NOT NULL,
    unidade_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuarios_unidades FOREIGN KEY (unidade_id) REFERENCES unidades(id)
) ENGINE=InnoDB;


-- 4. TABELA: chamados
CREATE TABLE chamados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    unidade_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    solicitante_id BIGINT NOT NULL,
    tecnico_atribuido_id BIGINT NULL,
    tipo_chamado ENUM('INCIDENTE', 'SOLICITACAO_SERVICO', 'MUDANCA_MELHORIA') NOT NULL,
    impacto ENUM('BAIXO', 'MEDIO', 'ALTO') NOT NULL,
    urgencia ENUM('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
    prioridade ENUM('BAIXA', 'MEDIA', 'ALTA', 'CRITICA') NOT NULL,
    status ENUM('NOVO', 'EM_ATENDIMENTO', 'ESCALADO', 'RESOLVIDO', 'FECHADO') NOT NULL DEFAULT 'NOVO',
    nivel_suporte ENUM('N1', 'N2', 'N3') NOT NULL DEFAULT 'N1',
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NOT NULL,
    solucao_registrada TEXT NULL,
    sla_limite DATETIME NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_chamados_unidade FOREIGN KEY (unidade_id) REFERENCES unidades(id),
    CONSTRAINT fk_chamados_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT fk_chamados_solicitante FOREIGN KEY (solicitante_id) REFERENCES usuarios(id),
    CONSTRAINT fk_chamados_tecnico FOREIGN KEY (tecnico_atribuido_id) REFERENCES usuarios(id)
) ENGINE=InnoDB;


-- 5. TABELA: historico_chamados
CREATE TABLE historico_chamados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chamado_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    nivel_origem ENUM('N1', 'N2', 'N3') NULL,
    nivel_destino ENUM('N1', 'N2', 'N3') NULL,
    motivo TEXT NOT NULL,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_historico_chamado FOREIGN KEY (chamado_id) REFERENCES chamados(id),
    CONSTRAINT fk_historico_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
) ENGINE=InnoDB;


-- 6. TABELA: artigos_base_conhecimento
CREATE TABLE artigos_base_conhecimento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoria_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    conteudo TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_artigos_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT fk_artigos_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FULLTEXT INDEX idx_busca_artigo (titulo, conteudo)
) ENGINE=InnoDB;


-- ========================================================
-- POVOAMENTO INICIAL DE DADOS (DML)
-- ========================================================
-- Inserção de Unidades
INSERT INTO unidades (id, codigo_loja, cidade, uf, tipo_unidade) VALUES
(1, 'MATRIZ-01', 'São Paulo', 'SP', 'MATRIZ'),
(2, 'LOJA-101', 'Belo Horizonte', 'MG', 'LOJA');


-- Inserção de Categorias
INSERT INTO categorias (id, nome, descricao) VALUES
(1, 'PDV', 'FALHAS EM CAIXAS E MAQUINAS DE CARTÃO'),
(2, 'INTERNET', 'QUEDA DE LINK E CONECTIVIDADE'),
(3, 'IMPRESSAO', 'IMPRESSORAS DE CUPOM E ETIQUETAS');


-- Inserção de Usuários (Gerffeone configurado como ADMINISTRADOR)
INSERT INTO usuarios (id, nome, email, senha, perfil, unidade_id) VALUES
(1, 'Gerffeone', 'Gerffeone@gmail.com', '2a10$e89b.8K9u/hCq6.Y.XG7O.N5FwL0N3gH6uE/fK1t0B.y1Jg8cK1e', 'ADMINISTRADOR', 1),
(2, 'Ana Ribeiro', 'ana.ribeiro@varejo.com', '2a10$e89b.8K9u/hCq6.Y.XG7O.N5FwL0N3gH6uE/fK1t0B.y1Jg8cK1e', 'SOLICITANTE', 1),
(3, 'Bruno Lima', 'bruno.lima@varejo.com', '2a10$e89b.8K9u/hCq6.Y.XG7O.N5FwL0N3gH6uE/fK1t0B.y1Jg8cK1e', 'SOLICITANTE', 1),
(4, 'Diego Nunes', 'diego.nunes@varejo.com', '2a10$e89b.8K9u/hCq6.Y.XG7O.N5FwL0N3gH6uE/fK1t0B.y1Jg8cK1e', 'TECNICO_N1', 1),
(5, 'Fernanda Melo', 'fernanda.melo@varejo.com', '2a10$e89b.8K9u/hCq6.Y.XG7O.N5FwL0N3gH6uE/fK1t0B.y1Jg8cK1e', 'TECNICO_N1', 1),
(6, 'Caio Souza', 'caio.souza@varejo.com', '2a10$e89b.8K9u/hCq6.Y.XG7O.N5FwL0N3gH6uE/fK1t0B.y1Jg8cK1e', 'TECNICO_N2', 1),
(7, 'Patrícia Gomes', 'patricia.gomes@varejo.com', '2a10$e89b.8K9u/hCq6.Y.XG7O.N5FwL0N3gH6uE/fK1t0B.y1Jg8cK1e', 'TECNICO_N3', 1);


ALTER TABLE usuarios AUTO_INCREMENT = 8;
