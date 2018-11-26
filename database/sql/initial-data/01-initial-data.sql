--Password encoder https://www.dailycred.com/article/bcrypt-calculator
--Max number round 10
--Default password 12345
INSERT INTO core.users(id, password, first_name, last_name, email, phone_number, enabled) VALUES
  (1, '$2a$10$h7MeivRcGNAnDS4p4buFuuYIbruyKcmT7DbKXwDagwNyMnvnuhXLS', 'Александр', 'Иванов', 'user@domain.com', '+996772136130', true);
INSERT INTO core.users(id, password, first_name, last_name, email, phone_number, enabled) VALUES
  (2, '$2a$10$h7MeivRcGNAnDS4p4buFuuYIbruyKcmT7DbKXwDagwNyMnvnuhXLS', 'Роман', 'Александров', 'admin@domain.com', '+996557136130', true);

INSERT INTO core.roles(id, name) VALUES (1, 'ROLE_USER');
INSERT INTO core.roles(id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO core.user_roles(user_id, authority_id) VALUES (1, 1);
INSERT INTO core.user_roles(user_id, authority_id) VALUES (2, 1);
INSERT INTO core.user_roles(user_id, authority_id) VALUES (2, 2);