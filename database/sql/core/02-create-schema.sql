-- scheme for core
DROP SCHEMA IF EXISTS core CASCADE;
CREATE SCHEMA IF NOT EXISTS core AUTHORIZATION postgres;

-- tables for core
DROP TABLE IF EXISTS core.users;
DROP SEQUENCE IF EXISTS core.users_seq;

CREATE SEQUENCE core.users_seq INCREMENT 1 MINVALUE 3 MAXVALUE 9223372036854775807 START 3 CACHE 1;

CREATE TABLE core.users(
  id BIGINT DEFAULT nextval('core.users_seq') NOT NULL,
  password VARCHAR(500),
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  email VARCHAR(100),
  phone_number VARCHAR(20),
  enabled BOOLEAN,

  CONSTRAINT pk_users PRIMARY KEY(id)
);

CREATE TABLE core.roles(
  id BIGINT NOT NULL,
  name VARCHAR(20),

  CONSTRAINT pk_roles PRIMARY KEY(id)
);

CREATE SEQUENCE core.user_roles_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE core.user_roles(
  id BIGINT DEFAULT nextval('core.user_authority_seq') NOT NULL,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,

  CONSTRAINT pk_user_roles PRIMARY KEY(id),
  CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES core.users(id),
  CONSTRAINT fk_user_roles_authority_id FOREIGN KEY (role_id) REFERENCES core.roles(id)
);