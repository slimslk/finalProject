CREATE DATABASE IF NOT EXISTS eshop;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS usersInfo;
DROP TABLE IF EXISTS catalog;
DROP TABLE IF EXISTS usersCrate;
DROP TABLE IF EXISTS goods;

CREATE TABLE IF NOT EXISTS roles
(
    roleId   INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    roleName VARCHAR(255)       NOT NULL,
    UNIQUE (roleId, roleName)
);

CREATE TABLE IF NOT EXISTS users
(
    id       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(255)       NOT NULL,
    password VARCHAR(255)       NOT NULL,
    roleId   INT                NOT NULL,
    UNIQUE (id, username),
    FOREIGN KEY (roleId) REFERENCES roles (roleId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usersInfo
(
    id      INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userId  INT                NOT NULL,
    name    VARCHAR(255),
    surname VARCHAR(255),
    UNIQUE (id),
    FOREIGN KEY (userId) references users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS goods
(
    id   INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255)       NOT NULL,
    UNIQUE (id, name)
);

CREATE TABLE IF NOT EXISTS usersCrate
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    userId  INT NOT NULL,
    goodsId int NOT NULL,
    UNIQUE (id),
    FOREIGN KEY (goodsId) REFERENCES goods (id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS statuses
(
    id         INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    statusName VARCHAR(255)       NOT NULL,
    UNIQUE (id, statusName)
);

CREATE TABLE IF NOT EXISTS orders
(
    id          INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    orderNumber INT                NOT NULL,
    userId      INT                NOT NULL,
    goodsId     INT                NOT NULL,
    orderStatus INT                NOT NULL,
    orderDate   DATETIME           NOT NULL,
    UNIQUE (id),
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (goodsId) REFERENCES goods (id) ON DELETE CASCADE,
    FOREIGN KEY (orderStatus) REFERENCES statuses (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS catalog
(
    id       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    goodsId  INT                NOT NULL,
    price    DOUBLE             NOT NULL,
    quantity INT                NOT NULL DEFAULT (0),
    addDate  DATE               NOT NULL,
    UNIQUE (id, goodsId),
    FOREIGN KEY (goodsId) REFERENCES goods (id) ON DELETE CASCADE
);

INSERT INTO roles(roleName)
VALUES ('superadmin'),
       ('admin'),
       ('user');

INSERT INTO users(username, password, roleId)
VALUES ('slim.slk@gmail.com', '777', '1');

INSERT INTO goods (name)
VALUES ('toy1'),
       ('toy2'),
       ('toy3');

INSERT INTO catalog (goodsId, price, quantity, addDate)
VALUES (1, 10, 3, CURRENT_DATE),
       (2, 3, 5, CURRENT_DATE),
       (3, 15, 0, CURRENT_DATE);