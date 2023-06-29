-- 这个文件主要用来写建库建表语句.
-- 一般都建议大家, 在建表的时候把建表 sql 保留下来. 以备后续部署其他机器的时候就方便了.

-- 这个只是一个文本文件，将你的sql记录下来。

create database if not exists java106_blog_system;
use java106_blog_system;

-- 删除旧表, 重新创建新表. 删除旧表是为了防止之前的残留数据对后续的程序有负面影响.
drop table if exists user;
drop table if exists blog;

-- 真正创建表.
create table blog (
    blogId int primary key auto_increment,
    title varchar(128),
    content varchar(4096),
    postTime datetime,
    userId int
);

create table user (
    userId int primary key auto_increment,
    username varchar(20) unique,        -- 要求用户名和别人不重复~~
    password varchar(20)
);

-- 构造测试数据
insert into blog values(1, '这是我的第一篇博客', '从今天开始我要认真敲代码', now(), 1);
insert into blog values(2, '这是我的第二篇博客', '从昨天开始我要认真敲代码', now(), 1);
insert into blog values(3, '这是我的第三篇博客', '从前天开始我要认真敲代码', now(), 1);

-- 构造测试数据
insert into user values(1, 'zhangsan', '123');
insert into user values(2, 'lisi', '123');
