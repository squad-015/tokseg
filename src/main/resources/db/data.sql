-- Inserir tipos de usuário
INSERT INTO tipo_usuarios (id, nome, descricao) VALUES 
(1, 'ADMIN', 'Administrador do sistema'),
(2, 'MORADOR', 'Morador do condomínio'),
(3, 'ENTREGADOR', 'Entregador de encomendas')
ON CONFLICT (id) DO NOTHING;

-- Inserir usuários (senha: 123456)
INSERT INTO usuarios (id, nome, email, senha, telefone, tipo_usuario_id) VALUES 
(1, 'Administrador', 'admin@example.com', '$2a$10$PUvDWuJG.a.FEECCxQTjzOQwi3IFsZpK6Jy5jCJWEYZ6NnX8iNrNu', '81999999999', 1)
ON CONFLICT (id) DO NOTHING;

-- Inserir condomínios
INSERT INTO condominios (id, nome, endereco, telefone) VALUES 
(1, 'Residencial Flores', 'Rua das Flores, 123', '8132224444'),
(2, 'Edifício Central', 'Avenida Central, 456', '8132225555'),
(3, 'Condomínio Parque', 'Rua do Parque, 789', '8132226666')
ON CONFLICT (id) DO NOTHING;

-- Inserir armários
INSERT INTO armarios (id, localizacao, id_condominio) VALUES 
(1, 'Bloco A, Térreo', 1),
(2, 'Bloco B, Térreo', 1),
(3, 'Hall de Entrada', 2)
ON CONFLICT (id) DO NOTHING;

-- Inserir compartimentos
INSERT INTO compartimentos (id, numero, status, id_armario) VALUES 
(1, 1, 'DISPONIVEL', 1),
(2, 2, 'DISPONIVEL', 1),
(3, 3, 'DISPONIVEL', 1),
(4, 1, 'DISPONIVEL', 2),
(5, 2, 'DISPONIVEL', 2),
(6, 1, 'DISPONIVEL', 3)
ON CONFLICT (id) DO NOTHING;