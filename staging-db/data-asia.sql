USE asia_db;

CREATE TABLE IF NOT EXISTS PRODUCT (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);

INSERT INTO PRODUCT VALUES (1, 'Product 1');
INSERT INTO PRODUCT VALUES (2, 'Product 2');
INSERT INTO PRODUCT VALUES (4, 'Product 4');
INSERT INTO PRODUCT VALUES (5, 'Product 5');