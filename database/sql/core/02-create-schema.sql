-- scheme for core
DROP SCHEMA IF EXISTS core CASCADE;
CREATE SCHEMA IF NOT EXISTS core AUTHORIZATION postgres;

-- tables for core
DROP TABLE IF EXISTS core.user_roles;
DROP TABLE IF EXISTS core.roles;
DROP TABLE IF EXISTS core.users;

DROP SEQUENCE IF EXISTS core.user_roles_seq;
DROP SEQUENCE IF EXISTS core.users_seq;

DROP TABLE IF EXISTS core.product_images;
DROP TABLE IF EXISTS core.products;
DROP TABLE IF EXISTS core.categories;

DROP SEQUENCE IF EXISTS core.product_images_seq;
DROP SEQUENCE IF EXISTS core.products_seq;
DROP SEQUENCE IF EXISTS core.categories_seq;

------------------------------------------------------------------------------------------------------------------------
--                                           core.users
------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE core.users_seq INCREMENT 1 MINVALUE 3 MAXVALUE 9223372036854775807 START 3 CACHE 1;

CREATE TABLE core.users(
  id BIGINT DEFAULT nextval('core.users_seq') NOT NULL,
  phone VARCHAR(20),
  email VARCHAR(100),
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  password VARCHAR(100),
  enabled BOOLEAN,

  CONSTRAINT pk_users PRIMARY KEY(id)
);

------------------------------------------------------------------------------------------------------------------------
--                                           core.roles
------------------------------------------------------------------------------------------------------------------------

CREATE TABLE core.roles(
  id BIGINT NOT NULL,
  name VARCHAR(20),

  CONSTRAINT pk_roles PRIMARY KEY(id)
);

------------------------------------------------------------------------------------------------------------------------
--                                           core.user_roles
------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE core.user_roles_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE core.user_roles(
  id BIGINT DEFAULT nextval('core.user_roles_seq') NOT NULL,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,

  CONSTRAINT pk_user_roles PRIMARY KEY(id),
  CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES core.users(id),
  CONSTRAINT fk_user_roles_roles_id FOREIGN KEY (role_id) REFERENCES core.roles(id)
);

------------------------------------------------------------------------------------------------------------------------
--                                           core.categories
------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE core.categories_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE core.categories(
  id BIGINT DEFAULT nextval('core.categories_seq') NOT NULL,
  name VARCHAR(40),
  status SMALLINT DEFAULT 0,

  CONSTRAINT pk_categories PRIMARY KEY(id)
);

------------------------------------------------------------------------------------------------------------------------
--                                           core.products
------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE core.products_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE core.products(
  id BIGINT DEFAULT nextval('core.products_seq') NOT NULL,
  category_id BIGINT,
  name VARCHAR(100),
  description VARCHAR(500),
  short_description VARCHAR(500),
  additional_info VARCHAR(500),
  badge VARCHAR(200),
  price REAL,
  price_old REAL,
  stars SMALLINT,
  status SMALLINT DEFAULT 0,

  CONSTRAINT pk_products PRIMARY KEY(id),
  CONSTRAINT fk_products_category_id FOREIGN KEY (category_id) REFERENCES core.categories(id)
);

------------------------------------------------------------------------------------------------------------------------
--                                           core.product_images
------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE core.product_images_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE core.product_images(
  id BIGINT DEFAULT nextval('core.product_images_seq') NOT NULL,
  product_id BIGINT,
  url VARCHAR(1000),

  CONSTRAINT pk_product_images PRIMARY KEY(id),
  CONSTRAINT fk_product_images_product_id FOREIGN KEY (product_id) REFERENCES core.products(id)
);
