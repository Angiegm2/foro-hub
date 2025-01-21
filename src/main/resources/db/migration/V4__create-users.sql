CREATE TABLE users (
       id bigint auto_increment primary key,
       email varchar(100) not null unique,
       password varchar(255) not null,
       role varchar(50) not null default'user',
       created_on timestamp default current_timestamp,
       last_updated_on timestamp default current_timestamp on update current_timestamp
);

