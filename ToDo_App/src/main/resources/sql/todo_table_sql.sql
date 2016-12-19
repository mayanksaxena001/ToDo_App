--DROP DATABASE TODO;
--CREATE DATABASE TODO;
--USE TODO;
CREATE DATABASE todo_db WITH
  ENCODING = 'UTF8'
  TABLESPACE = pg_default
  CONNECTION LIMIT = -1;
CREATE SCHEMA IF NOT EXISTS postgres 
drop table IF EXISTS postgres.todo_items;
create table postgres.todo_items(
id int NOT NULL ,
text varchar(255),
created_date date,
due_date date,
primary key(id)
);