-- 这个文件主要是建库建表使用。
-- 这就是一个文本文件，里面记录着如何建库建表。以及各表的信息。
-- 一般将这个文件保存着，供以后其他人方便查看使用。

create database blog if not exists blog_system;

use blog_system;

drop table if exists user;
drop table if exists blog;

create table user {
    userID int primary key auto_increment,
    userName varchar(20) unique,
    passward varchar(20)
};


create table blog {
    blogId int primary key auto_increment,
    title varchar(128),
    content varchar(4096),
    postTime datetime,
    userId int
};
