DROP TABLE students IF EXISTS;

CREATE TABLE students (
  id         INTEGER IDENTITY PRIMARY KEY,
  name       VARCHAR(30),
  faculty    VARCHAR(50),
  hometown   VARCHAR(255),
  stu_id      VARCHAR(30),
  birthday   DATE,
  sex        VARCHAR(10)
);



