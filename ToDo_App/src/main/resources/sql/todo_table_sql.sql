--DROP DATABASE TODO;
--CREATE DATABASE TODO;
--USE TODO;
drop table mysql.todo_items;
create table mysql.todo_items(
id int NOT NULL ,
text varchar(255),
created_date date,
due_date date,
primary key(id)
);