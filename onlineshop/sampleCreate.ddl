create table clientes (id bigint not null, dtNascimento timestamp, nome varchar(255), primary key (id))
create table clientes (id bigint not null, dtNascimento timestamp, nome varchar(255), primary key (id))
create table clientes (id bigint not null, dtNascimento timestamp, nome varchar(255), primary key (id))
create sequence hibernate_sequence start with 1 increment by 1
create table clientes (id bigint not null, dtNascimento timestamp, nome varchar(255), primary key (id))
create sequence hibernate_sequence start with 1 increment by 1
create table clientes (id bigint not null, dtNascimento timestamp, nome varchar(255), primary key (id))
create sequence hibernate_sequence start with 1 increment by 1
create table clientes (id bigint not null, dtNascimento timestamp, nome varchar(255), primary key (id))
create table Marca (id bigint not null, descricao varchar(255), logomarca varchar(255), nome varchar(255), primary key (id))
create table Produto (id bigint not null, imagem varchar(255), moeda varchar(255), nome varchar(255), preco numeric(19,2), marca_id bigint, primary key (id))
create table Produto_Tag (Produto_id bigint not null, tags_nome varchar(255) not null)
create table Tag (nome varchar(255) not null, primary key (nome))
alter table Produto add constraint FKk1rkkokyj8lgp301ac8m7cp1j foreign key (marca_id) references Marca
alter table Produto_Tag add constraint FK6crsmdgm8drdqideack2t0pga foreign key (tags_nome) references Tag
alter table Produto_Tag add constraint FKevy1ga1jm4fq4bvob2y363saq foreign key (Produto_id) references Produto
create sequence hibernate_sequence start with 1 increment by 1
create table clientes (id bigint not null, dtNascimento timestamp, nome varchar(255), primary key (id))
create table Marca (id bigint not null, descricao varchar(255), logomarca varchar(255), nome varchar(255), primary key (id))
create table Produto (id bigint not null, imagemPrincipal varchar(255), imagens varchar(255) for bit data, moeda varchar(255), nome varchar(255), preco numeric(19,2), marca_id bigint, primary key (id))
create table Produto_Tag (Produto_id bigint not null, tags_nome varchar(255) not null)
create table Tag (nome varchar(255) not null, primary key (nome))
alter table Produto add constraint FKk1rkkokyj8lgp301ac8m7cp1j foreign key (marca_id) references Marca
alter table Produto_Tag add constraint FK6crsmdgm8drdqideack2t0pga foreign key (tags_nome) references Tag
alter table Produto_Tag add constraint FKevy1ga1jm4fq4bvob2y363saq foreign key (Produto_id) references Produto
