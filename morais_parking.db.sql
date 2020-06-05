BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "estacionamento" (
	"_id"	INTEGER,
	"veiculo_id"	INTEGER NOT NULL,
	"placa"	TEXT NOT NULL UNIQUE,
	"area_id"	INTEGER NOT NULL,
	PRIMARY KEY("_id")
);
CREATE TABLE IF NOT EXISTS "permissoes" (
	"_id"	INTEGER,
	"veiculo_id"	INTEGER NOT NULL UNIQUE,
	"area_id"	INTEGER NOT NULL,
	PRIMARY KEY("_id")
);
CREATE TABLE IF NOT EXISTS "reservas" (
	"_id"	INTEGER,
	"evento_id"	INTEGER NOT NULL,
	"area_id"	INTEGER NOT NULL,
	"vagas"	INTEGER NOT NULL,
	PRIMARY KEY("_id")
);
CREATE TABLE IF NOT EXISTS "eventos" (
	"_id"	INTEGER,
	"nome"	TEXT NOT NULL UNIQUE,
	"inicio"	TEXT NOT NULL UNIQUE,
	"fim"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("_id")
);
CREATE TABLE IF NOT EXISTS "veiculos" (
	"_id"	INTEGER,
	"placa"	TEXT NOT NULL UNIQUE,
	"proprietario_id"	INTEGER,
	"modelo"	TEXT,
	"cor"	TEXT,
	"tipo_veiculo"	TEXT NOT NULL,
	PRIMARY KEY("_id")
);
CREATE TABLE IF NOT EXISTS "proprietarios" (
	"_id"	INTEGER,
	"nome"	TEXT NOT NULL UNIQUE,
	"matricula"	INTEGER,
	"curso"	TEXT,
	PRIMARY KEY("_id")
);
CREATE TABLE IF NOT EXISTS "areas_estacionamento" (
	"_id"	INTEGER,
	"nome"	TEXT NOT NULL UNIQUE,
	"capacidade"	INTEGER NOT NULL,
	"tipo_veiculo"	TEXT NOT NULL,
	"especial"	INTEGER NOT NULL,
	PRIMARY KEY("_id")
);
CREATE TABLE IF NOT EXISTS "usuarios" (
	"_id"	INTEGER,
	"nome"	TEXT NOT NULL,
	"setor"	TEXT NOT NULL,
	"email"	TEXT NOT NULL UNIQUE,
	"password"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("_id")
);
INSERT INTO "permissoes" VALUES (2,2,4);
INSERT INTO "permissoes" VALUES (3,3,4);
INSERT INTO "permissoes" VALUES (4,1,5);
INSERT INTO "permissoes" VALUES (5,5,6);
INSERT INTO "reservas" VALUES (1,1,6,0);
INSERT INTO "reservas" VALUES (2,1,1,3);
INSERT INTO "reservas" VALUES (3,1,3,5);
INSERT INTO "reservas" VALUES (4,1,2,0);
INSERT INTO "reservas" VALUES (5,1,5,1);
INSERT INTO "reservas" VALUES (6,1,4,0);
INSERT INTO "eventos" VALUES (1,'Hackathon','2020-06-01','2020-06-15');
INSERT INTO "veiculos" VALUES (1,'QFI7289',1,'Ford Ka','Preto','CARRO');
INSERT INTO "veiculos" VALUES (2,'7777',3,'HB20','Vermelho','CARRO');
INSERT INTO "veiculos" VALUES (3,'S0JU',3,'Ferrari','Vermelho','CARRO');
INSERT INTO "veiculos" VALUES (4,'REN474',5,'Ford Ka','Preto','CARRO');
INSERT INTO "veiculos" VALUES (5,'XUN1077',6,'Mercedes Benz','Prata','ONIBUS');
INSERT INTO "proprietarios" VALUES (1,'Roberto',11021093,'SpI');
INSERT INTO "proprietarios" VALUES (2,'Larissa',11021093,'SpI');
INSERT INTO "proprietarios" VALUES (3,'Iria',11021093,'SpI');
INSERT INTO "proprietarios" VALUES (4,'Arthur',11021093,'SpI');
INSERT INTO "proprietarios" VALUES (5,'Renata',20192007054,'Engenharia da Computacao');
INSERT INTO "proprietarios" VALUES (6,'Junior',11021093,'SpI');
INSERT INTO "areas_estacionamento" VALUES (1,'Carros',10,'CARRO',0);
INSERT INTO "areas_estacionamento" VALUES (2,'Onibus',5,'ONIBUS',0);
INSERT INTO "areas_estacionamento" VALUES (3,'Motos',20,'MOTOCICLETA',0);
INSERT INTO "areas_estacionamento" VALUES (4,'Deficientes',5,'CARRO',1);
INSERT INTO "areas_estacionamento" VALUES (5,'VIP',2,'CARRO',1);
INSERT INTO "areas_estacionamento" VALUES (6,'Escolar',5,'ONIBUS',1);
INSERT INTO "usuarios" VALUES (1,'Roberto','ESTACIONAMENTO','robertompfm@gmail.com','beto');
INSERT INTO "usuarios" VALUES (2,'Larissa','RH','larissa@cauane.com','hunterhunter');
INSERT INTO "usuarios" VALUES (3,'Iria','GESTOR','iria@guazzi.com','soju');
COMMIT;
