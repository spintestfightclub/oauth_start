--DROP TABLE USER;
--DROP TABLE TRANSACTIONS;
--CREATE TABLE IF NOT EXISTS USER (ID INT PRIMARY KEY, USERNAME VARCHAR(30), EMAIL VARCHAR(30), ENABLED BOOLEAN)

/*drop table users if exists;
drop table authorities if exists;
create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled boolean not null,
    email varchar(100) not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username, authority);*/
--CREATE TABLE IF NOT EXISTS TRANSACTIONS (ID INT PRIMARY KEY, USERNAME VARCHAR(30), COIN VARCHAR (30), ACT VARCHAR(30), AMOUNT INT);