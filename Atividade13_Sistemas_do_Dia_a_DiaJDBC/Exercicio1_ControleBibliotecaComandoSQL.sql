CREATE DATABASE controle_biblioteca;
USE controle_biblioteca;
CREATE TABLE alunos (
    matricula INT PRIMARY KEY,
    multa BOOLEAN NOT NULL,
    livros_emprestados INT NOT NULL
);
CREATE TABLE livros (
    codigo_livro INT PRIMARY KEY,
    disponivel BOOLEAN NOT NULL
);
CREATE TABLE emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula INT NOT NULL,
    codigo_livro INT NOT NULL,
    status VARCHAR(50) NOT NULL,

    FOREIGN KEY (matricula)
        REFERENCES alunos(matricula),

    FOREIGN KEY (codigo_livro)
        REFERENCES livros(codigo_livro)
);
