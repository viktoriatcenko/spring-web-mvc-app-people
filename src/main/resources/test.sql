-- auto-generated definition
create sequence first_seq;

alter sequence first_seq owner to postgres;

drop table person

create table person(
                       id serial primary key ,
                       name varchar(1000) not null,
                       age int check (
                           age > 0
                           ),
                       email varchar unique
)

    insert into person(name, age, email) values ('Tagir', 25, 'tagir@mail.ru')
insert into person(name, age, email) values ('Vitaliy', 25, 'tagir@mail.ru')
insert into person(name) values ('Tagir')
insert into person(name) values ('Vitaliy')
