# Gamesproject

### Database Setup
```
create database if not exists gamesproject;

use gamesproject;

create table if not exists publisher (
id int not null primary key,
publishername varchar(245)
);

create table if not exists games (
id int not null primary key,
gamename varchar(245),
publisher_id int,
releasedate date,
platform varchar(95),
foreign key (publisher_id) references publisher(id)
);
```