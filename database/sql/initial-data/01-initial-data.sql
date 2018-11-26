--Password encoder https://www.dailycred.com/article/bcrypt-calculator
--Max number round 10
--Default password 12345
INSERT INTO core.users(id, phone, phone, first_name, last_name, password, enabled) VALUES
  (1, '+996772136130', 'user@domain.com', 'Александр', 'Иванов', '$2a$10$h7MeivRcGNAnDS4p4buFuuYIbruyKcmT7DbKXwDagwNyMnvnuhXLS', true);
INSERT INTO core.users(id, password, first_name, last_name, email, phone, enabled) VALUES
  (2, '+996557136130', 'admin@domain.com', 'Роман', 'Александров', '$2a$10$h7MeivRcGNAnDS4p4buFuuYIbruyKcmT7DbKXwDagwNyMnvnuhXLS', true);

INSERT INTO core.roles(id, name) VALUES (1, 'ROLE_USER');
INSERT INTO core.roles(id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO core.user_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO core.user_roles(user_id, role_id) VALUES (2, 1);
INSERT INTO core.user_roles(user_id, role_id) VALUES (2, 2);