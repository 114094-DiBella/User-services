-- ✅ TEMPORAL: Passwords en texto plano para testing fácil
INSERT INTO users (id, email, first_name, last_name, password, telephone, number_doc, birthday, created_at, updated_at, role, status)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'admin@example.com', 'Admin', 'User', 'admin123', NULL, 12345678, '2002-04-11T12:00:00', '2025-04-11T12:00:00', '2025-04-11T12:00:00', 'ADMIN', 1);

INSERT INTO users (id, email, first_name, last_name, password, telephone, number_doc, birthday, created_at, updated_at, role, status)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'usuario@example.com', 'Usuario', 'Normal', 'user123', NULL, 12345698, '2002-04-11T12:00:00', '2025-04-11T12:00:00', '2025-04-11T12:00:00', 'USER', 1);

-- Credenciales para testing:
-- admin@example.com / admin123
-- usuario@example.com / user123