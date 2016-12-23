--DROP DATABASE TODO;
--CREATE DATABASE TODO;
--USE TODO;
CREATE USER todo_app WITH PASSWORD 'todo_app';
CREATE SCHEMA IF NOT EXISTS postgres ;
GRANT ALL ON ALL postgres TO todo_app ;
GRANT ALL ON ALL TABLES IN SCHEMA postgres TO todo_app ;
SELECT * FROM pg_users;
drop table IF EXISTS postgres.todo_items;
create table postgres.todo_items(
id int NOT NULL ,
text varchar(255),
created_date date,
due_date date,
primary key(id)
);
