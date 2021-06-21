create database GlobalWay
use GlobalWay


create table Paises(
IDPais int Primary Key not null identity (1,1),
NombresPais varchar(max) not null,
IsoPais char(2) not null,
Moneda varchar(max) not null,
ValorMoneda MONEY not null,
PorcentajeComi MONEY not null);

create table Reservaciones (
 codigoreserva int Primary Key not null identity (1,1),
 pasaporte int ,
 id int,
 monto money,
 moneda varchar(max),
 oficinapago varchar(max),
 oficinarecibe varchar(max),
 fachareserva date
)

create table Oficinas(
IDOficina int Primary Key not null identity (1,1),
IDPaisOfi int not null,
Ubicacion varchar(max) not null,
FOREIGN KEY(IDPaisOfi) REFERENCES Paises(IDPais));


create table ClientesUsers(
Pasaporte int Primary Key not null,
Cedula int not null,
Nombre_Apellidos varchar(max) not null,
Telefono int not null,
Correo varchar(max) not null);


create table ClientePais(
IDPais int not null,
CodCliente int not null,
Constraint PK_ClientePais primary key clustered (IDPais ASC, CodCliente ASC),
FOREIGN KEY(IDPais) REFERENCES Paises(IDPais),
FOREIGN KEY(CodCliente) REFERENCES ClientesUsers(Pasaporte));


create table LoginUser(
CodUser int Primary Key not null identity (1,1),
PasaporteLogin int not null,
CedulaLogin int  not null,
Contraseña varchar(max),
FOREIGN KEY(PasaporteLogin) REFERENCES ClientesUsers(Pasaporte));


Create table MetodosPago(
IDMetodo int primary Key not null,
NombreMetodo varchar(max) not null);

drop table FacturaRegistro

create table FacturaRegistro(
Codigofactura int primary key not null identity (1,1),
CodigoCliente int  not null,
OficinaPaga int not null,
OficinaRecibe int not null,
MontoPaga money not null,
MontoRecibe money not null,
Porcentaje money not null,
MontoComision money not null,
MetodoPago int not null,
FechaRealizado date not null,
FOREIGN KEY(OficinaPaga) REFERENCES Oficinas(IDOficina),
FOREIGN KEY(OficinaRecibe) REFERENCES Oficinas(IDOficina),
FOREIGN KEY(MetodoPago) REFERENCES MetodosPago(IDMetodo),
FOREIGN KEY(CodigoCliente) REFERENCES ClientesUsers(Pasaporte));

CREATE TABLE Bitacora(
CodBi int Primary Key not null identity (1,1),
CodigoCliente int not null,
fechatrans date not null,
FOREIGN KEY(CodigoCliente) REFERENCES ClientesUsers(Pasaporte)
)
delete from ClientesUsers
select * from ClientesUsers
select * from FacturaRegistro
select * from LoginUser
select * from Paises
select * from Oficinas
select * from MetodosPago
select * from Bitacora

Select PorcentajeComi from Paises where Moneda='Colones'

insert into ClientesUsers values(1,1,'1',1,'1')
insert into MetodosPago values(1, 'Efectivo')
insert into MetodosPago values(2, 'Tarjeta Credito')

insert into Paises values('Costa Rica','CR','Colones',580,0.03)
insert into Paises values('Panama','PN','Balboas',1.00,0.04)
insert into Paises values('Peru','PU','Soles',3.52,0.055)
insert into Paises values('Guatemala','GU','Quetzales',7.67,0.03)
insert into Paises values('Mexico','MX','Pesos',22.03,0.05)
insert into Paises values('Honduras','HD','Lempiras',24.93,0.04)



insert into Oficinas values(1,'San José')
insert into Oficinas values(2,'Ciudad de Panama')
insert into Oficinas values(3,'Lima')
insert into Oficinas values(4,'Ciudad de Guatemala')
insert into Oficinas values(5,'Ciudad de Mexico')
insert into Oficinas values(6,'Tegucigalpa')