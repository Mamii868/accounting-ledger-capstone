-- PennyWise database setup script
-- Fixed: users created before transactions (FK dependency order),
-- FK checks wrapper for safe re-runs, redundant unique indexes removed.

SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS pennywise;

CREATE DATABASE IF NOT EXISTS pennywise /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE pennywise;


-- BEGIN TABLE users (parent table first)
DROP TABLE IF EXISTS users;
CREATE TABLE `users` (
  `userID` binary(16) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Inserting 4 rows into users
-- (removed stale pre-hashing user 'tk' whose password was stored in plaintext)
INSERT INTO users (userID, firstname, lastname, email, username, password, role) VALUES
(0x2c68ee4d3faa41928b62c8d261861b84, 'Takudzwa', 'Mucherengi', 't@email.com', 'tk3', '$2a$10$cS1oyYMav6ZRIiRGcx4PzeMemWPQqIUwhDF3Wp94Mxxlqd2iscWOe', 'USER'),
(0x7eb955ae8014439da171be5c2371665c, 'Takudzwa', 'Mucherengi', 'tmucherengi@my.yearupunited.org', 'tk1', '$2a$10$qlLFF35PsBT.OXd0NejCZOTNI/tUsUNZyfn7uPa/hVUQ0kUMZN.v.', 'USER'),
(0x9d8b4e89f53f475f8d2e63a46b6fc7b3, 'Takudzwa', 'Mucherengi', 't@email', 'tk4', '$2a$10$r5LGOQsGS39asOFIhZmgeed3LZK4/QvCWyJ0IaBPQAompr1pTpA2.', 'USER'),
(0xa6a5ef39128f40d0ae52f3b79527b42f, 'Takudzwa', 'Mucherengi', 'tmucherengi@my.yearupunited.org', 'tk2', '$2a$10$WGTX5G5oa7Z9nMk1O7ylTOQLvv3P01RQLSi83j7BUUx5w0TVCBgpm', 'USER');

-- END TABLE users


-- BEGIN TABLE transactions (child table second)
DROP TABLE IF EXISTS transactions;
CREATE TABLE `transactions` (
  `transactionID` int NOT NULL AUTO_INCREMENT,
  `userID` binary(16) NOT NULL,
  `timestamp` timestamp NOT NULL,
  `description` varchar(45) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `vendor` varchar(45) NOT NULL,
  `reference` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`transactionID`),
  UNIQUE KEY `reference_UNIQUE` (`reference`),
  KEY `fk_transactions_user` (`userID`),
  CONSTRAINT `fk_transactions_user` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Inserting 1 row into transactions
INSERT INTO transactions (transactionID, userID, `timestamp`, description, amount, vendor, reference) VALUES
(1, 0x7eb955ae8014439da171be5c2371665c, '2026-07-15 12:26:03', 'Office supply', 42.55, 'Staple', 'TXN-20260715-B66CE6F3');

-- END TABLE transactions

SET FOREIGN_KEY_CHECKS = 1;
