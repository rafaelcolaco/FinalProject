CREATE DATABASE sistema_vendas;

USE sistema_vendas;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
	ativo BOOLEAN DEFAULT TRUE,
    pcd BOOLEAN DEFAULT FALSE,
    senha VARCHAR(255) NOT NULL,
    dataCadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vendedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    ativo BOOLEAN NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE marca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE veiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(255) NOT NULL,
    preco DOUBLE NOT NULL,
    marca_id INT,
    pcd BOOLEAN,
    FOREIGN KEY (marca_id) REFERENCES marca(id)
);

CREATE TABLE venda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    vendedor_id INT NOT NULL DEFAULT 1,
    interesse_id INT,
    preco_final DOUBLE NOT NULL,
    parcelas INT NOT NULL,
    data_venda DATE NOT NULL,
    veiculo_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (vendedor_id) REFERENCES vendedor(id),
    FOREIGN KEY (interesse_id) REFERENCES interesse(id),
    FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)
);
