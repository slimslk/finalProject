CREATE DATABASE IF NOT EXISTS eshop CHARACTER SET UTF8 COLLATE utf8_bin;



SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS age;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS gender;
DROP TABLE IF EXISTS goods;
DROP TABLE IF EXISTS goodsParam;
DROP TABLE IF EXISTS itemsCatalog;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS ordersList;
DROP TABLE IF EXISTS orderStatus;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS size;
DROP TABLE IF EXISTS style;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS usersCart;
DROP TABLE IF EXISTS usersInfo;
DROP TABLE IF EXISTS userStatuses;

CREATE TABLE IF NOT EXISTS roles
(
    roleId   INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    roleName VARCHAR(255)       NOT NULL,
    UNIQUE (roleId, roleName)
);

CREATE TABLE IF NOT EXISTS userStatuses
(
    id         INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    statusName VARCHAR(255)       NOT NULL,
    UNIQUE (id, statusName)
);

CREATE TABLE IF NOT EXISTS users
(
    id       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(255)       NOT NULL,
    password VARCHAR(255)       NOT NULL,
    roleId   INT                NOT NULL,
    status   INT                NOT NULL,
    locale   VARCHAR(20)        NOT NULL,
    UNIQUE (id, username),
    FOREIGN KEY (roleId) REFERENCES roles (roleId) ON DELETE CASCADE,
    FOREIGN KEY (status) REFERENCES userStatuses (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usersInfo
(
    id      INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userId  INT                NOT NULL,
    name    VARCHAR(255),
    surname VARCHAR(255),
    UNIQUE (id, userId),
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS gender
(
    id         INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    genderName VARCHAR(50)        NOT NULL,
    UNIQUE (id, genderName)
);

CREATE TABLE IF NOT EXISTS age
(
    id      INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    ageName VARCHAR(50)        NOT NULL,
    UNIQUE (id, ageName)
);

CREATE TABLE IF NOT EXISTS size
(
    id       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    sizeName VARCHAR(50)        NOT NULL,
    UNIQUE (id, sizeName)
);

CREATE TABLE IF NOT EXISTS category
(
    id           INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    categoryName VARCHAR(50)        NOT NULL,
    UNIQUE (id, categoryName)
);

CREATE TABLE IF NOT EXISTS style
(
    id        INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    styleName VARCHAR(50)        NOT NULL,
    UNIQUE (id, styleName)
);

CREATE TABLE IF NOT EXISTS goods
(
    id   INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255)       NOT NULL,
    img  VARCHAR(255)       NOT NULL,
    UNIQUE (id, name)

);

CREATE TABLE IF NOT EXISTS goodsParam
(
    id         INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    goodsId    INT                NOT NULL,
    genderId   INT                NOT NULL,
    ageId      INT                NOT NULL,
    sizeId     INT                NOT NULL,
    categoryId INT                NOT NULL,
    styleId    INT                NOT NULL,
    UNIQUE (id),
    FOREIGN KEY (goodsId) REFERENCES goods(id) ON DELETE CASCADE,
    FOREIGN KEY (genderId) REFERENCES gender (id) ON DELETE CASCADE,
    FOREIGN KEY (ageId) REFERENCES age (id) ON DELETE CASCADE,
    FOREIGN KEY (sizeId) REFERENCES size (id) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES category (id) ON DELETE CASCADE,
    FOREIGN KEY (styleId) REFERENCES style (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usersCart
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    userId  INT NOT NULL,
    goodsId int NOT NULL,
    UNIQUE (id),
    FOREIGN KEY (goodsId) REFERENCES goods (id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orderStatus
(
    id         INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    statusName VARCHAR(255)       NOT NULL,
    UNIQUE (id, statusName)
);

CREATE TABLE IF NOT EXISTS orders
(
    orderNumber INT      NOT NULL PRIMARY KEY,
    userId      INT      NOT NULL,
    orderStatus INT      NOT NULL,
    orderDate   DATETIME NOT NULL,
    UNIQUE (orderNumber),
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (orderStatus) REFERENCES orderStatus (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ordersList
(
    id            INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    orderId       INT                NOT NULL,
    goodsId       INT                NOT NULL,
    goodsQuantity INT                NOT NULL,
    UNIQUE (id),
    FOREIGN KEY (orderId) REFERENCES orders (orderNumber) ON DELETE CASCADE,
    FOREIGN KEY (goodsId) REFERENCES itemsCatalog (goodsParamId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS itemsCatalog
(
    id           INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    goodsParamId INT                NOT NULL,
    price        DOUBLE             NOT NULL,
    quantity     INT                NOT NULL DEFAULT (0),
    addDate      DATE               NOT NULL,
    UNIQUE (id, goodsParamId),
    FOREIGN KEY (goodsParamId) REFERENCES goodsParam (id) ON DELETE CASCADE
);

INSERT INTO roles(roleName)
VALUES ('superadmin'),
       ('admin'),
       ('user');

INSERT INTO userStatuses(statusName)
VALUES ('active'),
       ('disabled');

INSERT INTO orderStatus(statusName)
VALUES ('registered'),
       ('paid'),
       ('canceled');

INSERT INTO users(username, password, roleId, status)
VALUES ('slim.slk@gmail.com', '777', 1, 1);

INSERT INTO usersInfo(userId, name, surname)
VALUES (1, 'Dimm', 'K');

INSERT INTO gender(genderName)
VALUES ('girl'),
       ('boy');

INSERT INTO age(ageName)
VALUES ('baby'),
       ('toddler'),
       ('kid');

INSERT INTO category(categoryName)
VALUES ('bottoms'),
       ('tops'),
       ('pajamas'),
       ('sets'),
       ('shoes'),
       ('dresses'),
       ('rompers');

INSERT INTO size(sizeName)
VALUES ('3m'),
       ('6m'),
       ('9m'),
       ('12m'),
       ('18m'),
       ('24m');

INSERT INTO style(styleName)
VALUES ('graphics'),
       ('casual'),
       ('dresses'),
       ('shorts'),
       ('tops&tees'),
       ('sleep&play'),
       ('romper');

INSERT INTO goods (name, img)
VALUES ('Pants', 'img1.jpg'),
       ('Top', 'img2.jpg'),
       ('Romper', 'img3.jpg'),
       ('Dress', 'img4.jpg'),
       ('T-shirt', 'img5.jpg'),
       ('Socks', 'img6.jpg'),
       ('Red Shoes', 'img7.jpg'),
       ('Body', 'img8.jpg'),
       ('Tulip', 'img9.jpg');

INSERT INTO goodsParam(goodsId, genderId, ageId, sizeId, categoryId, styleId)
VALUES (1, 1, 1, 1, 1, 1),
       (1, 2, 1, 1, 1, 1),
       (2, 1, 3, 2, 3, 4),
       (3, 1, 2, 2, 4, 1),
       (2, 2, 1, 2, 1, 1),
       (4, 1, 2, 5, 2, 4),
       (5, 2, 3, 4, 4, 3),
       (4, 2, 3, 5, 3, 2),
       (4, 1, 1, 4, 4, 1),
       (6, 2, 2, 3, 2, 1),
       (7, 2, 3, 4, 1, 2),
       (8, 1, 1, 1, 2, 3),
       (9, 2, 1, 3, 2, 4);



INSERT INTO itemsCatalog (goodsParamId, price, quantity, addDate)
VALUES (1, 10.5, 3, CURRENT_DATE),
       (2, 3.3, 5, CURRENT_DATE),
       (3, 15.6, 0, CURRENT_DATE),
       (4, 10.15, 3, CURRENT_DATE),
       (5, 3.8, 5, CURRENT_DATE),
       (6, 15.12, 7, CURRENT_DATE),
       (7, 10.43, 1, CURRENT_DATE),
       (8, 3.56, 6, CURRENT_DATE),
       (9, 15.0, 12, CURRENT_DATE),
       (10, 15.0, 12, CURRENT_DATE),
       (11, 15.0, 12, CURRENT_DATE),
       (12, 15.0, 12, CURRENT_DATE),
       (13, 15.0, 12, CURRENT_DATE);

ALTER TABLE users ADD locale VARCHAR(20) NOT NULL AFTER status;