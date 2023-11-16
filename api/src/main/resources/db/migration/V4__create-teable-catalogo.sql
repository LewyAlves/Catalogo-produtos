create table catalogo(
    id bigint not null auto_increment,
    produto VARCHAR(155) not null,
    secao VARCHAR(155) not null,
    descricao VARCHAR(255),
    preco DOUBLE not null,

    primary key(id)
    );