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
    taxa_deposito DECIMAL,
    taxa_saque DECIMAL
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

-- Inserindo dados na tabela Cliente
INSERT INTO Cliente (cpf, nome, email, saldo) VALUES
('111.111.111-11', 'Felipe Sobral', 'felipesobral_@hotmail.com', 1000.00),
('222.222.222-22', 'Maria Oliveira', '65f29037-dc49-4127-98ba-c82c9aeb40b5@email.webhook.site', 1500.50),
('333.333.333-33', 'Pedro Santos', 'pedro.santos@email.com', 2000.00),
('444.444.444-44', 'Ana Souza', 'ana.souza@email.com', 1200.75),
('555.555.555-55', 'Luiz Costa', 'luiz.costa@email.com', 1800.25),
('666.666.666-66', 'Julia Lima', 'julia.lima@email.com', 900.00),
('777.777.777-77', 'Roberto Pereira', 'roberto.pereira@email.com', 1300.50),
('888.888.888-88', 'Amanda Alves', 'amanda.alves@email.com', 1600.00),
('999.999.999-99', 'Lucas Rocha', 'lucas.rocha@email.com', 2200.75),
('123.456.789-01', 'Carla Oliveira', 'carla.oliveira@email.com', 950.25);


-- Inserindo dados na tabela Empresa
INSERT INTO Empresa (cnpj, nome, saldo, taxa_deposito, taxa_saque) VALUES
('01.234.567/0001-11', 'Empresa XYZ', 50000.00, 0.5, 0.7),
('11.222.333/0001-22', 'ABC Corporation', 75000.50, 0.6, 0.8),
('22.333.444/0001-33', 'InovaTech Solutions', 100000.00, 0.7, 0.9),
('33.444.555/0001-44', 'TechStart Ltda.', 60000.75, 0.4, 0.6),
('44.555.666/0001-55', 'Global Innovations', 90000.25, 0.6, 0.8),
('55.666.777/0001-66', 'MegaSoft Systems', 45000.00, 0.3, 0.5),
('66.777.888/0001-77', 'Data Dynamics', 65000.50, 0.8, 1.0),
('77.888.999/0001-88', 'Future Technologies', 80000.00, 0.7, 0.9),
('88.999.000/0001-99', 'NexGen Innovations', 110000.75, 0.5, 0.7),
('99.000.111/0001-00', 'WiseTech Solutions', 47500.25, 0.4, 0.6);

-- Inserindo dados na tabela Transacao
INSERT INTO Transacao (tipo, valor, data_transacao, cliente_id, empresa_id) VALUES
('DEPÓSITO', 500.00, CURRENT_TIMESTAMP, 1, 1),
('SAQUE', 200.50, CURRENT_TIMESTAMP, 2, 2),
('DEPÓSITO', 800.00, CURRENT_TIMESTAMP, 3, 3),
('SAQUE', 300.75, CURRENT_TIMESTAMP, 4, 4),
('DEPÓSITO', 1000.25, CURRENT_TIMESTAMP, 5, 5),
('SAQUE', 400.00, CURRENT_TIMESTAMP, 6, 6),
('DEPÓSITO', 600.50, CURRENT_TIMESTAMP, 7, 7),
('SAQUE', 150.00, CURRENT_TIMESTAMP, 8, 8),
('DEPÓSITO', 1200.00, CURRENT_TIMESTAMP, 9, 9),
('SAQUE', 350.25, CURRENT_TIMESTAMP, 10, 10);
