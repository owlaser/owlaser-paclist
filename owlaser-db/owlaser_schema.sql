drop database if exists OWLASER;
drop user if exists 'OWLASER'@'localhost';
create database OWLASER default character set utf8mb4 collate utf8mb4_unicode_ci;
use OWLASER;
create user 'OWLASER'@'localhost' identified by 'OWLASER';
grant all privileges on OWLASER.* to 'OWLASER'@'localhost';
flush privileges;