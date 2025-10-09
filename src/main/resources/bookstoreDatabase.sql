
-- Varmuuden vuoksi poistetaan vanhat taulut, jos ne on jo olemassa
DROP TABLE IF EXISTS book CASCADE;
DROP TABLE IF EXISTS category CASCADE;
DROP TABLE IF EXISTS app_user CASCADE;

-- Taulu: CATEGORY
CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Taulu: BOOK
CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INT NOT NULL,
    isbn VARCHAR(30) NOT NULL UNIQUE,
    price DOUBLE PRECISION NOT NULL,
    category_id BIGINT REFERENCES category(id) ON DELETE SET NULL
);

-- Taulu: APP_USER 
CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    application_password VARCHAR(255) NOT NULL,
    application_role VARCHAR(50) NOT NULL
);

-- Lisätään esimerkkidataa

-- Categories
INSERT INTO category (name) VALUES 
('Fantasy'),
('Sci-Fi'),
('Thriller');

-- Books
INSERT INTO book (title, author, publication_year, isbn, price, category_id) VALUES
('Eragon', 'Christopher Paolini', 2005, '978-952-459-525-2', 19.90, 1),
('Perillinen', 'Christopher Paolini', 2006, '978-952-459-773-7', 21.90, 1),
('Brisingr', 'Christopher Paolini', 2008, '978-952-459-924-3', 24.90, 1),
('Perintö', 'Christopher Paolini', 2012, '978-952-459-995-3', 27.90, 1);

-- Users (bcrypt-salatut salasanat "password")
INSERT INTO app_user (firstname, lastname, username, application_password, application_role) VALUES
('Admin', 'User', 'admin', '$2a$10$FWjcgEeZdWUGgjyJZmxQNurBxhED3WfIdem7MPqxnvbxEJibSYFdu', 'ADMIN'),
('User', 'User', 'user',  '$2a$10$G5mujdmwWh.jil9X.sqV3uBGJJ9Lz/vn7Emyv16KnpykYBjASGzFu', 'USER'),
('Aksu', 'Ekman', 'aksu', '$2a$10$tJa62m.vEuDntkh10I50le3FPogit6Xx7CAqT6oh062K2LU3IwZja', 'ADMIN');

