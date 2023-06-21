# Gamesproject M295

## Database Setup
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

## Spring Boot Setup

In the `application.properties` file under _src/main/resources_ you probably need to change these two properties:
``` 
spring.datasource.username= <your value>
spring.datasource.password= <your value>
```

## OpenApi Specification

Put the code from [restapi.yaml](src/main/resources/restapi.yaml) into the [swagger editor](https://editor.swagger.io/), to see all the different paths and crud operations and what they do.

