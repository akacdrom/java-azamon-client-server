drop database azamondb;
drop user azamon;
create user azamon with password 'password';
create database azamondb with template=template0 owner=azamon;
\connect azamondb;
alter default privileges grant all on tables to azamon;
alter default privileges grant all on sequences to azamon;

create table et_admin(
id SERIAL primary key not null,
name varchar(20) not null,
surname varchar(20) not null,
email varchar(30) not null,
password text not null,
secret_key text not null,
IV text not null
);

create table et_employee(
id SERIAL primary key not null,
name varchar(20) not null,
surname varchar(20) not null,
email varchar(30) not null,
password text not null,
secret_key text not null,
IV text not null
);

create table et_users(
id SERIAL primary key not null,
name varchar(20) not null,
surname varchar(20) not null,
email varchar(30) not null,
password text not null,
secret_key text not null,
IV text not null
);

create table et_books(
id SERIAL primary key not null,
title varchar(20) not null,
description varchar(50) not null,
author varchar(20) not null,
genre varchar(20) not null
);

create table et_orders(
id SERIAL primary key not null,
email varchar(20) not null
);

