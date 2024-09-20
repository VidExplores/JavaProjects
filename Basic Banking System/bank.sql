CREATE DATABASE bank_db;

CREATE TABLE accounts (
    account_number INT PRIMARY KEY,
    holder VARCHAR(50),
    balance DOUBLE
);
