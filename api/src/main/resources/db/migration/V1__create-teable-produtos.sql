create table Produtos(
    id bigint not null auto_increment,
    nomeProduto VARCHAR(55) not null,
    secao VARCHAR(100) not null,
    descricao VARCHAR(155),
    preco DOUBLE not null,

    primary key(id)
    );