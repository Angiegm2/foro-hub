CREATE TABLE profile (
         id bigint auto_increment primary key,
         user_id bigint not null,
         name varchar(100) not null,
         lastname varchar(100) not null,
         country varchar(100) not null,
         created_on timestamp default current_timestamp,
         last_updated_on timestamp default current_timestamp on update current_timestamp,
         unique (user_id),
         constraint fk_user_profile foreign key (user_id) references users (id) on delete cascade
);