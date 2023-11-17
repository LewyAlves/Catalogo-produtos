create table usuarios(
    id bigint not null auto_increment,
    login VARCHAR(100) not null,
    senha VARCHAR(255) not null,

    primary key(id)
    );