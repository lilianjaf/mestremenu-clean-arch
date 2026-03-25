-- Create user type DONO if needed
INSERT INTO tipo_usuario (id, nome, tipo_nativo) 
VALUES ('799981d3-3b1f-4b68-b3d9-6938d6174154', 'dono', 'DONO');

-- Create Admin user
INSERT INTO usuario (
    id, nome, email, login, senha, tipo_usuario_id,
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    endereco_cidade, endereco_cep, endereco_uf,
    data_ultima_alteracao, ativo
)
VALUES (
    '8c9c7e0c-84d4-4a4e-862d-0b70c3c54d3d', 'Admin', 'admin@mestremenu.com.br', 'admin',
    '$2a$10$3tOrF7F0GRIK9dcTwMeSWONigMJgp28aifO4tEOcKpDnOsO3n7g7u', -- senha: password
    '799981d3-3b1f-4b68-b3d9-6938d6174154',
    'Rua Admin', '1', 'Sala 1', 'Bairro Admin',
    'Cidade Admin', '00000000', 'UF',
    CURRENT_TIMESTAMP, true
);

-- Create another DONO user for permission tests
INSERT INTO usuario (
    id, nome, email, login, senha, tipo_usuario_id,
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    endereco_cidade, endereco_cep, endereco_uf,
    data_ultima_alteracao, ativo
)
VALUES (
    '11111111-1111-1111-1111-111111111111', 'Outro Usuario', 'outro@mestremenu.com.br', 'outro.usuario',
    '$2a$10$3tOrF7F0GRIK9dcTwMeSWONigMJgp28aifO4tEOcKpDnOsO3n7g7u', -- senha: password
    '799981d3-3b1f-4b68-b3d9-6938d6174154',
    'Rua Outra', '2', 'Apt 2', 'Bairro Outro',
    'Cidade Outra', '11111111', 'UF',
    CURRENT_TIMESTAMP, true
);

-- Create a restaurante for the Admin user
INSERT INTO restaurante (id, nome, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade, endereco_cep, endereco_uf, tipo_cozinha, horario_funcionamento, id_dono, ativo)
VALUES ('00000000-0000-0000-0000-000000000001', 'Restaurante do Admin', 'Rua do Admin', '123', 'Lado A', 'Bairro Admin', 'Cidade Admin', '12345678', 'SP', 'Brasileira', '09:00-18:00', '8c9c7e0c-84d4-4a4e-862d-0b70c3c54d3d', true);
