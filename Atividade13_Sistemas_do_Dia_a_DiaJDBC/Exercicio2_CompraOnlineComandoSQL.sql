CREATE DATABASE compra_online;
USE compra_online;

CREATE TABLE clientes (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL
);
CREATE TABLE pedidos (
    numero_pedido INT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL,
    valor_compra DECIMAL(10,2) NOT NULL,
    frete DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,

    FOREIGN KEY (cpf)
        REFERENCES clientes(cpf)
);
CREATE TABLE pagamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_pedido INT NOT NULL,
    cartao_aprovado BOOLEAN NOT NULL,

    FOREIGN KEY (numero_pedido)
        REFERENCES pedidos(numero_pedido)
);
