drop table if exists author;
create table author(id bigserial primary key, fio varchar(255), book_id bigint,
constraint author_fio_uk unique (fio));

drop table if exists genre;
create table genre(id bigserial primary key, name varchar(255), book_id bigint,
constraint genre_name_uk unique (name));

drop table if exists book;
create table book(id bigserial primary key, title varchar(255), author_id bigint, genre_id bigint,
foreign key (author_id) references author(id),
foreign key (genre_id) references genre(id),
constraint book_uk unique (title, author_id));