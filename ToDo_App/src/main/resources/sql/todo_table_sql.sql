--DROP DATABASE TODO;
--CREATE DATABASE TODO;
--USE TODO;
SELECT usename FROM pg_users;
CREATE USER todo_app WITH PASSWORD 'todo_app';
CREATE SCHEMA IF NOT EXISTS postgres_1 ;
GRANT ALL ON ALL postgres_1 TO todo_app ;
GRANT ALL ON ALL TABLES IN SCHEMA postgres_1 TO todo_app ;
SELECT usename FROM pg_users;
ALTER USER todo_app SET search_path=postgres_1;
drop table IF EXISTS postgres_1.todo_items;
create table postgres_1.todo_items(
id int NOT NULL ,
text varchar(255),
created_date date,
due_date date,
primary key(id)
);
