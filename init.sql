-- Tabela para armazenar informações sobre clientes
CREATE TABLE Cliente (
    id SERIAL PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    saldo DECIMAL
);

-- Tabela para armazenar informações sobre empresas
CREATE TABLE Empresa (
    id SERIAL PRIMARY KEY,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    saldo DECIMAL,
    taxa_sistema DECIMAL
);

-- Tabela para armazenar informações sobre transações
CREATE TABLE Transacao (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(10) CHECK (tipo IN ('DEPÓSITO', 'SAQUE')) NOT NULL,
    valor NUMERIC NOT NULL,
    data_transacao TIMESTAMP,
    cliente_id BIGINT,
    empresa_id BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id),
    FOREIGN KEY (empresa_id) REFERENCES Empresa(id)
);
