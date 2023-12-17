CREATE DATABASE IF NOT EXISTS stock_service;

USE stock_service;

CREATE TABLE tbl_stock (
                        id BIGINT AUTO_INCREMENT NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        current_price DECIMAL(10, 2) NOT NULL,
                        last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY(id),
                        UNIQUE KEY unique_name (name)
);

INSERT INTO tbl_stock (name, current_price)
VALUES
('BNB', 200.3),
('TCT', 13.1),
('FGF', 34.18),
('KMK', 1.1),
('ERE', 2354.34),
('HYH', 75675.25),
('TRT', 654.98),
('VCB', 12.23);
