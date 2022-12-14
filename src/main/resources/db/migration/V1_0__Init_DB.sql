-- auto-generated role table
create table role_table (
    id bigint generated by default as identity,
    name varchar(255),
    primary key (id)
)

create index user_id_index on user_table (id);

-- auto-generated user table
create table user_table (
    id bigint generated by default as identity,
    login varchar(255), password varchar(255),
    role_id bigint,
    primary key (id)
)

create index role_id_index on role_table (id);

alter table user_table add constraint user_table_constraint foreign key (role_id) references role_table

