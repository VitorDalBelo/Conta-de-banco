# Conta-de-banco
Este projeto simula (de maneira abstrata) uma conta de banco com comandos via console
para usa-lo é necessario usar o driver JDBC(no meu caso usei o do mariadb , mas vc pode usar outro , basta mudar as duas Strings de coneção ) também é necessario criar a seguinte tabela:
create table clientes( 
dac integer unique not null,agencia integer not null,nome varchar(30), 
saldo double default 0,numeroConta integer unique not null,senha varchar (30),
cpf varchar(30) primary key not null);
