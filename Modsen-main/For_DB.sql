create table if not exists "bookLog"
(
    id          bigserial
        primary key,
    "bookId"    bigint not null
        unique,
    "startTime" date   not null,
    "endTime"   date   not null
);

alter table "bookLog"
    owner to postgres;

create table if not exists users
(
    id       bigint  not null
        primary key
        unique,
    login    varchar not null
        unique,
    email    varchar not null,
    password varchar not null
);

alter table users
    owner to postgres;

create table if not exists user_credential
(
    id       bigserial
        primary key,
    email    varchar(255),
    login    varchar(255),
    password varchar(255)
);

alter table user_credential
    owner to postgres;

create table if not exists books
(
    id          serial
        primary key,
    author      varchar(255),
    description varchar(255),
    genre       varchar(255),
    isbn        integer,
    name        varchar(255)
);

alter table books
    owner to postgres;