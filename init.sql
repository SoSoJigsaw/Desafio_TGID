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
('857.329.142-77', 'Felipe Sobral', 'felipesobral_@hotmail.com', 1000.00),
('411.012.171-03', 'Maria Oliveira', 'maria-oliveira@email.com', 1500.00),
('466.625.052-26', 'Pedro Santos', 'pedro.santos@email.com', 2000.00),
('558.687.551-97', 'Ana Souza', 'ana.souza@email.com', 1200),
('852.915.865-24', 'Luiz Costa', 'luiz.costa@email.com', 0.00),
('808.889.643-66', 'Julia Lima', 'julia.lima@email.com', 900.00),
('037.012.245-36', 'Roberto Pereira', 'roberto.pereira@email.com', 0.00),
('111.113.653-00', 'Amanda Alves', 'amanda.alves@email.com', 1600.00),
('146.831.947-75', 'Lucas Rocha', 'lucas.rocha@email.com', 2200.00),
('053.164.618-19', 'Carla Oliveira', 'carla.oliveira@email.com', 0.00);

-- Inserindo dados na tabela Empresa
INSERT INTO Empresa (cnpj, nome, saldo, taxa_deposito, taxa_saque) VALUES
('21.347.851/0001-85', 'Empresa XYZ', 20000.00, 0.5, 0.7),
('68.541.284/0001-67', 'ABC Corporation', 40000.00, 0.6, 0.8),
('45.199.289/0001-58', 'InovaTech Solutions', 100000.00, 0.7, 0.9),
('32.659.793/0001-41', 'TechStart Ltda.', 0.00, 0.4, 0.6),
('04.270.558/0001-48', 'Global Innovations', 90000.00, 0.6, 0.8),
('25.223.637/0001-78', 'MegaSoft Systems', 45000.00, 0.3, 0.5),
('20.766.943/0001-37', 'Data Dynamics', 65000.00, 0.8, 1.0),
('10.786.508/0001-10', 'Future Technologies', 80000.00, 0.7, 0.9),
('67.706.847/0001-67', 'NexGen Innovations', 0.00, 0.5, 0.7),
('61.559.248/0001-81', 'WiseTech Solutions', 37500.00, 0.4, 0.6);

-- Inserindo dados na tabela Transacao
INSERT INTO Transacao (tipo, valor, data_transacao, cliente_id, empresa_id) VALUES
('DEPÓSITO', 500.00, CURRENT_TIMESTAMP, 1, 3),
('SAQUE', 200.00, CURRENT_TIMESTAMP, 2, 5),
('DEPÓSITO', 800.00, CURRENT_TIMESTAMP, 3, 3),
('SAQUE', 300.00, CURRENT_TIMESTAMP, 4, 8),
('DEPÓSITO', 1000.00, CURRENT_TIMESTAMP, 5, 2),
('SAQUE', 400.00, CURRENT_TIMESTAMP, 6, 6),
('DEPÓSITO', 600.00, CURRENT_TIMESTAMP, 7, 10),
('SAQUE', 150.00, CURRENT_TIMESTAMP, 8, 5),
('DEPÓSITO', 1200.00, CURRENT_TIMESTAMP, 9, 7),
('SAQUE', 350.00, CURRENT_TIMESTAMP, 10, 2);
