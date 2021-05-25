CREATE DATABASE IF NOT EXISTS eshop;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS userStatuses;
DROP TABLE IF EXISTS ordersStatuses;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS usersInfo;
DROP TABLE IF EXISTS itemsCatalog;
DROP TABLE IF EXISTS usersCart;
DROP TABLE IF EXISTS gender;
DROP TABLE IF EXISTS age;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS size;
DROP TABLE IF EXISTS style;
DROP TABLE IF EXISTS goods;
DROP TABLE IF EXISTS goodsParam;

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

CREATE TABLE IF NOT EXISTS orderStatuses
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
    FOREIGN KEY (orderStatus) REFERENCES orderStatuses (id) ON DELETE CASCADE
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

INSERT INTO orderStatuses(statusName)
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
       ('rampers');

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
VALUES ('toy1', 'img1'),
       ('toy2', 'img2'),
       ('toy3', 'img3'),
       ('toy4', 'img4'),
       ('toy5', 'img5'),
       ('toy6', 'imm6'),
       ('toy7', 'img7'),
       ('toy8', 'img8'),
       ('toy9', 'img9');

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
       (9, 15.0, 12, CURRENT_DATE);

SELECT name,
       genderName,
       ageName,
       sizeName,
       categoryName,
       styleName,
       price,
       quantity,
       addDate
FROM goodsParam
         INNER JOIN goods g ON g.id = goodsId
         INNER JOIN gender g2 on goodsParam.genderId = g2.id
         INNER JOIN age a on goodsParam.ageId = a.id
         INNER JOIN size s on goodsParam.sizeId = s.id
         INNER JOIN category c on goodsParam.categoryId = c.id
         INNER JOIN style s2 on goodsParam.styleId = s2.id
         INNER JOIN itemsCatalog iC on goodsParam.id = iC.goodsParamId
ORDER BY name;