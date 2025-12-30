create database BankingDB;
use BankingDb;
create table accounts(
	acc_num INT PRIMARY KEY auto_increment,
    name varchar(50),
    balance double
);
use BankingDb;
select * from accounts;
SELECT * FROM accounts WHERE acc_num = 1;
show databases;