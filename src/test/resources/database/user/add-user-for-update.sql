INSERT INTO users (id, email, first_name, last_name, password, is_deleted) VALUES
    (3, 'test@example.com', 'Name', 'Last', '$2a$10$yvofprfFFpxZatoflOuduupLzGqFWizREUJ0.lcQMDREJ28r8XB6.', false);

INSERT INTO users_roles (user_id, role_id) VALUES
    (3, 2);

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));